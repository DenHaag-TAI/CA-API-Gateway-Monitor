package nl.denhaag.rest.service;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceDetail {
	private static final Logger logger = LogManager.getLogger();
//	private String id;
	private String version;
//	private String name;
	private String enabled;
	private String folderId;
	private ServiceMappings serviceMappings;
	private Properties properties;
	
//	/**
//	 * @return the id
//	 */
//	@XmlAttribute
//	public String getId() {
//		logger.debug("getId:start");
//		return id;
//	}
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(String id) {
//		logger.debug("setId:start");
//		this.id = id;
//		logger.debug("setId:end");
//	}
//	
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
//	/**
//	 * @return the name
//	 */
//	@XmlElement(name="Name")
//	public String getName() {
//		logger.debug("getName:start");
//		return name;
//	}
//	/**
//	 * @param name the name to set
//	 */
//	public void setName(String name) {
//		logger.debug("setName:start");
//		this.name = name;
//		logger.debug("setName:end");
//	}
	/**
	 * @return the enabled
	 */
	@XmlElement(name="Enabled")
	public String getEnabled() {
		logger.debug("getEnabled:start");
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		logger.debug("setEnabled:start");
		this.enabled = enabled;
		logger.debug("setEnabled:end");
	}
	
	@XmlAttribute
	public String getFolderId() {
		logger.debug("getFolderId:start");
		return folderId;
	}
	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		logger.debug("setFolderId:start");
		this.folderId = folderId;
		logger.debug("setFolderId:end");
	}
	
	/**
	 * @return the serviceMappings
	 */
	@XmlElement(name="ServiceMappings")
	public ServiceMappings getServiceMappings() {
		logger.debug("getServiceMappings:start");
		return serviceMappings;
	}
	/**
	 * @param serviceMappings the serviceMappings to set
	 */
	public void setServiceMappings(ServiceMappings serviceMappings) {
		logger.debug("setServiceMappings:start");
		this.serviceMappings = serviceMappings;
		logger.debug("setServiceMappings:end");
	}
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		logger.debug("getProperties:start");
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	@XmlElement(name="Properties")
	public void setProperties(Properties properties) {
		logger.debug("setProperties:start");
		this.properties = properties;
		logger.debug("setProperties:end");
	}



}
