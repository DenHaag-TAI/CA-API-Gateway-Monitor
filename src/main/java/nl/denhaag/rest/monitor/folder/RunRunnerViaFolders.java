package nl.denhaag.rest.monitor.folder;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import nl.denhaag.rest.monitor.directory.CheckIfDirectoryExists;
import nl.denhaag.rest.monitor.enabled.ServiceEnabledChecker;
import nl.denhaag.rest.monitor.properties.CaProperties;
import nl.denhaag.rest.monitor.remove.CurrentServices;
import nl.denhaag.rest.monitor.zip.Zipper;
import nl.denhaag.rest.service.Item;
import nl.denhaag.rest.service.processer.IndexHtml;
import nl.denhaag.rest.service.processer.Indexer;
import nl.denhaag.rest.service.processer.Processor;
import nl.denhaag.rest.transformations.Transform;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;


public class RunRunnerViaFolders {
	
	private static final Logger logger = LogManager.getLogger();
	
	
	public void monitor() throws IOException, SAXException , ParserConfigurationException, KeyManagementException, NoSuchAlgorithmException {
		logger.info("RunRunnerViaFolders.monitor: start monitor");
		String indexxml = "index.xml";
		String indexhtml = "index.html";
		String s = System.getProperty("file.separator");
		String baseUrl = "";
//		String webUrl = "";
		String zipsUrl = "";
		String configUrl = "";
		boolean skip = false;
		
		CaProperties ca = new CaProperties();
		java.util.Properties cap = ca.getPropertyValue();
		configUrl = cap.getProperty("configurl");
		baseUrl = cap.getProperty("baseurl"); 
		zipsUrl = baseUrl + s + "zips";
		
		String authString = cap.getProperty("username") + ":" + cap.getProperty("password");
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		
		Files.createDirectories(Paths.get(baseUrl+s+"web"));
		Files.createDirectories(Paths.get(baseUrl+s+"zips"));
		
		String caurl =cap.getProperty("caurl");
		String folderurl =cap.getProperty("folderurl");
		CurrentServices c = new CurrentServices();
		c.setAll(baseUrl+s+"web");
		SSLContextBuilder sslContextBuilder = SSLContexts.custom();
		SSLContext sslContext = sslContextBuilder.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpClientx = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
		HttpGet request = new HttpGet(folderurl);
		request.addHeader("User-Agent", "Mozilla/5.0");
		request.addHeader("Authorization", "Basic " + authStringEnc);
		Folders l = null;
		try {
			HttpResponse response = httpClientx.execute(request);
			l = JAXB.unmarshal(new InputStreamReader(response.getEntity().getContent()), nl.denhaag.rest.monitor.folder.Folders.class);
		} catch (IOException e){
			// Gateway niet beschikbaar, nu leidt deze error niet toe dat dit programma stopt (eerder wel, nl via het gooien van de exceptie)
			skip = true;
			logger.warn("RunRunnerViaFolders.monitor: gateway not available - skip run");
		}
		
		if (!skip){
			Dependencies d = l.getResource().getDependencyList().getDependencies();
			ShowDeps.noshow(l.getResource().getDependencyList().getDependencies(), Optional.<String>empty(), 0);
			
			for (Dependency dd : d.getDependencies()){
				logger.trace("RunRunnerViaFolders.monitor: start loop Dependency "+dd.getName());
				if (dd.getType().equals("SERVICE")){
					logger.trace("RunRunnerViaFolders.monitor: start if SERVICE");
					CloseableHttpClient httpClientxx = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
					//Nu de gevonden urls uitvoeren
					HttpGet requestService = new HttpGet(caurl+"/"+dd.getId());
					requestService.addHeader("User-Agent", "Mozilla/5.0");
					requestService.addHeader("Authorization", "Basic " + authStringEnc);
					HttpResponse responseService = httpClientxx.execute(requestService);
					Item i = JAXB.unmarshal(new InputStreamReader(responseService.getEntity().getContent()), Item.class);
					
					Processor p = new Processor();
					Indexer ix = new Indexer();
					
					p.setBaseUrl(baseUrl);
					p.setConfigUrl(configUrl);
					p.setIndexer(ix);
					p.itemProcessor(i);
					//Uit de lijst halen, alleen directories die in de lijst staan worden uiteindelijk verwijderd
					c.remove(ix.getId()+"-"+ix.getName().replaceAll("/", " ").trim()+"-v"+ix.getVersion()+"-pv"+ix.getPolicyVersion());
					boolean existence = ServiceEnabledChecker.checkExistence(baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion());
					//boolean enabled = ServiceEnabledChecker.check(baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion());
					boolean wasEnabled = ix.getEnabled().equalsIgnoreCase("true");
					//executed when enabled en wasEnabled are different or when index.html is not found
					if (/*(enabled == wasEnabled) == false ||*/ existence == false){
						logger.trace("RunRunnerViaFolders.monitor: start if (enabled == wasEnabled) == false");
						//Aan de slag
						IndexHtml ih = new IndexHtml();
						//Nu de naam verzinnen
						try {
							ih.doIt(baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion(), ix);
						} catch (JAXBException e) {
							logger.error("RunRunnerViaFolders.monitor: "+e.getMessage());
						}
						try {
							Transform.htmlTransform(baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion()+s+indexxml, configUrl+s+"xsl"+s+"html.xsl", baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion()+s+indexhtml);
						} catch (TransformerException e) {
							logger.error("RunRunnerViaFolders.monitor: "+e.getMessage());
						}
						Zipper.pack(baseUrl+s+"web"+s + ix.getId() +"-" +ix.getName().replaceAll("/", " ").trim() +"-v" + ix.getVersion() +"-pv" +ix.getPolicyVersion(), zipsUrl+s+ix.getId()+"-"+ix.getName().replaceAll("/", " ").trim()+"-v"+ix.getVersion()+"-pv"+ix.getPolicyVersion()+".zip");
						logger.trace("RunRunnerViaFolders.monitor: end if (enabled == wasEnabled) == false");
					}
					String base = baseUrl + s + "web";
					Files.list(Paths.get(base)).forEach(path -> CheckIfDirectoryExists.delete(Paths.get(base), path, ix.getId(),i.getId()+"-"+i.getName().replaceAll("/", " ").trim()+"-v"+ix.getVersion()+"-pv"+ix.getPolicyVersion()));
					logger.trace("RunRunnerViaFolders.monitor: end if SERVICE");
				}
				logger.trace("RunRunnerViaFolders.monitor: end loop Dependency "+dd.getName());
			}
			for (String dir : c.getCurrentServices()){
				logger.trace("RunRunnerViaFolders.monitor: start loop deleteDirectory "+dir);
				try{
					FileUtils.deleteDirectory(new File (baseUrl+s+"web"+s+dir));
				} catch (IOException e){
					logger.warn("RunRunnerViaFolders.monitor: failed to delete directory: "+e.getMessage());
				}
				logger.trace("RunRunnerViaFolders.monitor: start end deleteDirectory "+dir);
			}
		}	
		logger.info("RunRunnerViaFolders.monitor: end monitor");
		//System.exit(0);
	}	
	
	public static void main(String[] args) throws ClientProtocolException, IOException, SAXException, ParserConfigurationException, KeyManagementException, NoSuchAlgorithmException {
		RunRunnerViaFolders runnerViaFolders = new RunRunnerViaFolders();
		runnerViaFolders.monitor();
		
	}
}
