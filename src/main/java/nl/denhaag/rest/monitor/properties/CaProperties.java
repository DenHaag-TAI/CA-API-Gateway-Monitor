package nl.denhaag.rest.monitor.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class CaProperties {
	private static final Logger logger = LogManager.getLogger();
	private static final String propertyFileName = "config/camonitor.properties"; 
	
	public Properties getPropertyValue ()  {
		logger.info ("CaProperties.getPropertyValue: start");
		Properties prop = new Properties();
		InputStream i = getClass().getClassLoader().getResourceAsStream(propertyFileName);
		
		try {
			if (i != null){
				prop.load(i);
			} else {
				logger.fatal("getPropertyValue: property file {} not found", propertyFileName);
				throw new FileNotFoundException("File not found "+propertyFileName);
			}
		} catch (IOException e) {
			logger.fatal("getPropertyValue: property file {} not found", propertyFileName);
		}
		logger.info ("CaProperties.getPropertyValue: end");
		return prop;
	}
}
