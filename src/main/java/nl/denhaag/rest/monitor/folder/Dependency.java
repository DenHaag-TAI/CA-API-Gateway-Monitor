package nl.denhaag.rest.monitor.folder;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dependency {
	
	private static final Logger logger = LogManager.getLogger();
	private String name;
	private String type;
	private String id;
	private Dependencies dependencies;
	
	@XmlElement(name="Dependencies")
	public Dependencies getDependencies() {
		return dependencies;
	}
	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(Dependencies dependencies) {
		this.dependencies = dependencies;
	}

	
	/**
	 * @return the serviceList
	 */
	@XmlElement(name="Name")
	public String getName() {
		logger.debug("getName:start");
		return name;
	}
	/**
	 * @param serviceList the serviceList to set
	 */
	public void setName(String name) {
		logger.debug("setName:start");
		this.name = name;
		logger.debug("setName:end");
	}
	/**
	 * @return the type
	 */
	@XmlElement(name="Type")
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
	 * @return the timestamp
	 */
	@XmlElement(name="Id")
	public String getId() {
		logger.debug("getTimestamp:start");
		return id;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setId(String id) {
		logger.debug("setTimestamp:start");
		this.id = id;
		logger.debug("setTimestamp:end");
	}


}
