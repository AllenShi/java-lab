package net.sjl.k8s;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class DirectoriesWatchExample {

	private static Map<WatchKey, Path> keyPathMap = new HashMap<>();

	public static void main(String[] args) {

		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();

			Path root = Paths.get("/tmp/watcher");
			registerAllwithSubFolders(watcher, root);

			WatchKey key = null;
			for (;;) {

				// wait for key to be signaled
				try {
					key = watcher.take();
				} catch (InterruptedException x) {
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					// This key is registered only
					// for ENTRY_CREATE events,
					// but an OVERFLOW event can
					// occur regardless if events
					// are lost or discarded.
					if (kind == OVERFLOW) {
						continue;
					}

					// The filename is the context of the event.
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path subPath = (Path) ev.context();

					try {

						Path parent = keyPathMap.get(key);
						Path child = parent.resolve(subPath);
						String type = Files.probeContentType(child);
						System.out.format("A event type is '%s' and file name is '%s' with a type %s of file.%n",
								kind.name(), child, type);
						
						if (ev.kind() == ENTRY_CREATE) {
							registerAllwithSubFolders(watcher, child);
							if(Files.isRegularFile(child, LinkOption.NOFOLLOW_LINKS)) {
								String fileName = subPath.toString();
								if(fileName.endsWith(".all.OK")) {
									System.out.println("Found OK file");
								} else if(fileName.endsWith(".FAILED")) {
									System.out.println("Found FAILED file");
								}
							}
						} 
						
					} catch (IOException x) {
						System.err.println(x);
						continue;
					}
				}

				// Reset the key -- this step is critical if you want to
				// receive further watch events. If the key is no longer valid,
				// the directory is inaccessible so exit the loop.
				boolean valid = key.reset();
				if (!valid) {
					keyPathMap.remove(key);
				}

				if (keyPathMap.isEmpty()) {
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void registerAllwithSubFolders(final WatchService watcher, final Path root) throws IOException {
		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
				keyPathMap.put(key, dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
