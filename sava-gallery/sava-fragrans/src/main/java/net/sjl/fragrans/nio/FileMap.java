package net.sjl.fragrans.nio;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;

public class FileMap {

  public static void main(String[] args) {
    long start = System.nanoTime();
    mappedRead("/var/log/auth.log");
    long duration = System.nanoTime() - start;
    System.out.println("mappedRead duration is " + duration + "nano" );
    start = System.nanoTime();
    mappedWrite("/var/log/auth.log", "/var/log/newauth.log");    
    duration = System.nanoTime() - start;
    System.out.println("mappedWrite duration is " + duration + "nano" );
    start = System.nanoTime();
    copy("/var/log/auth.log", "/var/log/newauth.log");    
    duration = System.nanoTime() - start;
    System.out.println("copy duration is " + duration + "nano" );
  }

  public static void mappedRead(String filePath) {
    CharBuffer charBuffer = null;
    Path pathToRead = Paths.get(filePath);

    try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead, EnumSet.of(StandardOpenOption.READ))) {
      MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

      if (mappedByteBuffer != null) {
        charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // System.out.println(charBuffer);
  }  

  public static void mappedWrite(String sourcePath, String targetPath) {

    try(RandomAccessFile srandomAccessFile = new RandomAccessFile(sourcePath, "r"); RandomAccessFile trandomAccessFile = new RandomAccessFile(targetPath, "rw")) {
      FileChannel sChannel = srandomAccessFile.getChannel(); 
      FileChannel tChannel = trandomAccessFile.getChannel();
      MappedByteBuffer rmappedByteBuffer = sChannel.map(FileChannel.MapMode.READ_ONLY, 0, srandomAccessFile.length());
      // MappedByteBuffer wmappedByteBuffer = tChannel.map(FileChannel.MapMode.READ_WRITE, 0, srandomAccessFile.length());
      MappedByteBuffer wmappedByteBuffer = tChannel.map(FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE);
      byte[] data = new byte[512];
      while (rmappedByteBuffer.hasRemaining()) {
        int remaining = data.length;
        if(rmappedByteBuffer.remaining() < remaining)
          remaining = rmappedByteBuffer.remaining();
        rmappedByteBuffer.get(data, 0, remaining);
        wmappedByteBuffer.put(data);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void copy(String sourcePath, String targetPath) {
    try(FileChannel source = new FileInputStream(sourcePath).getChannel(); FileChannel destination = new FileOutputStream(targetPath).getChannel()) {
      destination.transferFrom(source, 0, source.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
