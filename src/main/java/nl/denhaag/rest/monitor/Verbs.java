package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Verbs {
	
	private static final Logger logger = LogManager.getLogger();
	private ArrayList<String> verb;

	/**
	 * @return the verb
	 */
	@XmlElement(name="Verb")
	public ArrayList<String> getVerb() {
		logger.debug("getVerb:start");
		return verb;
	}

	/**
	 * @param verb the verb to set
	 */
	public void setVerb(ArrayList<String> verb) {
		logger.debug("setVerb:start");
		this.verb = verb;
		logger.debug("setVerb:end");
	}
}
