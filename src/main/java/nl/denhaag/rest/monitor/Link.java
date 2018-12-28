package nl.denhaag.rest.monitor;

import javax.xml.bind.annotation.XmlAttribute;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Link {
	private static final Logger logger = LogManager.getLogger();
	String rel;
	String uri;
	/**
	 * @return the rel
	 */
	
	@XmlAttribute
	public String getRel() {
		logger.debug("getRel:start");
		return rel;
	}
	/**
	 * @param rel the rel to set
	 */
	public void setRel(String rel) {
		logger.debug("setRel:start");
		this.rel = rel;
		logger.debug("setRel:end");
	}
	/**
	 * @return the uri
	 */
	@XmlAttribute
	public String getUri() {
		logger.debug("getUri:start");
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		logger.debug("setUri:start");
		this.uri = uri;
		logger.debug("setUri:end");
	}
}
