package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@XmlRootElement(name="Item")
@XmlType(namespace="http://ns.l7tech.com/2010/04/gateway-management")
public class Item {
	private static final Logger logger = LogManager.getLogger();
	private String name;
	private String id;
	private String type;
	private String timestamp;
	private Resource resource;
	
	
	/**
	 * @return the resource
	 */
	@XmlElement(name="Resource")
	public Resource getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
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
}
