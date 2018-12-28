package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcesResource {
	
	private static final Logger logger = LogManager.getLogger();
	private String type;
	private String version;
	private String sourceUrl;
	private String description;
	/**
	 * @return the description
	 */
	@XmlValue
	public String getDescription() {
		logger.debug("getDescription:start");
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		logger.debug("setDescription:start");
		this.description = description;
		logger.debug("setDescription:end");
	}
	/**
	 * @return the type
	 */
	@XmlAttribute
	public String getType() {
		logger.debug("getType:start");
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		logger.debug("setType:start");
		this.type = type;
		logger.debug("setType:end");
	}
	/**
	 * @return the version
	 */
	@XmlAttribute
	public String getVersion() {
		logger.debug("getVersion:start");
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		logger.debug("setVersion:start");
		this.version = version;
		logger.debug("setVersion:end");
	}
	
	@XmlAttribute(name="sourceUrl")
	public String getSourceUrl() {
		logger.debug("getSourceUrl:start");
		return sourceUrl;
	}
	/**
	 * @param type the type to set
	 */
	public void setSourceUrl(String sourceUrl) {
		logger.debug("setSourceUrl:start");
		this.sourceUrl = sourceUrl;
		logger.debug("setSourceUrl:end");
	}

}
