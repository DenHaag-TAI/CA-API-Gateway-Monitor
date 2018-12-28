package nl.denhaag.rest.monitor.remove;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CurrentServices {
	
	List <String>  currentServices = new ArrayList<String>();
	private static final Logger logger = LogManager.getLogger();
	
	public void  setAll (String dir) throws IOException {
		logger.info("CurrentServices.setALL: start");	
		
		File[] currentFServices = new File(dir).listFiles(File::isDirectory);
		
		for (File f : currentFServices) {
			currentServices.add(f.getName());
		}
		logger.info("CurrentServices.setALL: end");
	}

	/**
	 * @return the currentServices
	 */
	public List <String> getCurrentServices() {
		logger.info("CurrentServices.getCurrentServices: return");
		return currentServices;
	}
	
	
	public void remove (String service) {
		logger.info("CurrentServices.remove: start");
		currentServices.remove(service);
		logger.info("CurrentServices.remove: end");
	}
}
