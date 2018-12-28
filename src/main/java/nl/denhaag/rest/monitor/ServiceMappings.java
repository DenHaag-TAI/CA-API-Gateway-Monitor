package nl.denhaag.rest.monitor;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceMappings {
	private static final Logger logger = LogManager.getLogger();
	private HttpMapping httpMapping;
	private SoapMapping soapMapping;

	
	/**
	 * @return the httpMapping
	 */
	@XmlElement(name="HttpMapping")
	public HttpMapping getHttpMapping() {
		logger.debug("getHttpMapping:start");
		return httpMapping;
	}
	/**
	 * @param httpMapping the httpMapping to set
	 */
	public void setHttpMapping(HttpMapping httpMapping) {
		logger.debug("setHttpMapping:start");
		this.httpMapping = httpMapping;
		logger.debug("setHttpMapping:end");
	}
	/**
	 * @return the soapMapping
	 */
	@XmlElement(name="SoapMapping")
	public SoapMapping getSoapMapping() {
		logger.debug("getSoapMapping:start");
		return soapMapping;
	}
	/**
	 * @param soapMapping the soapMapping to set
	 */
	public void setSoapMapping(SoapMapping soapMapping) {
		logger.debug("setSoapMapping:start");
		this.soapMapping = soapMapping;
		logger.debug("setSoapMapping:end");
	}

}
