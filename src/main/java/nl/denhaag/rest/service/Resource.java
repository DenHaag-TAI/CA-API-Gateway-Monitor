package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlElement;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Resource {

	private static final Logger logger = LogManager.getLogger();
	private Service service;

	/**
	 * @return the service
	 */
	@XmlElement(name="Service")
	public Service getService() {
		logger.debug("getService:start");
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(Service service) {
		logger.debug("setService(Service:start");
		this.service = service;
		logger.debug("setService(Service:end");
	}

}
