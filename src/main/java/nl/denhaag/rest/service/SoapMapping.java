package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoapMapping {
	
	private static final Logger logger = LogManager.getLogger();
	@XmlElement(name="Lax")
	private String Lax;

	/**
	 * @return the lax
	 */
	public String getLax() {
		logger.debug("getLax:start");
		return Lax;
	}

	/**
	 * @param lax the lax to set
	 */
	public void setLax(String lax) {
		logger.debug("setLax:start");
		Lax = lax;
		logger.debug("setLax:end");
	}

}
