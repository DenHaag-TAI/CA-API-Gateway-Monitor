package nl.denhaag.rest.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Verb {
	
	private String verb;
	private static final Logger logger = LogManager.getLogger();
	/**
	 * @return the verb
	 */
	public String getVerb() {
		logger.debug("getVerb:start");
		return verb;
	}

	/**
	 * @param verb the verb to set
	 */
	public void setVerb(String verb) {
		logger.debug("setVerb:start");
		this.verb = verb;
		logger.debug("setVerb:end");
	}

}
