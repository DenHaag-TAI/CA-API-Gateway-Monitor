package nl.denhaag.rest.monitor;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
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

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import nl.denhaag.rest.monitor.directory.CheckIfDirectoryExists;
import nl.denhaag.rest.monitor.editor.EditXml;
import nl.denhaag.rest.monitor.enabled.ServiceEnabledChecker;
import nl.denhaag.rest.monitor.folder.ShowDeps;
import nl.denhaag.rest.monitor.index.Bestand;
import nl.denhaag.rest.monitor.index.FinalArray;
import nl.denhaag.rest.monitor.index.HttpMethod;
import nl.denhaag.rest.monitor.index.IndexHtml;
import nl.denhaag.rest.monitor.index.Indexer;
import nl.denhaag.rest.monitor.properties.CaProperties;
import nl.denhaag.rest.monitor.remove.CurrentServices;
import nl.denhaag.rest.monitor.zip.Zipper;
import nl.denhaag.rest.transformations.Transform;
import nl.denhaag.rest.transformations.XmlString;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RestMonitor {
	//Hieronder staat wat nog ontbreekt in de index.xml file
	//Missing <wsSecurity> tag en ook waarde in REST service => niet beschikbaar
	//Missing <policyManagerPath>
//	Hoe zien we verwijderde services ? 
//	Eerst een lijst maken van alle services die er moeten zijn op basis van de directory structuur
//  Tijdens het aflopen van de daadwerkelijke services de gevonden services uit de lijst van allen verwijderen
//  Totdat we de services over hebben die zijn verwijderd	
	private static final Logger logger = LogManager.getLogger();
	public static final String CONTENT_TYPE         = "Content-Type";
	public static final String ACCEPT_ENCODING      = "Accept-Encoding";
	public static final String CONTENT_ENCODING     = "Content-Encoding";
	public static final String ENCODING_GZIP        = "gzip";
	public static final String MIME_FORM_ENCODED    = "application/x-www-form-urlencoded";
	public static final String MIME_TEXT_PLAIN      = "text/plain";
	private final String USER_AGENT = "Mozilla/5.0";
	private static String baseUrl = "";
	private static String webUrl = "";
	private static String zipsUrl = "";
	private static String configurl = "";
	private static final String policyxml = "policy.xml";
	private static final String policyhtml = "policy.html";
	private static final String indexxml = "index.xml";
	private static final String indexhtml = "index.html";
	private static final String endpointxml = "endpoints.xml";
	private static final String policy64xml = "policybase64.xml";
	private static final String endpointpath = "/endpoints/endpoint";
	private static final String multipleendpointpath= "policyMultipleProtectedEndpoints.xsl";
	private static final String s = System.getProperty("file.separator");
	
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

	
	public void monitor () throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, JAXBException, SaxonApiException, ParserConfigurationException, SAXException {
		
		boolean continu = true;
		SSLContextBuilder builderssl = new SSLContextBuilder( );
		builderssl.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builderssl.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    CloseableHttpClient httpClientxx = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		
		
//		CloseableHttpClient httpClientxx = HttpClientBuilder.create().build();
		CaProperties ca = new CaProperties();
		java.util.Properties cap = ca.getPropertyValue();
		configurl = cap.getProperty("configurl");
		baseUrl = cap.getProperty("baseurl"); //Dir 
		String caUrl = cap.getProperty("caurl");
		String folderurl = cap.getProperty("folderurl");
		
		logger.debug("monitor:baseUrl {}", baseUrl);
		logger.debug("monitor:baseUrl {}", caUrl);
		Files.createDirectories(Paths.get(baseUrl+s+"web"));
		Files.createDirectories(Paths.get(baseUrl+s+"zips"));
		
		//Rommelig maar nu gebruik ik baseurl op een andere wijze
//		baseUrl = cap.getProperty("baseurl")+s+"web";
		webUrl = baseUrl + s + "web";
		zipsUrl = baseUrl + s + "zips";
		
//		Vullen van alle services op basis van webUrl
		CurrentServices c = new CurrentServices();
		c.setAll(webUrl);
		
		String authString = cap.getProperty("username") + ":" + cap.getProperty("password");
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);

		HttpGet requestF = new HttpGet(folderurl);
		requestF.addHeader("User-Agent", "Mozilla/5.0");
		requestF.addHeader("Authorization", "Basic " + authStringEnc);
		nl.denhaag.rest.monitor.folder.Folders fl = null;
		HttpResponse responseF = null;
		try {
//			responseF.
			responseF = httpClientxx.execute(requestF);
		} catch (Exception e){
			//Wel loggen maar de maar de service niet laten knallen. Service kan tijdelijk onbereikbaar zijn
			System.err.println ("Exception "+e.getMessage());
			continu = false;
		}	
		
		fl = JAXB.unmarshal(new InputStreamReader(responseF.getEntity().getContent()), nl.denhaag.rest.monitor.folder.Folders.class);
		if (continu){
			ShowDeps.noshow(fl.getResource().getDependencyList().getDependencies(), Optional.<String>empty(), 0);
			//Alle info over folders verzameld
			
			logger.info("monitor:Starting monitor run");
			
			
			//Als de dirs niet bestaan dan maken
	
//			CloseableHttpClient httpClientx = HttpClientBuilder.create().build();
			
			HttpGet request = new HttpGet(caUrl);
			request.addHeader("User-Agent", USER_AGENT);
			request.addHeader("Authorization", "Basic " + authStringEnc);
			
			HttpResponse response = httpClientxx.execute(request);
			
			List l = JAXB.unmarshal(new InputStreamReader(response.getEntity().getContent()), List.class);
			
			String currentDir = "";
			String zipDir = "";
			boolean skip = false; 
			for (Item i : l.getItems()){
				logger.debug("monitor:loop Items");
				//Voor iedere service een nieuwe Indexer nodig met daarin de juiste waarden
				//WS-Security informatie niet beschikbaar
				Indexer indexer = new Indexer();
				indexer.setWsSecurity("false");
				indexer.setGeneratorVersion(cap.getProperty("generatorversion"));
				int policyVersion = 0;
				
				indexer.setGenerationDate(i.getTimestamp());
				indexer.setId(i.getId());
				indexer.setName(i.getName());

				
				//Dit moet natuurlijk niet via getResources.get(0), maar dat is van latere zorg
				for (Property p : i.getResources().get(0).getService().getServiceDetail().getProperties().getProperty()){
					logger.debug("monitor:loop Properties");
					if (p.getKey().equalsIgnoreCase("internal")){
						indexer.setInternal(p.getBooleanValue());
						logger.debug("monitor:value internal {}", p.getBooleanValue());
					}
					if (p.getKey().equalsIgnoreCase("policyRevision")){
						indexer.setPolicyVersion(p.getLongValue());
						policyVersion = Integer.parseInt(p.getLongValue());
						logger.debug("monitor:value policyRevision ()", p.getLongValue());
					}
					if (p.getKey().equalsIgnoreCase("soap")){
						indexer.setSoap(p.getBooleanValue());
						logger.debug("monitor:loop soap {}",p.getBooleanValue());
					}	
					if (p.getKey().equalsIgnoreCase("soapVersion")){
						indexer.setSoapVersion(p.getLongValue());
						logger.debug("monitor:loop soapVersion {}",p.getLongValue());
					}
					logger.debug("monitor:end loop Properties");
				}
				if (i.getResources().get(0).getService().getServiceDetail().getServiceMappings() != null){
					if (i.getResources().get(0).getService().getServiceDetail().getServiceMappings().getHttpMapping() != null){
						if (i.getResources().get(0).getService().getServiceDetail().getServiceMappings().getHttpMapping().getVerbs() != null){
							HttpMethod hm = new HttpMethod();
							ArrayList<String> e = new ArrayList<String>();
							for (String v: i.getResources().get(0).getService().getServiceDetail().getServiceMappings().getHttpMapping().getVerbs().getVerb()){
								logger.debug("monitor:loop Verb");
								e.add(v);
								logger.debug("monitor:end loop Verb");
							}
							hm.setHttpMethod(e);
							indexer.setHttpMethods(hm);
						}
						if (i.getResources().get(0).getService().getServiceDetail().getServiceMappings().getHttpMapping().getUrlPattern() != null){
							indexer.setResolutionPath(i.getResources().get(0).getService().getServiceDetail().getServiceMappings().getHttpMapping().getUrlPattern());
						}
					}
				}
				indexer.setEnabled(i.getResources().get(0).getService().getServiceDetail().getEnabled());
				
				try {
					ShowDeps.search(i.getResources().get(0).getService().getServiceDetail().getFolderId(), "");
				} catch (RuntimeException e){
					//expected
				}
				indexer.setPolicyManagerPath(ShowDeps.res);
				indexer.setVersion(i.getResources().get(0).getService().getServiceDetail().getVersion());
				
				currentDir = webUrl+s+i.getId()+"-"+i.getName().replaceAll("/", " ").trim()+"-v"+i.getResources().get(0).getService().getServiceDetail().getVersion()+"-pv"+policyVersion;
				c.remove(i.getId()+"-"+i.getName().replaceAll("/", " ").trim()+"-v"+i.getResources().get(0).getService().getServiceDetail().getVersion()+"-pv"+policyVersion);
				String ffile = i.getId()+"-"+i.getName().replaceAll("/", " ").trim()+"-v"+i.getResources().get(0).getService().getServiceDetail().getVersion()+"-pv"+policyVersion; 
				zipDir = zipsUrl+s+i.getId()+"-"+i.getName().replaceAll("/", " ").trim()+"-v"+i.getResources().get(0).getService().getServiceDetail().getVersion()+"-pv"+policyVersion+".zip";
				skip = false;
				if (Files.exists(Paths.get(currentDir))){
					//Overslaan van deze service
					logger.debug("monitor:Service already exists");
					skip =true;
				}
				//In service/enabled kijken of de service van enabled naar disabled of omgekeerd is gegaan
				//Bestandje heet altijd index.html, dus dat is gemakkelijk
			    //Even een side-projectje starten en deze dan later invoegen in mijn code
				
				if (skip){
					boolean enabled = ServiceEnabledChecker.check(currentDir);
					
					boolean wasEnabled = false;
					if (indexer.getEnabled().equalsIgnoreCase("true")){
						wasEnabled = true;
					} 
					
					if (enabled != wasEnabled && skip){
						skip = false;
					}
				}

				
				Files.createDirectories(Paths.get(currentDir));
				Files.list(Paths.get(webUrl)).forEach(path -> CheckIfDirectoryExists.delete(Paths.get(webUrl), path, i.getId(),ffile));
				
				FinalArray ffs = new FinalArray();
				ArrayList<Bestand> b = new ArrayList<Bestand>();
				//Maken van de bestandjes voor Resources
				
				
				
				
				if (!skip){
					logger.debug("monitor:new service");
					for (Resource r: i.getResources()){
						for (ResourceSet rs:r.getService().getResources().getResourceset()){
							for (RResource rrs:rs.getRrs()){
								if (rrs.getType().equalsIgnoreCase("wsdl")){
									Bestand bs1 = new Bestand ();
									bs1.setBestandsnaam(removePath(rrs.getSourceUrl().replace("?", ".")));
									bs1.setType(rrs.getType().toUpperCase());
									if (rs.getRootUrl().equalsIgnoreCase(rrs.getSourceUrl())){
										bs1.setRoot("true");
									}
									b.add(bs1);
									try(  PrintWriter out = new PrintWriter( currentDir+s+rrs.getSourceUrl().replace("?", ".").substring(rrs.getSourceUrl().lastIndexOf("/")+1))){
									    out.println( (new EditXml()).edit(rrs.getDescription()) );
									}
								} else if (rrs.getType().equalsIgnoreCase("xmlschema")){
									Bestand bs2 = new Bestand ();
									bs2.setBestandsnaam(removePath(rrs.getSourceUrl().replace("?", ".")));
									bs2.setType("XSD"); //Netter is een vertaler xmlschema->XSD
									b.add(bs2);
									
									try(  PrintWriter out = new PrintWriter( currentDir+s+rrs.getSourceUrl().replace("?", ".").substring(rrs.getSourceUrl().lastIndexOf("/")+1))){
									    out.println(  (new EditXml()).edit(rrs.getDescription()) );
									}
								} else if (rrs.getType().equalsIgnoreCase("policy")){
									//Hier mag er maar een van zijn natuurlijk!!!!!
									PrintWriter ep = new PrintWriter( currentDir+s+endpointxml);
									try(  PrintWriter out = new PrintWriter( currentDir+s+policyxml )){
									    out.println( rrs.getDescription() );
									}
									//Write endpoints to file
									generateSecuredEndpointsReport(new PrintWriter( currentDir+s+endpointxml), new File(currentDir+s+policyxml));
									//Use those endpoints for other stuff
									ep.flush();
									ep.close();
									
									String protectedServiceUrl = ""; 
									DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
									domFactory.setNamespaceAware(false);
									DocumentBuilder builder = domFactory.newDocumentBuilder();
									Document policyDocument = builder.parse(currentDir+s+endpointxml);
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
									x.editXMLFile(currentDir+s+policyxml , currentDir+s+policy64xml );
									try {
										Transform.htmlTransform(currentDir+s+policy64xml, configurl+s+"xsl"+s+"policy.xsl", currentDir+s+policyhtml);
									} catch (TransformerException e) {
										logger.error("monitor:Transform "+e.getMessage());
									}
								}
							}
						}
					}
					ffs.setFile(b);
					indexer.setBestanden(ffs);
					IndexHtml indexHtml  = new IndexHtml();
					indexHtml.doIt(currentDir, indexer);
					
					try {
						Transform.htmlTransform(currentDir+s+indexxml, configurl+s+"xsl"+s+"html.xsl", currentDir+s+indexhtml);
					} catch (TransformerException e) {
						logger.error("monitor:Transform "+e.getMessage());
					}
					Zipper.pack(currentDir, zipDir);
				}
			}	
		}
		for (String dir : c.getCurrentServices()){
			FileUtils.deleteDirectory(new File (webUrl+s+dir));
		}
		
		logger.info("monitor:End monitor run");
	}
	
	
	private void processResource (ArrayList<Resource> resources, boolean skip, String currentDir) throws SAXException, IOException, ParserConfigurationException {
	
//	Moet een object uitkomen met b en een string waarin het endpoint zit	
		ProcessedResource pr = new ProcessedResource();
		ArrayList<Bestand> b = new ArrayList<Bestand>();
		if (!skip){
			logger.debug("monitor:new service");
			for (Resource r: resources){
				for (ResourceSet rs:r.getService().getResources().getResourceset()){
					
					for (RResource rrs:rs.getRrs()){
						if (rrs.getType().equalsIgnoreCase("wsdl")){
							Bestand bs1 = new Bestand ();
							bs1.setBestandsnaam(removePath(rrs.getSourceUrl()));
							bs1.setType(rrs.getType().toUpperCase());
							if (rs.getRootUrl().equalsIgnoreCase(rrs.getSourceUrl())){
								bs1.setRoot("true");
							}
							b.add(bs1);
							try(  PrintWriter out = new PrintWriter( currentDir+s+rrs.getSourceUrl().substring(rrs.getSourceUrl().lastIndexOf("/")+1))){
							    out.println( (new EditXml()).edit(rrs.getDescription()) );
							}
						} else if (rrs.getType().equalsIgnoreCase("xmlschema")){
							Bestand bs2 = new Bestand ();
							bs2.setBestandsnaam(removePath(rrs.getSourceUrl()));
							bs2.setType("XSD"); //Netter is een vertaler xmlschema->XSD
							b.add(bs2);
							
							try(  PrintWriter out = new PrintWriter( currentDir+s+rrs.getSourceUrl().substring(rrs.getSourceUrl().lastIndexOf("/")+1))){
							    out.println(  (new EditXml()).edit(rrs.getDescription()) );
							}
						} else if (rrs.getType().equalsIgnoreCase("policy")){
							//Hier mag er maar een van zijn natuurlijk!!!!!
							PrintWriter ep = new PrintWriter( currentDir+s+endpointxml);
							try(  PrintWriter out = new PrintWriter( currentDir+s+policyxml )){
							    out.println( rrs.getDescription() );
							}
							//Write endpoints to file
							generateSecuredEndpointsReport(new PrintWriter( currentDir+s+endpointxml), new File(currentDir+s+policyxml));
							//Use those endpoints for other stuff
							ep.flush();
							ep.close();
							
							String protectedServiceUrl = ""; 
							DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
							domFactory.setNamespaceAware(false);
							DocumentBuilder builder = domFactory.newDocumentBuilder();
							Document policyDocument = builder.parse(currentDir+s+endpointxml);
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
//								indexer.setProtectedEndpoint(protectedServiceUrl);
								pr.setPretectedEndpoint(protectedServiceUrl);
							}
							XmlString x = new XmlString();
							x.editXMLFile(currentDir+s+policyxml , currentDir+s+policy64xml );
							try {
								Transform.htmlTransform(currentDir+s+policy64xml, configurl+s+"xsl"+s+"policy.xsl", currentDir+s+policyhtml);
							} catch (TransformerException e) {
								logger.error("monitor:Transform "+e.getMessage());
							}
						}
					}
				}
			}

		}
		pr.setB(b);
	}
	
	
	private String removePath (String fileLocation) {
		logger.info("removePath:start");
		return fileLocation.substring(fileLocation.lastIndexOf("/")+1);
	}
	
    //Eerst alle endpoints opvragen die actief zijn
    public void generateSecuredEndpointsReport(Writer writer, File xmlFile) {
    	logger.info("generateSecuredEndpointsReport:start");
    	try {
	    	FileInputStream fileInputStream = new FileInputStream(xmlFile);
			Source xmlSource = new StreamSource(fileInputStream);
			logger.error(configurl+s+"xsl"+s+multipleendpointpath);
	    	XsltExecutable executable = getXsltExecutable(configurl+s+"xsl"+s+multipleendpointpath);
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
    		logger.fatal("generateSecuredEndpointsReport:Transform "+e.getMessage());
    		System.exit(-1);
    	}
    	logger.info("generateSecuredEndpointsReport:end");
    }
    
	private static XsltExecutable getXsltExecutable(String xslUrl) throws SaxonApiException{
		logger.info("getXsltExecutable:start");
        ClassLoader classLoader = (ClassLoader) Thread.currentThread().getContextClassLoader();
        logger.error("parent:"+classLoader.getResource(xslUrl));
        Source xsltSource = new StreamSource(classLoader.getResourceAsStream(xslUrl));
        Processor processor = new Processor(false);
        Base64Extension resourcebundleExtension = new Base64Extension();
        processor.registerExtensionFunction(resourcebundleExtension);
        XsltCompiler compiler = processor.newXsltCompiler();
        logger.info("getXsltExecutable:end");
        return compiler.compile(xsltSource);
	}
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, JAXBException, SaxonApiException, ParserConfigurationException, SAXException {
		RestMonitor anotherRESTRequest = new RestMonitor();
		anotherRESTRequest.monitor();
	}
	
    private static class PolicyNamespaceContext implements NamespaceContext {
    	
        public String getNamespaceURI(String prefix) {
        	logger.info("getNamespaceURI:start");
            if("wsp".equals(prefix)) {
                return "http://schemas.xmlsoap.org/ws/2002/12/policy";
            }if("L7p".equals(prefix)) {
                return "http://www.layer7tech.com/ws/policy";
            }
            return null;
        }

        public String getPrefix(String namespaceURI) {
        	logger.info("getPrefix:start");
            return null;
        }

        @SuppressWarnings("rawtypes")
		public Iterator getPrefixes(String namespaceURI) {
        	logger.info("getPrefixes:start");
            return null;
        }
    }
}
