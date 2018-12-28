package nl.denhaag.rest.monitor.directory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckIfDirectoryExists extends SimpleFileVisitor<Path>{
	
	private static final Logger logger = LogManager.getLogger();
	
    public static void delete (Path dir, Path file, String pattern, String newname)   {
    	logger.info("CheckIfDirectoryExists.delete: start");
        if (file.toString().startsWith(dir+System.getProperty("file.separator")+pattern) && !file.toString().equals(dir+System.getProperty("file.separator")+newname)) {
        	try {
        		FileUtils.deleteDirectory(file.toFile());
			} catch (IOException e) {
				logger.error("delete:failed to delete directory file.toString() "+e.getMessage());
			}
        }
        logger.info("CheckIfDirectoryExists.delete: end");
    }
}

