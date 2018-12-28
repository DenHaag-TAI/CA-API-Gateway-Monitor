package nl.denhaag.rest.monitor.zip;

import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Zipper {
	
	private static final Logger logger = LogManager.getLogger();
	
	public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
		logger.info("Zipper.pack: start");
		
		Path pad = Paths.get(zipFilePath);
		
		if (Files.exists(pad)){
			//doe niets; zipbestand bestaat al
			return;
		}
			
	    Path p = Files.createFile(Paths.get(zipFilePath));

	    ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
	    try {
	        Path pp = Paths.get(sourceDirPath);
	        Files.walk(pp)
	          .filter(path -> !Files.isDirectory(path))
	          .forEach(path -> {
	              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
	              try {
	                  zs.putNextEntry(zipEntry);
	                  zs.write(Files.readAllBytes(path));
	                  zs.closeEntry();
	            } catch (Exception e) {
	            	logger.error("pack: "+e.getMessage());
	            }
	          });
	    } finally {
	        zs.close();
	    }
	    logger.info("Zipper.pack: end");
	}
}
