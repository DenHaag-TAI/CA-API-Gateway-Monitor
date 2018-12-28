package nl.denhaag.rest.monitor.index;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinalArray {
	
	private static final Logger logger = LogManager.getLogger();
	
	private ArrayList<Bestand> file;

	/**
	 * @return the file
	 */
	public ArrayList<Bestand> getFile() {
		logger.debug("getFile:start");
		return file;
	}

	/**
	 * @param file the file to set
	 */
	@XmlElement(name="file")
	public void setFile(ArrayList<Bestand> file) {
		logger.debug("setFile:start");
		this.file = file;
		logger.debug("setFile:end");
	}
	
	

}
