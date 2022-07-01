package net.sjl.fragrans.ar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FilesZipper {

	public static String zip(String srcFolder) {
		String dstFile = srcFolder.concat(".zip");
		try (FileOutputStream fos = new FileOutputStream(dstFile);
				ZipOutputStream zos = new ZipOutputStream(fos)) {

			Path sourcePath = Paths.get(srcFolder);
			String output = Paths.get(dstFile).toString();
			System.out.println("output " + output);
			// using WalkFileTree to traverse directory
			Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
						throws IOException {
					// it starts with the source folder so skipping that
					if (!sourcePath.equals(dir)) {
						System.out.println("DIR " + dir);
						zos.putNextEntry(new ZipEntry(sourcePath.relativize(dir).toString() + "/"));

						zos.closeEntry();
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					if(sourcePath.equals(file)) {
						zos.putNextEntry(new ZipEntry(file.getFileName().toString()));
					} else {
						zos.putNextEntry(new ZipEntry(sourcePath.relativize(file).toString()));
					}
					Files.copy(file, zos);
					zos.closeEntry();
					return FileVisitResult.CONTINUE;
				}
			});
			
			return output;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
