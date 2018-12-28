package nl.denhaag.rest.transformations;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlString {
	private static final Logger logger = LogManager.getLogger();
	private static final String base64StringSearch = "<L7p:Base64Expression stringValue=\".*?\"/>";
	private static final String base64String = "<L7p:Base64Expression stringValue=\"";
	
	/*Vertalen van special characters in xml */
	public String escape (String str){
		logger.info("escape:start");	
//		str.replace("\"", "&quot;").replace("`", "&apos;").replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
//		logger.info("escape:end");
		return str.replace("\"", "&quot;").replace("`", "&apos;").replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;");
	}
	
	/*Waarden in base64 vinden*/
	private String replace (String xml) throws UnsupportedEncodingException {
		logger.info("escape:start");
		String base64 = "";
		Translator t = new Translator();
		Pattern p = Pattern.compile(base64StringSearch);
		Matcher m = p.matcher(xml);
		String replacedXml = xml;
		int j=0;
		//Gevonden string moet worden aangepast. Dus de oorspronkelijke matcher is alleen een kapstok
		while (m.find()){
			Matcher rm = p.matcher(replacedXml);
			rm.find(j);
			logger.debug("escape:match found "+replacedXml.substring(rm.start(), rm.end()));
			//base64= replacedXml.substring(rm.start(), rm.end());
			//base64 = base64.replaceAll(base64String, "").replaceAll("\"/>", ""); dit is de te vertalen string
			base64 = base64String+escape(t.translate(replacedXml.substring(rm.start(), rm.end()).replaceAll(base64String, "").replaceAll("\"/>", "")))+"\"/>"; 
			replacedXml = replacedXml.substring(0,rm.start())+base64+replacedXml.substring(rm.end());
			j = rm.start()+base64.length();
			logger.debug("escape:string altered "+base64);
		}
		logger.info("escape:end");
		return replacedXml;
	}
	
	/*Read old policy file and write a new one where base64 values are replaced*/
	public void editXMLFile (String oldfile, String newfile) throws UnsupportedEncodingException{
		logger.info("editXMLFile:start");
		String xml = "";
		Charset charset = Charset.forName("UTF-8");
		logger.debug("editXml: read file "+oldfile);
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(oldfile), charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        xml +=line+System.lineSeparator();
		    }
		} catch (IOException e) {
			logger.fatal("editXml: read file "+e.getMessage());
		}
		
		String replacedXml = replace(xml);
		logger.debug("editXml: read file "+newfile);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newfile), charset)) {
		    writer.write(replacedXml, 0, replacedXml.length());
		} catch (IOException e) {
			logger.fatal("editXMLFilel: write to file "+e.getMessage());
		}
		logger.info("editXml:end");
	}
	
	
	public static void main(String args[]) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		String y = "<L7p:Base64Expression stringValue=\"<st:administratie xmlns:st=\"http://www.egem.nl/StUF/StUF0301\">${administrationid.result}</st:administratie>\"/>";
		XmlString xmlString = new XmlString();
		
		System.err.println(xmlString.escape(y));

	}
	
}