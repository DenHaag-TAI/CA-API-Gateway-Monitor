package nl.denhaag.rest.transformations;

import java.io.File;
//import java.io.FileInputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//import net.sf.saxon.s9api.Processor;
//import net.sf.saxon.s9api.Serializer;
//import net.sf.saxon.s9api.XsltCompiler;
//import net.sf.saxon.s9api.XsltExecutable;
//import net.sf.saxon.s9api.XsltTransformer;
//import nl.denhaag.rest.monitor.Base64Extension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Transform {
	
	private static final Logger logger = LogManager.getLogger();
	
	/* Tranformeren van xml bestanden via xslt */
	public static void htmlTransform (String source, String xsl, String output) throws TransformerException {
		logger.info("Transform.htmlTransform: start");
		TransformerFactory factory = TransformerFactory.newInstance();
//        Source xslt = new StreamSource(new File(xsl));
	    ClassLoader classLoader = (ClassLoader) Thread.currentThread().getContextClassLoader();
        Source xslt = new StreamSource(classLoader.getResourceAsStream(xsl));
        Transformer transformer = factory.newTransformer(xslt);
        Source text = new StreamSource(new File(source));
        transformer.transform(text, new StreamResult(new File(output)));
        logger.info("Transform.htmlTransform: end");
	}
}
