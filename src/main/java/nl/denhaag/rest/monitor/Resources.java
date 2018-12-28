package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Resources {
	
	private ArrayList<ResourceSet> resourceset;
	private static final Logger logger = LogManager.getLogger();
	/**
	 * @return the resourceset
	 */
	@XmlElement(name="ResourceSet")
	public ArrayList<ResourceSet> getResourceset() {
		logger.debug("getResourceset:start");
		return resourceset;
	}

	/**
	 * @param resourceset the resourceset to set
	 */
	public void setResourceset(ArrayList<ResourceSet> resourceset) {
		logger.debug("setResourceset:start");
		this.resourceset = resourceset;
		logger.debug("setResourceset:end");
	}
}
