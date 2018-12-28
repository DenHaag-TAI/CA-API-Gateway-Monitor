package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@XmlRootElement(name="List")
@XmlType(namespace="http://ns.l7tech.com/2010/04/gateway-management")
public class List {
	private static final Logger logger = LogManager.getLogger();
	private String name;
	private String type;
	private String timestamp;
	private ArrayList<Link> links;
	private ArrayList<Item> items;

	@XmlElement(name="Link")
	public ArrayList<Link> getLinks(){
		logger.debug("getLinks:start");
		return links;
	}

	public void setLinks(ArrayList<Link> links){
		logger.debug("setLinks:start");
		this.links = links;
		logger.debug("setLinks:end");
	}
	
	@XmlElement(name="Item")
	public ArrayList<Item> getItems(){
		logger.debug("getItems:start");
		return items;
	}

	public void setItems(ArrayList<Item> items){
		logger.debug("setItems:start");
		this.items = items;
		logger.debug("setItems:end");
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
	 * @return the linkSelf
	 */
}
