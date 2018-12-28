package nl.denhaag.rest.monitor.editor;

//import java.io.IOException;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditXml {
	//Edit the xml's and re move some stuff
	private static final Logger logger = LogManager.getLogger();
	
	public String edit (String xml){
		logger.info("EditXml.edit: start");
		String replaced = xml.replaceAll("\r", "").replaceAll("\n", "");
		String cutter = "schemaLocation=\".+?\"";
//		String cutter = "(schemaLocation=\".+?\")|(location=\".+?\")";
		Pattern p = Pattern.compile(cutter);
		Matcher m = p.matcher(replaced);

		String editedStr = replaced;
		int count = 0;
		int diff = 0;
		while (m.find()){
			int xx = editedStr.length();
			String newstring = "";
			if (replaced.substring(m.start(), m.end()).contains("/")){
				newstring = "schemaLocation=\""+replaced.substring(m.start(), m.end()).substring(replaced.substring(m.start(), m.end()).lastIndexOf("/")+1).replace("?", ".");
			} else {
				newstring= replaced.substring(m.start(), m.end()).replace("?", ".");
			}
			if (count==0){
				editedStr = editedStr.substring(0,m.start()-diff)+newstring+replaced.substring(m.end()).replace("?", ".");
			}
			diff = diff + xx - editedStr.length();
		}
		logger.debug("EditXml.edit: end");
		return editedStr;
	}
	
	public String editLocation (String xml){
		logger.debug("EditXml.editLocation: start");
		String replaced = xml.replaceAll("\r", "").replaceAll("\n", "");
		String cutter = "location=\".+?\"";
//		String cutter = "(schemaLocation=\".+?\")|(location=\".+?\")";
		Pattern p = Pattern.compile(cutter);
		Matcher m = p.matcher(replaced);

		String editedStr = replaced;
		int count = 0;
		int diff = 0;
		while (m.find()){
			int xx = editedStr.length();
			String newstring = "";
			if (replaced.substring(m.start(), m.end()).contains("/")){
				newstring = "location=\""+replaced.substring(m.start(), m.end()).substring(replaced.substring(m.start(), m.end()).lastIndexOf("/")+1).replace("?", ".");
			} else {
				newstring= replaced.substring(m.start(), m.end()).replace("?", ".");
			}
			if (count==0){
				editedStr = editedStr.substring(0,m.start()-diff)+newstring+replaced.substring(m.end()).replace("?", ".");
			}
			diff = diff + xx - editedStr.length();
		}
		logger.debug("EditXml.editLocation: end");
		return editedStr;
	}
	
//	public static void main(String[] args) {
//		
//		
//		String xml = "<wsdl:definitions name=\"ReferentieService\" targetNamespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:tns=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+
//		"<wsdl:documentation>Contract version 1.1</wsdl:documentation>"+
//		"<wsdl:types>"+
//			"<xsd:schema>"+
//			"<xsd:import namespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" location=\"referentie-messages-v1.2.xsd\"/>"+
//				"<xsd:import namespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" schemaLocation=\"file://ccc/referentie-messages-v1.1?xsd\"/>"+
//				"<xsd:import namespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" schemaLocation=\"referentie-messages-v1.2.xsd\"/>"+
//				"<xsd:import namespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" schemaLocation=\"file://ccc/referentie-messages-v1.3?xsd\"/>"+
//				"<xsd:import namespace=\"http://contracts.denhaag.nl/referentie/javaservice/v1\" location=\"file://ccc/referentie-messages-v1.3?xsd\"/>"+
//			"</xsd:schema>"+
//	    "</wsdl:types>"+
//		"<wsdl:message name=\"geefAlleDocumentenRequest\">"+
//			"<wsdl:part element=\"tns:geefAlleDocumenten\" name=\"parameters\"/>"+
//		"</wsdl:message>"+
//		"<wsdl:message name=\"geefAlleDocumentenResponse\">"+
//			"<wsdl:part element=\"tns:geefAlleDocumentenResponse\" name=\"parameters\"/>"+
//		"</wsdl:message>";
//
//		
//		
//		EditXml	e = new EditXml();
////		e.edit(xml);
//		System.err.println(e.editLocation(e.edit(xml)));
//		
//		
//	}
}
