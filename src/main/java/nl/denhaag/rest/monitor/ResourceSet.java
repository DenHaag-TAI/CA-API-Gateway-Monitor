package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceSet {
	private static final Logger logger = LogManager.getLogger();
	private ArrayList <RResource> rrs;
	private String tag;
	private String rootUrl;

	/**
	 * @return the rrs
	 */
	@XmlElement(name="Resource")
	public ArrayList<RResource> getRrs() {
		logger.debug("getRrs:start");
		return rrs;
	}

	/**
	 * @return the tag
	 */
	@XmlAttribute
	public String getTag() {
		logger.debug("getTag:start");
		return tag;
		
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		logger.debug("setTag:start");
		this.tag = tag;
		logger.debug("setTag:end");
	}

	/**
	 * @return the rootUrl
	 */
	@XmlAttribute(name="rootUrl")
	public String getRootUrl() {
		logger.debug("getRootUrl:start");
		return rootUrl;
	}

	/**
	 * @param rootUrl the rootUrl to set
	 */
	public void setRootUrl(String rootUrl) {
		logger.debug("setRootUrl:start");
		this.rootUrl = rootUrl;
		logger.debug("setRootUrl:end");
	}

	/**
	 * @param rrs the rrs to set
	 */
	public void setRrs(ArrayList<RResource> rrs) {
		logger.debug("setRrs:start");
		this.rrs = rrs;
		logger.debug("setRrs:end");
	}
}
