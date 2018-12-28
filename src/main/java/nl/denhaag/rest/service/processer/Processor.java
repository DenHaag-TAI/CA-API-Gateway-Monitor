package nl.denhaag.rest.service.processer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import nl.denhaag.rest.monitor.Base64Extension;
import nl.denhaag.rest.monitor.editor.EditXml;
import nl.denhaag.rest.monitor.editor.FindSplitter;
import nl.denhaag.rest.monitor.folder.ShowDeps;
import nl.denhaag.rest.service.HttpMapping;
import nl.denhaag.rest.service.Item;
import nl.denhaag.rest.service.Properties;
import nl.denhaag.rest.service.Property;
import nl.denhaag.rest.service.Resource;
import nl.denhaag.rest.service.ResourceSet;
import nl.denhaag.rest.service.Resources;
import nl.denhaag.rest.service.ResourcesResource;
import nl.denhaag.rest.service.Service;
import nl.denhaag.rest.service.ServiceDetail;
import nl.denhaag.rest.service.ServiceMappings;
import nl.denhaag.rest.service.SoapMapping;
import nl.denhaag.rest.service.Verbs;
import nl.denhaag.rest.transformations.Transform;
import nl.denhaag.rest.transformations.XmlString;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Processor {
	
	private static final Logger logger = LogManager.getLogger();
	private String baseUrl;
	private String configUrl;
	private String multipleendpointpath= "policyMultipleProtectedEndpoints.xsl";
	private String endpointxml = "endpoints.xml";
	private static final String policyxml = "policy.xml";
	private static String endpointpath = "/endpoints/endpoint";
	private String policy64xml = "policybase64.xml";
	private String policyhtml = "policy.html";
	private String splitter = "";
	private boolean startedWithFindSplitter = false;


	private Indexer indexer; //Hier in een later stadium een indexerService van maken
//	private Bestanden bs = new Bestanden(); //Dit moet eigenlijk anders
	ArrayList<Bestand> bs = new ArrayList<Bestand>();
	String s = System.getProperty("file.separator");
	
	private final static XPath XPATH = XPathFactory.newInstance().newXPath();
	private static XPathExpression PROTECTED_SERVICE_URL_EXPRESSION;
	static {
		try {
			XPATH.setNamespaceContext(new PolicyNamespaceContext());
			PROTECTED_SERVICE_URL_EXPRESSION = XPATH.compile(endpointpath);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the configUrl
	 */
	public String getConfigUrl() {
		return configUrl;
	}


	/**
	 * @param configUrl the configUrl to set
	 */
	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}


	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	
	/**
	 * @return the indexer
	 */
	public Indexer getIndexer() {
		return indexer;
	}


	/**
	 * @param indexer the indexer to set
	 */
	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}


	public void itemProcessor (Item i) throws SAXException, IOException , ParserConfigurationException {
		logger.info("Processor.itemProcessor start "+i.getId());
		indexer.setId(i.getId());
		indexer.setGenerationDate(i.getTimestamp());
		indexer.setName(i.getName().replace("/$", ""));
		resourceProcessor (i.getResource());
		Bestanden bbs = new Bestanden();
		bbs.setFile(bs);
		indexer.setBestanden(bbs);
		logger.info("Processor.itemProcessor end "+i.getId());
	}
	
	
	private void resourceProcessor (Resource r) throws SAXException, IOException , ParserConfigurationException{
		logger.debug("Processor.resourceProcessor start");
		if (r != null) {
			if (r.getService() != null){ 
				serviceProcessor(r.getService());
			}
		} 
		logger.debug("Processor.resourceProcessor end");
	}
	
	private void serviceProcessor (Service s) throws SAXException, IOException, ParserConfigurationException{
//		s.getId();
//		s.getVersion();
		logger.debug("Processor.serviceProcessor start "+s.getId());
		serviceDetailProcessor(s.getServiceDetail());
		resourcesProcessor (s.getResources());
		logger.debug("Processor.serviceProcessor end "+s.getId());
	}
	
	private void serviceDetailProcessor(ServiceDetail sd ){
		logger.debug("Processor.serviceDetailProcessor start");
		indexer.setEnabled(sd.getEnabled());
		try {
			ShowDeps.search(sd.getFolderId(), "");
		} catch (RuntimeException e){
			//expected
		}
		indexer.setPolicyManagerPath(ShowDeps.res);
		
//		sd.getId();
//		sd.getName();
		indexer.setVersion(sd.getVersion());
		propertiesProcessor (sd.getProperties());
		serviceMappingsProcessor (sd.getServiceMappings());
		logger.debug("Processor.serviceDetailProcessor end");
	}
	
	private void retrieveSourceUrl (ResourcesResource rr) throws SAXException, IOException, ParserConfigurationException {
		logger.debug("Processor.retrieveSourceUrl start");
		if (rr.getType().equalsIgnoreCase("wsdl") || rr.getType().equalsIgnoreCase("xmlschema")){
			logger.trace("Processor.retrieveSourceUrl if wsdl or xmlschema");
			if (!startedWithFindSplitter){
				logger.trace("Processor.retrieveSourceUrl start if !startedWithFindSplitter");
				startedWithFindSplitter = true;
				splitter = rr.getSourceUrl().substring(0,rr.getSourceUrl().lastIndexOf("/"));
				logger.trace("Processor.retrieveSourceUrl end if !startedWithFindSplitter");
			} else {
				logger.trace("Processor.retrieveSourceUrl start if startedWithFindSplitter");
				if (splitter != null && !splitter.equalsIgnoreCase("")){
					logger.trace("Processor.retrieveSourceUrl start if splitter not null");
					FindSplitter f = new FindSplitter();
					splitter = f.check(rr.getSourceUrl().substring(0,rr.getSourceUrl().lastIndexOf("/")), splitter);
					logger.trace("Processor.retrieveSourceUrl end if splitter not null");
				}	
				logger.trace("Processor.retrieveSourceUrl end if startedWithFindSplitter");
			}
			logger.trace("Processor.retrieveSourceUrl end wsdl or xmlschema");
		}	
		logger.debug("Processor.retrieveSourceUrl end");
	}
	
	private void processResourcesResource (ResourcesResource rr, String rootUrl) throws SAXException, IOException, ParserConfigurationException {
		logger.debug("Processor.processResourcesResource start");
		EditXml ex = new EditXml();
		String resourceBaseDirectory = baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion();
		if (rr.getType().equalsIgnoreCase("wsdl")) {
			logger.trace("Processor.processResourcesResource if wsdl");
			boolean isSplitter = false;
			boolean containsparenthesis = false;
			String [] split = null;
			String finalPart = null;
			if (splitter.contains("(")||splitter.contains("(")){
				splitter = splitter.replace("(","\32").replace(")", "\33");
				containsparenthesis = true;
			}
				
			if (containsparenthesis) {
				if (rr.getSourceUrl().replace("(","\32").replace(")", "\33").contains(splitter)){
					isSplitter = true;
					split =  rr.getSourceUrl().replace("(","\32").replace(")", "\33").split(splitter);
					finalPart = split[1].replace ("\32","(").replace("\33",")" );
				}
			} else {
				if (rr.getSourceUrl().contains(splitter)){
					isSplitter = true;
					split = rr.getSourceUrl().split(splitter);
					finalPart = split[1];
				}
					
			}
			Bestand b = new Bestand ();
			if (rootUrl != null){
				if (rr.getSourceUrl().equalsIgnoreCase(rootUrl)){
					b.setRoot("true");
				}
			}
//				b.setBestandsnaam("resources"+s+removePath(rr.getSourceUrl().replace("?", ".")));
			if (isSplitter){
				Files.createDirectories(Paths.get(resourceBaseDirectory+s+"resources"+split[1].substring(0,split[1].lastIndexOf("/")).replace("/", "\\")));
				b.setBestandsnaam("resources"+s+finalPart.substring(1).replace("?", ".").replace("/", s));
			} else {
				b.setBestandsnaam("resources"+s+removePath(rr.getSourceUrl().replace("?", ".")));
			}
			b.setType(rr.getType().toUpperCase());
			bs.add(b);
			try{
				createDirectory (resourceBaseDirectory+s+"resources");
//					PrintWriter out = new PrintWriter( baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
//					PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
//					Files.createDirectories(Paths.get(resourceBaseDirectory+s+"resources"+split[1].substring(0,split[1].lastIndexOf("/")).replace("/", "\\")));
					
				if (isSplitter){
					PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+finalPart.replace("?", ".").replace("/", "\\"));
//				    out.println(ex.editLocation(ex.edit(rr.getDescription())));
					out.println( (rr.getDescription()) );
					out.close();
				} else {
					PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
					out.println(ex.editLocation(ex.edit(rr.getDescription())));
					out.close();
				}
				    
			} catch (FileNotFoundException e){
				logger.error("Processor:processResourcesResource:wsdl "+e.getMessage());
			}
			logger.trace("Processor.processResourcesResource end if wsdl");	
		} else if (rr.getType().equalsIgnoreCase("xmlschema")) {
			logger.trace("Processor.processResourcesResource start if xmlschema");
			boolean isSplitter = false;
			String [] split =  null; //rr.getSourceUrl().split(splitter);
			String finalPart = null; //split[1];
				
			if (rr.getSourceUrl().contains(splitter)){
				isSplitter = true;
				split =  rr.getSourceUrl().split(splitter);
				finalPart = split[1];
			}	
			if (isSplitter){
				Files.createDirectories(Paths.get(resourceBaseDirectory+s+"resources"+split[1].substring(0,split[1].lastIndexOf("/")).replace("/", "\\")));
			}	
			Bestand b = new Bestand ();
			if (isSplitter) {
				b.setBestandsnaam("resources"+s+finalPart.substring(1).replace("?", ".").replace("/", s));
			} else {
				b.setBestandsnaam("resources"+s+removePath(rr.getSourceUrl().replace("?", ".")));
			}
//				
				
			b.setType("XSD");
			bs.add(b);
			try{
				createDirectory (resourceBaseDirectory+s+"resources");
//				PrintWriter out = new PrintWriter( baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
//				PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
				if (isSplitter){
					PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+s+finalPart.replace("?", "."));
				    out.println( (rr.getDescription()) );
				    out.close();
				} else {
					PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"resources"+s+rr.getSourceUrl().replace("?", ".").substring(rr.getSourceUrl().lastIndexOf("/")+1));
					out.println(ex.editLocation(ex.edit(rr.getDescription())));
					out.close();
				}
//				    out.println( (new EditXml()).edit(rr.getDescription()) );
//				    out.println(ex.editLocation(ex.edit(rr.getDescription())));
			} catch (FileNotFoundException e){
				logger.error("Processor:processResourcesResource:xmlschema "+e.getMessage());
			}
			logger.trace("Processor.processResourcesResource end if xmlschema");	
		} else if (rr.getType().equalsIgnoreCase("policy")) {
			logger.trace("Processor.processResourcesResource start if policy");
			try{
					//Wegschrijven policy
//					PrintWriter out = new PrintWriter( baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+"policy.xml");
				PrintWriter out = new PrintWriter( resourceBaseDirectory+s+"policy.xml");
			    out.println( rr.getDescription() );
			    out.close();
			    
//				    PrintWriter ep = new PrintWriter( baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+endpointxml);
			    PrintWriter ep = new PrintWriter( resourceBaseDirectory+s+endpointxml);
//					generateSecuredEndpointsReport(new PrintWriter( baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+endpointxml), new File(baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+policyxml));
				generateSecuredEndpointsReport(new PrintWriter( resourceBaseDirectory+s+endpointxml), new File(resourceBaseDirectory+s+policyxml));
				//Use those endpoints for other stuff
				ep.flush();
				ep.close();
				
				String protectedServiceUrl = ""; 
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
				domFactory.setNamespaceAware(false);
				DocumentBuilder builder = domFactory.newDocumentBuilder();
//					Document policyDocument = builder.parse(baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+endpointxml);
				Document policyDocument = builder.parse(resourceBaseDirectory+s+endpointxml);
				NodeList nl = null;
				try {
					nl  = (NodeList) PROTECTED_SERVICE_URL_EXPRESSION.evaluate(policyDocument, XPathConstants.NODESET);
				} catch (XPathExpressionException e) {
					logger.error("monitor:NodeList "+e.getMessage());
				}
				if (nl.getLength()>0){
					protectedServiceUrl = nl.item(0).getTextContent();
				}
				if (!protectedServiceUrl.equalsIgnoreCase("") || protectedServiceUrl!=null){
					indexer.setProtectedEndpoint(protectedServiceUrl);
				}
				XmlString x = new XmlString();
//					x.editXMLFile(baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+policyxml , baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+policy64xml );
				x.editXMLFile(resourceBaseDirectory+s+policyxml , resourceBaseDirectory+s+policy64xml );

				try {
//						Transform.htmlTransform(baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+policy64xml, configUrl+s+"xsl"+s+"policy.xsl", baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion()+s+policyhtml);
					Transform.htmlTransform(resourceBaseDirectory+s+policy64xml, configUrl+s+"xsl"+s+"policy.xsl", resourceBaseDirectory+s+policyhtml);
				} catch (TransformerException e) {
					logger.error("monitor:Transform "+e.getMessage());
				}
			} catch (FileNotFoundException e){
				logger.error("Processor:processResourcesResource:policy "+e.getMessage());
			}
			logger.trace("Processor.processResourcesResource end if policy");
		} 
		logger.debug("Processor.processResourcesResource end");
	}
	
	private void resourcesProcessor (Resources rs) throws SAXException, IOException, ParserConfigurationException {
		logger.debug("Processor.resourcesProcessor start");
		if (createDirectory (baseUrl+s+"web"+s + indexer.getId() +"-" +indexer.getName().replaceAll("/", " ").trim() +"-v" + indexer.getVersion() +"-pv" +indexer.getPolicyVersion())){
			logger.trace("Processor.resourcesProcessor createDirectory");
			for (ResourceSet r: rs.getResourceset()){
				logger.trace("Processor.resourcesProcessor start loop ResourceSet");
				for (ResourcesResource rr :r.getRrs()){
					retrieveSourceUrl (rr);
				}
			
				for (ResourcesResource rr :r.getRrs()){
					processResourcesResource (rr, r.getRootUrl());
				}
				logger.trace("Processor.resourcesProcessor start loop ResourceSet");
			}
			logger.trace("Processor.resourcesProcessor createDirectory");
		}
		logger.debug("Processor.resourcesProcessor end");
	}
	
	
	private boolean createDirectory (String directory) {
		logger.debug("Processor.createDirectory start");
		if (!Files.isDirectory(Paths.get(directory))){
			try {
				Files.createDirectories(Paths.get(directory));
			} catch (IOException e) {
				logger.error("Processor:createDirectory "+e.getMessage());
			}
			logger.debug("Processor.createDirectory end return true");
			return true;
		}
		logger.debug("Processor.createDirectory end return false");
		return false;
	}
	
	private void propertiesProcessor (Properties ps){
		logger.debug("Processor.propertiesProcessor start");
		for (Property p : ps.getProperty()){
			logger.trace("Processor.propertiesProcessor: start loop Properties "+p.getKey());
			if (p.getKey().equalsIgnoreCase("internal")){
				indexer.setInternal(p.getBooleanValue());
				logger.trace("Processor.propertiesProcessor: if value internal {}", p.getBooleanValue());
			}
			if (p.getKey().equalsIgnoreCase("policyRevision")){
				indexer.setPolicyVersion(p.getLongValue());
				//policyVersion = Integer.parseInt(p.getLongValue());
				logger.trace("Processor.propertiesProcessor: if value policyRevision ()", p.getLongValue());
			}
			if (p.getKey().equalsIgnoreCase("soap")){
				indexer.setSoap(p.getBooleanValue());
				logger.trace("Processor.propertiesProcessor: if soap {}",p.getBooleanValue());
			}	
			if (p.getKey().equalsIgnoreCase("soapVersion")){
				indexer.setSoapVersion(p.getLongValue());
				logger.trace("Processor.propertiesProcessor: if soapVersion {}",p.getLongValue());
			}
			if (p.getKey().equalsIgnoreCase("wssProcessingEnabled")){
				indexer.setWsSecurity(p.getBooleanValue());
				logger.trace("Processor.propertiesProcessor: if wssProcessingEnabled {}",p.getBooleanValue());
			}
			logger.trace("Processor.propertiesProcessor: end loop Properties "+p.getKey());
		}
		logger.debug("Processor.propertiesProcessor start");
	}
	
	private void serviceMappingsProcessor (ServiceMappings sm) {
		logger.debug("Processor.serviceMappingsProcessor start");
		httpMappingProcessor (sm.getHttpMapping());
		soapMappingProcessor (sm.getSoapMapping());
		logger.debug("Processor.serviceMappingsProcessor end");
	}
	
	private void httpMappingProcessor (HttpMapping hm){
		logger.debug("Processor.httpMappingProcessor start");
		indexer.setResolutionPath(hm.getUrlPattern());
		verbsProcessor (hm.getVerbs());
		logger.debug("Processor.httpMappingProcessor end");
	}
	
	private void verbsProcessor (Verbs v) {
		logger.debug("Processor.verbsProcessor start");
		HttpMethod hm = new HttpMethod();
		ArrayList<String> e = new ArrayList<String>();
		for (String vs :v.getVerb()){
			e.add(vs);
		}
		hm.setHttpMethod(e);
		indexer.setHttpMethods(hm);
		logger.debug("Processor.verbsProcessor end");
	}
	
	private void soapMappingProcessor (SoapMapping sm){
		//null indien SOAP service
		if (sm != null){
			sm.getLax();
		}	
	}
	
	private String removePath (String fileLocation) {
		logger.debug("Processor.removePath return");
		return fileLocation.substring(fileLocation.lastIndexOf("/")+1);
	}
	
	private void generateSecuredEndpointsReport(Writer writer, File xmlFile) {
    	logger.debug("Processor.generateSecuredEndpointsReport: start");
    	try {
	    	FileInputStream fileInputStream = new FileInputStream(xmlFile);
			Source xmlSource = new StreamSource(fileInputStream);
//			logger.error(configurl+s+"xsl"+s+multipleendpointpath);
	    	XsltExecutable executable = getXsltExecutable(configUrl+s+"xsl"+s+multipleendpointpath);
	        XsltTransformer transformer = executable.load();
	        transformer.setSource(xmlSource);
	        Serializer serializer = new Serializer();
	        serializer.setOutputWriter(writer);
	        transformer.setDestination(serializer);
	        try {
	        	transformer.transform();
	        } catch (NullPointerException e) {
	        	logger.error("monitor:Transform "+e.getMessage());
	        }	
	        fileInputStream.close();
    	}catch (Exception e){
    		logger.fatal("Processor.generateSecuredEndpointsReport:Transform -"+e.getMessage());
    		System.exit(-1);
    	}
    	logger.debug("Processor.generateSecuredEndpointsReport: end");
    }
    
	private static XsltExecutable getXsltExecutable(String xslUrl) throws SaxonApiException{
		logger.info("Processor.getXsltExecutable: start");
        ClassLoader classLoader = (ClassLoader) Thread.currentThread().getContextClassLoader();
        Source xsltSource = new StreamSource(classLoader.getResourceAsStream(xslUrl));
        net.sf.saxon.s9api.Processor processor = new net.sf.saxon.s9api.Processor(false);
        Base64Extension resourcebundleExtension = new Base64Extension();
        processor.registerExtensionFunction(resourcebundleExtension);
        XsltCompiler compiler = processor.newXsltCompiler();
        logger.info("Processor.getXsltExecutable: end");
        return compiler.compile(xsltSource);
	}
	
    private static class PolicyNamespaceContext implements NamespaceContext {
    	
        public String getNamespaceURI(String prefix) {
            if("wsp".equals(prefix)) {
                return "http://schemas.xmlsoap.org/ws/2002/12/policy";
            }if("L7p".equals(prefix)) {
                return "http://www.layer7tech.com/ws/policy";
            }
            return null;
        }

        public String getPrefix(String namespaceURI) {
            return null;
        }

        @SuppressWarnings("rawtypes")
		public Iterator getPrefixes(String namespaceURI) {
            return null;
        }
    }
}
