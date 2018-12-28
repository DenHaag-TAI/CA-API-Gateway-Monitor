package nl.denhaag.rest.monitor.index;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IndexHtml {

	private static final Logger logger = LogManager.getLogger();
	private static final String schemaLocation = "http://schemas.denhaag.nl/tw/layer7/layer7.xsd";
	
	
	public void doIt (String dir, Indexer i) throws JAXBException {
		logger.debug("doIt:start");
		File f = new File (dir+System.getProperty("file.separator")+"index.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Indexer.class);
		Marshaller m = jaxbContext.createMarshaller();
		
		m.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
		m.marshal(i, f);
		logger.debug("doIt:end");
	}
}
