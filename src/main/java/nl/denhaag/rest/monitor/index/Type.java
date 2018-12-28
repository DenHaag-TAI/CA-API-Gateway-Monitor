package nl.denhaag.rest.monitor.index;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Type {
	private static final Logger logger = LogManager.getLogger();
	
	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		logger.debug("getType:start");
		return type;
	}

	/**
	 * @param type the type to set
	 */
	@XmlAttribute(name="type")
	public void setType(String type) {
		logger.debug("setType:start");
		this.type = type;
		logger.debug("setType:end");
	}
}
