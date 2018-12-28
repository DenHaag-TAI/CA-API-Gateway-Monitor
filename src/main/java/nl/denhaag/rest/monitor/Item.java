package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Item {
	
	private static final Logger logger = LogManager.getLogger();
	private String name;
	private String id;
	private String type;
	private String timestamp;
	private ArrayList<Resource> resources;
	/**
	 * @return the id
	 */
	@XmlElement(name="Id")
	public String getId() {
		logger.debug("getId:start");
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		logger.debug("setId:start");
		this.id = id;
		logger.debug("setId:end");
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
	@XmlElement(name="TimeStamp")
	public String getTimestamp() {
		logger.debug("getTimestamp:start");
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		logger.debug("setTimestamp:start");
		this.timestamp = timestamp;
		logger.debug("setTimestamp:end");
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
	
	@XmlElement(name="Resource")
	public ArrayList<Resource> getResources(){
		logger.debug("getResources:start");
		return resources;
	}

	public void setResources(ArrayList<Resource> resources){
		logger.debug("setResources:start");
		this.resources = resources;
		logger.debug("setName:end");
	}
}
