package nl.denhaag.rest.monitor.index;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpMethod {
	private static final Logger logger = LogManager.getLogger();
	private ArrayList <String> httpMethod;

	/**
	 * @return the httpMethod
	 */
	public ArrayList<String> getHttpMethod() {
		logger.debug("getHttpMethod:start");
		return httpMethod;
	}

	/**
	 * @param httpMethod the httpMethod to set
	 */
	@XmlElement
	public void setHttpMethod(ArrayList<String> httpMethod) {
		logger.debug("setHttpMethod:start");
		this.httpMethod = httpMethod;
		logger.debug("setHttpMethod:end");
	}

}
