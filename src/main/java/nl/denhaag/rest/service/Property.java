package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Property {
	
	private static final Logger logger = LogManager.getLogger();
	private String booleanValue;
	private String key;
	
	@XmlAttribute
	public String getKey() {
		logger.debug("getKey:start");
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		logger.debug("setKey:start");
		this.key = key;
		logger.debug("setKey:end");
	}
	
	/**
	 * @return the booleanValue
	 */
	@XmlElement(name="BooleanValue")
	public String getBooleanValue() {
		logger.debug("getBooleanValue:start");
		return booleanValue;
	}
	/**
	 * @param booleanValue the booleanValue to set
	 */
	public void setBooleanValue(String booleanValue) {
		logger.debug("setBooleanValue:start");
		this.booleanValue = booleanValue;
		logger.debug("setBooleanValue:end");
	}
	/**
	 * @return the longValue
	 */
	@XmlElement(name="LongValue")
	public String getLongValue() {
		logger.debug("getLongValue:start");
		return LongValue;
	}
	/**
	 * @param longValue the longValue to set
	 */
	public void setLongValue(String longValue) {
		logger.debug("setLongValue:start");
		LongValue = longValue;
		logger.debug("setLongValue:end");
	}
	
	private String LongValue;

}
