package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpMapping {
	
	private static final Logger logger = LogManager.getLogger();
	private String urlPattern;
	private Verbs verbs; 

	/**
	 * @return the verbs
	 */
	@XmlElement(name="Verbs")
	public Verbs getVerbs() {
		logger.debug("getVerbs:start");
		return verbs;
	}

	/**
	 * @param verbs the verbs to set
	 */
	public void setVerbs(Verbs verbs) {
		logger.debug("setVerbs:start");
		this.verbs = verbs;
		logger.debug("setVerbs:end");
	}

	/**
	 * @return the urlPattern
	 */
	@XmlElement(name="UrlPattern")
	public String getUrlPattern() {
		logger.debug("getUrlPattern:start");
		return urlPattern;
	}

	/**
	 * @param urlPattern the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		logger.debug("setUrlPattern:start");
		this.urlPattern = urlPattern;
		logger.debug("setUrlPattern:end");
	}

}
