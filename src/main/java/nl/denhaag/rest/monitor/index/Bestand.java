package nl.denhaag.rest.monitor.index;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bestand {
	private static final Logger logger = LogManager.getLogger();
	
	private String bestandsnaam;
	private String type;
	private String root;
/**
	 * @return the root
	 */
	public String getRoot() {
		logger.debug("getRoot:start");
		return root;
	}
	/**
	 * @param root the root to set
	 */
	@XmlAttribute(name="root")
	public void setRoot(String root) {
		logger.debug("setRoot:start");
		this.root = root;
		logger.debug("setRoot:end");
	}
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
	/**
	 * @return the bestandsnaam
	 */
	@XmlValue
	public String getBestandsnaam() {
		logger.debug("getBestandsnaam:start");
		return bestandsnaam;
	}
	/**
	 * @param bestandsnaam the bestandsnaam to set
	 */
//	
	public void setBestandsnaam(String bestandsnaam) {
		logger.debug("setBestandsnaam:start");
		this.bestandsnaam = bestandsnaam;
		logger.debug("setBestandsnaam:end");
	}
	/**
	 * @return the type
	 */
	
}
