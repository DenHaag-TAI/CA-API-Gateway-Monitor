package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Service {
	private static final Logger logger = LogManager.getLogger();
	private String version;
	private String id;
	private ServiceDetail serviceDetail;
	private Resources resources;
	
	
	
	
	@XmlAttribute
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
	
	/**
	 * @return the serviceDetail
	 */
	@XmlElement(name="ServiceDetail")
	public ServiceDetail getServiceDetail() {
		logger.debug("getServiceDetail:start");
		return serviceDetail;
	}
	/**
	 * @param serviceDetail the serviceDetail to set
	 */
	public void setServiceDetail(ServiceDetail serviceDetail) {
		logger.debug("setServiceDetail:start");
		this.serviceDetail = serviceDetail;
		logger.debug("setServiceDetail:end");
	}

	@XmlElement(name="Resources")
	public Resources getResources() {
		logger.debug("getResources:start");
		return resources;
	}
	/**
	 * @param resources the resources to set
	 */
	public void setResources(Resources resources) {
		logger.debug("setResources:start");
		this.resources = resources;
		logger.debug("setResources:end");
	}


}
