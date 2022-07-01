package net.sjl.fragrans.fs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class ApacheFileSystemWatcher {
	private List<DefaultFileMonitor> fileMonitors;

	public ApacheFileSystemWatcher() {
		this.fileMonitors = new ArrayList<>();
	}

	public void watchDir(File file) throws IOException {

		FileSystemManager fileSystemManager = VFS.getManager();
		FileObject dirToWatchFO = null;
		dirToWatchFO = fileSystemManager.resolveFile(file.getAbsolutePath());
		
		DefaultFileMonitor fileMonitor = new DefaultFileMonitor(new DefaultFileSystemListener());

		fileMonitor.setRecursive(true);
		fileMonitor.addFile(dirToWatchFO);
		fileMonitor.start();

		this.fileMonitors.add(fileMonitor);
	}

	public void shutdown() {
		for (DefaultFileMonitor fm : this.fileMonitors) {
			fm.stop();
			fm = null;
		}
	}

	public static class DefaultFileSystemListener implements FileListener {

		public void fileChanged(FileChangeEvent event) throws Exception {
			FileObject createdFO = event.getFile();
			System.out.println("changed file base name is " + createdFO.getName().getBaseName());
			System.out.println("changed file path is " + createdFO.getName().getPath());
		}

		public void fileDeleted(FileChangeEvent event) throws Exception {
			FileObject createdFO = event.getFile();
			System.out.println("deleted file base name is " + createdFO.getName().getBaseName());
			System.out.println("deleted file path is " + createdFO.getName().getPath());
		}

		public void fileCreated(FileChangeEvent event) throws Exception {
			FileObject createdFO = event.getFile();
			System.out.println("created file base name is " + createdFO.getName().getBaseName());
			System.out.println("created file path is " + createdFO.getName().getPath());
		}
	}
	
	public static void main(String[] args) {
		OptionParser parser = new OptionParser("t:");
		parser.accepts("target").withRequiredArg();

		OptionSet options = parser.parse(args);
		
		String td = "/opt/ibm/ecc/ecm_staging_deploy/cfg";
		if((options.has("t") || options.has("target")) && ((options.hasArgument("t")) || (options.hasArgument("target")))) {
			td = options.hasArgument("t") ? (String)options.valueOf("t") : (String)options.valueOf("target");
			System.out.println("td is " + td);
		}
		
		final String targetDir = td;
		Executor runner = Executors.newFixedThreadPool(1);
		runner.execute(new Runnable() {

			@Override
			public void run() {
				ApacheFileSystemWatcher watcher = new ApacheFileSystemWatcher();
				File target = new File(targetDir);
				try {
					watcher.watchDir(target);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

}
