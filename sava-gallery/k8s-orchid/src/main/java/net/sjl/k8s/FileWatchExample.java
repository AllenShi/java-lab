package net.sjl.k8s;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatchExample {

	public static void main(String[] args) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			Path dir = Paths.get("/tmp/");
			
			WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			
			for (;;) {

			    // wait for key to be signaled
			    try {
			        key = watcher.take();
			    } catch (InterruptedException x) {
			        return;
			    }

			    for (WatchEvent<?> event: key.pollEvents()) {
			        WatchEvent.Kind<?> kind = event.kind();

			        // This key is registered only
			        // for ENTRY_CREATE events,
			        // but an OVERFLOW event can
			        // occur regardless if events
			        // are lost or discarded.
			        if (kind == OVERFLOW) {
			            continue;
			        }

			        // The filename is the
			        // context of the event.
			        WatchEvent<Path> ev = (WatchEvent<Path>)event;
			        Path filename = ev.context();

			        // Verify that the new
			        //  file is a text file.
			        try {
			            // Resolve the filename against the directory.
			            // If the filename is "test" and the directory is "foo",
			            // the resolved name is "test/foo".
			            Path child = dir.resolve(filename);
			            String type = Files.probeContentType(child);
			            System.out.format("A event type is '%s' and file name is '%s' with a type %s of file.%n", kind.name(), child, type);
			
			        } catch (IOException x) {
			            System.err.println(x);
			            continue;
			        }
			    }

			    // Reset the key -- this step is critical if you want to
			    // receive further watch events.  If the key is no longer valid,
			    // the directory is inaccessible so exit the loop.
			    boolean valid = key.reset();
			    if (!valid) {
			        break;
			    }
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
