package nl.denhaag.rest.service.processer;

import java.util.ArrayList;


import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bestanden {
	
//	private static final Logger logger = LogManager.getLogger();
	
	private ArrayList<Bestand> file;

	/**
	 * @return the file
	 */
	public ArrayList<Bestand> getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	@XmlElement(name="file")
	public void setFile(ArrayList<Bestand> file) {
		this.file = file;
	}
}
