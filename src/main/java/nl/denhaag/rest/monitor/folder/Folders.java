package nl.denhaag.rest.monitor.folder;

//import java.io.File;


//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.ArrayList;
//import java.util.Optional;

//import javax.xml.bind.JAXB;
//import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerException;

//import nl.denhaag.rest.monitor.List;
//import nl.denhaag.rest.monitor.directory.CheckIfDirectoryExists;
//import nl.denhaag.rest.monitor.enabled.ServiceEnabledChecker;
//import nl.denhaag.rest.monitor.properties.CaProperties;
//import nl.denhaag.rest.monitor.remove.CurrentServices;
//import nl.denhaag.rest.monitor.zip.Zipper;
//import nl.denhaag.rest.service.Item;
//import nl.denhaag.rest.service.processer.IndexHtml;
//import nl.denhaag.rest.service.processer.Indexer;
//import nl.denhaag.rest.service.processer.Processor;

//import nl.denhaag.rest.transformations.Transform;

//import nl.denhaag.rest.monitor.Link;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.io.FileUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.xml.sax.SAXException;


@XmlRootElement(name="Item")
@XmlType(namespace="http://ns.l7tech.com/2010/04/gateway-management")
public class Folders {
	
	private static final Logger logger = LogManager.getLogger();
	private String name;
	private String type;
	private String timestamp;
	private ArrayList<Link> links;
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

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

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
		 * @throws ParserConfigurationException 
		 * @throws SAXException 
		 */
}
