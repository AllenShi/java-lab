package net.sjl.fragrans.test.ar;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.junit.Test;

import net.sjl.fragrans.ar.FilesZipper;

public class FilesZipperTest {
	
	@Test
	public void testZipFolder() {
		String srcFolder = "/Users/mobile/Downloads/cpe-docker-fdeefe00856540e795a709aef8e5469cc370c06f";
		String dstFile = FilesZipper.zip(srcFolder);
		System.out.println("dstFile is " + dstFile);
	}
	
	@Test
	public void testZipNonExistedFolder() {
		String srcFolder = "/Uxsers/mobile/Downloads/cpe-docker-fdeefe00856540e795a709aef8e5469cc370c06f";
		String dstFile = FilesZipper.zip(srcFolder);
		System.out.println("dstFile is " + dstFile);
	}
	
	@Test
	public void testZipFiler() {
		String srcFolder = "/Users/mobile/Downloads/Galera Replication Demystified_how does it work.pdf";
		String dstFile = FilesZipper.zip(srcFolder);
		System.out.println("dstFile is " + dstFile);
		
		UUID uuidPrefix = UUID.randomUUID();
		Path tempDir = null;
		try {
			System.out.println("uuidPrefix is " + uuidPrefix);
			tempDir = Files.createTempDirectory(uuidPrefix.toString());
			System.out.println("tempDir is " + tempDir);
			Path dstFilePath = Paths.get(dstFile);
			String compressedLogFile = Files.move(dstFilePath, tempDir.resolve(dstFilePath.getFileName()), StandardCopyOption.REPLACE_EXISTING).toString();
			System.out.println("compressedLogFile is " + compressedLogFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
