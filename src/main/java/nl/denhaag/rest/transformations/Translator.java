package nl.denhaag.rest.transformations;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Translator {
	private static final Logger logger = LogManager.getLogger();

	/*Vertalen van een string naar Base64 */
	public String translate (String base64) throws UnsupportedEncodingException{
		logger.info("translate: start");
		byte[] decoded = Base64.decodeBase64(base64);
		logger.info("translate: start");
		return(new String(decoded, "UTF-8"));
	}

}