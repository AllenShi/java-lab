package net.sjl.fragrans.fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;

public class FileTailer {

	public static class MyReader implements Runnable {

		private String file;

		public MyReader(String file) {
			this.file = file;
		}

		@Override
		public void run() {
			try (Stream<String> lines = Files.lines(Paths.get(file))) {

				lines.forEach(line -> {
					try {
						System.out.println(line);
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});

			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		}

	}

	public static class MyWriter implements Runnable {

		private String file;

		public MyWriter(String file) {
			this.file = file;
		}

		@Override
		public void run() {
			int lineNum = 1;
			try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
				while (lineNum < 10) {
					writer.write(Integer.toString(lineNum) + "\n");
					lineNum++;
					Thread.sleep(new Random().nextInt(300));
					writer.flush();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
    private static class MyListener extends TailerListenerAdapter
    {
        @Override
        public void handle(String line)
        {
            System.out.println(line);
        }
        
        @Override
        public void endOfFileReached() {
        	System.out.println("EOF");
        }
    }

	public static void main(String[] args) {
		String file = "/tmp/myrw.log";
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		// ExecutorService service1 = Executors.newSingleThreadExecutor();
		// service1.execute(new MyWriter(file));
		service.execute(new MyWriter(file));

//		ExecutorService service2 = Executors.newSingleThreadExecutor();
//		service2.execute(new MyReader(file));
		
//        Tailer.create(new File(file), new MyListener(), 2500);
        
		Tailer tailer = new Tailer(new File(file), new MyListener(), 2500);
        // ExecutorService service3 = Executors.newSingleThreadExecutor();
		// service3.execute(tailer);
		service.execute(tailer);
//		service1.shutdown();
		// service3.shutdown();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tailer.stop();
		service.shutdownNow();
	}

}
