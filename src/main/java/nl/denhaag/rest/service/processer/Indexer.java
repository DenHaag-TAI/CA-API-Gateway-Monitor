package nl.denhaag.rest.service.processer;

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

@XmlRootElement(name="service", namespace="http://schemas.denhaag.nl/tw/layer7")
public class Indexer {
	
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
	private Bestanden file;
	
	
	
	/**
	 * @return the httpMethods
	 */
	public HttpMethod getHttpMethods() {
		return httpMethods;
	}
	/**
	 * @param httpMethods the httpMethods to set
	 */
	public void setHttpMethods(HttpMethod httpMethods) {
		this.httpMethods = httpMethods;
	}
	/**
	 * @return the generationDate
	 */
	public String getGenerationDate() {
		return generationDate;
	}
	/**
	 * @param generationDate the generationDate to set
	 */
	@XmlAttribute
	public void setGenerationDate(String generationDate) {
		this.generationDate = generationDate;
	}
	/**
	 * @return the generatorVersion
	 */
	public String getGeneratorVersion() {
		return generatorVersion;
	}
	/**
	 * @param generatorVersion the generatorVersion to set
	 */
	@XmlAttribute
	public void setGeneratorVersion(String generatorVersion) {
		this.generatorVersion = generatorVersion;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the policyVersion
	 */
	public String getPolicyVersion() {
		return policyVersion;
	}
	/**
	 * @param policyVersion the policyVersion to set
	 */
	@XmlElement
	public void setPolicyVersion(String policyVersion) {
		this.policyVersion = policyVersion;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	@XmlElement
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	@XmlElement
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the policyManagerPath
	 */
	public String getPolicyManagerPath() {
		return policyManagerPath;
	}
	/**
	 * @param policyManagerPath the policyManagerPath to set
	 */
	@XmlElement
	public void setPolicyManagerPath(String policyManagerPath) {
		this.policyManagerPath = policyManagerPath;
	}
	/**
	 * @return the resolutionPath
	 */
	public String getResolutionPath() {
		return resolutionPath;
	}
	/**
	 * @param resolutionPath the resolutionPath to set
	 */
	@XmlElement
	public void setResolutionPath(String resolutionPath) {
		this.resolutionPath = resolutionPath;
	}
	/**
	 * @return the protectedEndpoint
	 */
	public String getProtectedEndpoint() {
		return protectedEndpoint;
	}
	/**
	 * @param protectedEndpoint the protectedEndpoint to set
	 */
	@XmlElement
	public void setProtectedEndpoint(String protectedEndpoint) {
		this.protectedEndpoint = protectedEndpoint;
	}
	/**
	 * @return the wsSecurity
	 */
	public String getWsSecurity() {
		return wsSecurity;
	}
	/**
	 * @param wsSecurity the wsSecurity to set
	 */
	@XmlElement
	public void setWsSecurity(String wsSecurity) {
		this.wsSecurity = wsSecurity;
	}
	/**
	 * @return the soap
	 */
	public String getSoap() {
		return soap;
	}
	/**
	 * @param soap the soap to set
	 */
	@XmlElement
	public void setSoap(String soap) {
		this.soap = soap;
	}
	/**
	 * @return the soapVersion
	 */
	public String getSoapVersion() {
		return soapVersion;
	}
	/**
	 * @param soapVersion the soapVersion to set
	 */
	@XmlElement
	public void setSoapVersion(String soapVersion) {
		this.soapVersion = soapVersion;
	}
	/**
	 * @return the internal
	 */
	public String getInternal() {
		return internal;
	}
	/**
	 * @param internal the internal to set
	 */
	@XmlElement
	public void setInternal(String internal) {
		this.internal = internal;
	}
	/**
	 * @return the file
	 */
	public Bestanden getBestanden() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	@XmlElement(name="files")
	public void setBestanden(Bestanden file) {
		this.file = file;
	}
}
