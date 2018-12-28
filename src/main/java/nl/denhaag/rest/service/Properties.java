package nl.denhaag.rest.service;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Properties {
	
	private ArrayList<Property> property;
	private static final Logger logger = LogManager.getLogger();

	/**
	 * @return the key
	 */

	/**
	 * @return the property
	 */
	@XmlElement(name="Property")
	public ArrayList<Property> getProperty() {
		logger.debug("getProperty:start");
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(ArrayList<Property> property) {
		logger.debug("setProperty:start");
		this.property = property;
		logger.debug("setProperty:end");
	}


}
