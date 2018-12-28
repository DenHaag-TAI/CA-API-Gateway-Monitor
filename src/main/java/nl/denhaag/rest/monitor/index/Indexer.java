package nl.denhaag.rest.monitor.index;

//import java.io.File;
//
//import java.util.ArrayList;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@XmlRootElement(name="service", namespace="http://schemas.denhaag.nl/tw/layer7")
public class Indexer {
	
	private static final Logger logger = LogManager.getLogger();
	private String generationDate;
	private String generatorVersion;
	private String id;
	private String name;
	private String policyVersion;
	private String version;
	private String enabled;
	private String policyManagerPath;
	private String resolutionPath;
	private String protectedEndpoint;
	private String wsSecurity;
	private String soap;
	private String soapVersion;
	private String internal;
	
	private HttpMethod httpMethods;
	private FinalArray file;
	
	
	
	/**
	 * @return the httpMethods
	 */
	public HttpMethod getHttpMethods() {
		logger.debug("getHttpMethods:start");
		return httpMethods;
	}
	/**
	 * @param httpMethods the httpMethods to set
	 */
	public void setHttpMethods(HttpMethod httpMethods) {
		logger.debug("setHttpMethods:start");
		this.httpMethods = httpMethods;
		logger.debug("setHttpMethods:end");
	}
	/**
	 * @return the generationDate
	 */
	public String getGenerationDate() {
		logger.debug("getGenerationDate:start");
		return generationDate;
	}
	/**
	 * @param generationDate the generationDate to set
	 */
	@XmlAttribute
	public void setGenerationDate(String generationDate) {
		logger.debug("setGenerationDate:start");
		this.generationDate = generationDate;
		logger.debug("setGenerationDate:end");
	}
	/**
	 * @return the generatorVersion
	 */
	public String getGeneratorVersion() {
		logger.debug("getGeneratorVersion:start");
		return generatorVersion;
	}
	/**
	 * @param generatorVersion the generatorVersion to set
	 */
	@XmlAttribute
	public void setGeneratorVersion(String generatorVersion) {
		logger.debug("setGeneratorVersion:start");
		this.generatorVersion = generatorVersion;
		logger.debug("setGeneratorVersion:end");
	}
	/**
	 * @return the id
	 */
	public String getId() {
		logger.debug("getId:start");
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@XmlElement
	public void setId(String id) {
		logger.debug("setId:start");
		this.id = id;
		logger.debug("setId:end");
	}
	/**
	 * @return the name
	 */
	public String getName() {
		logger.debug("getName:start");
		return name;
	}
	/**
	 * @param name the name to set
	 */
	@XmlElement
	public void setName(String name) {
		logger.debug("setName:start");
		this.name = name;
		logger.debug("setName:end");
	}
	/**
	 * @return the policyVersion
	 */
	public String getPolicyVersion() {
		logger.debug("getPolicyVersion:start");
		return policyVersion;
	}
	/**
	 * @param policyVersion the policyVersion to set
	 */
	@XmlElement
	public void setPolicyVersion(String policyVersion) {
		logger.debug("setPolicyVersion:start");
		this.policyVersion = policyVersion;
		logger.debug("setPolicyVersion:end");
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		logger.debug("getVersion:start");
		return version;
	}
	/**
	 * @param version the version to set
	 */
	@XmlElement
	public void setVersion(String version) {
		logger.debug("setVersion:start");
		this.version = version;
		logger.debug("setVersion:end");
	}
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		logger.debug("getEnabled:start");
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	@XmlElement
	public void setEnabled(String enabled) {
		logger.debug("setEnabled:start");
		this.enabled = enabled;
		logger.debug("setEnabled:end");
	}
	/**
	 * @return the policyManagerPath
	 */
	public String getPolicyManagerPath() {
		logger.debug("getPolicyManagerPath:start");
		return policyManagerPath;
	}
	/**
	 * @param policyManagerPath the policyManagerPath to set
	 */
	@XmlElement
	public void setPolicyManagerPath(String policyManagerPath) {
		logger.debug("setPolicyManagerPath:start");
		this.policyManagerPath = policyManagerPath;
		logger.debug("setPolicyManagerPath:start");
	}
	/**
	 * @return the resolutionPath
	 */
	public String getResolutionPath() {
		logger.debug("getResolutionPath:start");
		return resolutionPath;
	}
	/**
	 * @param resolutionPath the resolutionPath to set
	 */
	@XmlElement
	public void setResolutionPath(String resolutionPath) {
		logger.debug("setResolutionPath:start");
		this.resolutionPath = resolutionPath;
		logger.debug("setResolutionPath:end");
	}
	/**
	 * @return the protectedEndpoint
	 */
	public String getProtectedEndpoint() {
		logger.debug("getProtectedEndpoint:start");
		return protectedEndpoint;
	}
	/**
	 * @param protectedEndpoint the protectedEndpoint to set
	 */
	@XmlElement
	public void setProtectedEndpoint(String protectedEndpoint) {
		logger.debug("setProtectedEndpoint:start");
		this.protectedEndpoint = protectedEndpoint;
		logger.debug("setProtectedEndpoint:end");
	}
	/**
	 * @return the wsSecurity
	 */
	public String getWsSecurity() {
		logger.debug("getWsSecurity:start");
		return wsSecurity;
	}
	/**
	 * @param wsSecurity the wsSecurity to set
	 */
	@XmlElement
	public void setWsSecurity(String wsSecurity) {
		logger.debug("setWsSecurity:start");
		this.wsSecurity = wsSecurity;
		logger.debug("setWsSecurity:end");
	}
	/**
	 * @return the soap
	 */
	public String getSoap() {
		logger.debug("getSoap:start");
		return soap;
	}
	/**
	 * @param soap the soap to set
	 */
	@XmlElement
	public void setSoap(String soap) {
		logger.debug("setSoap:start");
		this.soap = soap;
		logger.debug("setSoap:end");
	}
	/**
	 * @return the soapVersion
	 */
	public String getSoapVersion() {
		logger.debug("getSoapVersion:start");
		return soapVersion;
	}
	/**
	 * @param soapVersion the soapVersion to set
	 */
	@XmlElement
	public void setSoapVersion(String soapVersion) {
		logger.debug("setSoapVersion:start");
		this.soapVersion = soapVersion;
		logger.debug("setSoapVersion:end");
	}
	/**
	 * @return the internal
	 */
	public String getInternal() {
		logger.debug("getInternal:start");
		return internal;
	}
	/**
	 * @param internal the internal to set
	 */
	@XmlElement
	public void setInternal(String internal) {
		logger.debug("setInternal:start");
		this.internal = internal;
		logger.debug("setInternal:end");
	}
	/**
	 * @return the file
	 */
	public FinalArray getBestanden() {
		logger.debug("getBestanden:start");
		return file;
	}
	/**
	 * @param file the file to set
	 */
	@XmlElement(name="files")
	public void setBestanden(FinalArray file) {
		logger.debug("setBestanden:start");
		this.file = file;
		logger.debug("setBestanden:end");
	}
}
