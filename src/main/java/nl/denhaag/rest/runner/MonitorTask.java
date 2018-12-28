package nl.denhaag.rest.runner;


import java.io.IOException;

import java.security.KeyManagementException;
//import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
//import java.time.Duration;
//import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

//import net.sf.saxon.s9api.SaxonApiException;
import nl.denhaag.rest.monitor.folder.RunRunnerViaFolders;
//import nl.denhaag.rest.monitor.properties.CaProperties;
//import nl.denhaag.twb.layer7.Layer7MonitorTask;
//import nl.denhaag.twb.util.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class MonitorTask implements Runnable{
	
	int delay;
	int interval;
	
	public MonitorTask (int delay,  int interval){
		this.delay = delay;
		this.interval = interval;
	}
	
	private static final Logger logger = LogManager.getLogger();
	
	public static void scheduledTask (int delay,  int interval ) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		//executor.shutdownNow()  ==> hier iets meedoen om de taak te beeindigen in code
			Runnable periodicTask = new Runnable() {
			    public void run() {
			    	RunRunnerViaFolders r = new RunRunnerViaFolders();
					try {
						logger.info("MonitorTask.scheduledTaskStart: start monitor");
						r.monitor();
						logger.info("MonitorTask.scheduledTaskStart: end monitor");
					} catch ( IOException | ParserConfigurationException | SAXException | KeyManagementException | NoSuchAlgorithmException e) {
						logger.fatal("MonitorTask.scheduledTaskStart: monitor failed to start "+e.getMessage());
						System.exit(-1);
					}
			    }
			};
			logger.debug("MonitorTask.scheduledTaskStart: monitor over {} minutes with interval {}", delay, interval);
			executor.scheduleAtFixedRate(periodicTask, delay, interval, TimeUnit.MINUTES);
			logger.debug("MonitorTask.scheduledTaskStart: end");
	}
	
	
	public void run()  {
		MonitorTask.scheduledTask(delay,interval);
	}
	
	
//	public static void main(String args[]) throws InterruptedException, IOException {
//		CaProperties ca = new CaProperties();
//		java.util.Properties cap = ca.getPropertyValue();
//		String starttime = cap.getProperty("starttime");
//		String interval = cap.getProperty("interval");
//		if (starttime.equalsIgnoreCase("now")){
//			MonitorTask.scheduledTask(0,Integer.parseInt(interval));
//		} else {
//			LocalTime t = LocalTime.parse(starttime);
//			LocalTime tt = LocalTime.now();
//			Duration d = null; 
//			
//			if (t.isBefore(tt)){
//				d = Duration.between(t, tt);
//			} else {
//				d = Duration.between(tt, t);
//			}
//			MonitorTask.scheduledTask(((new Long(d.getSeconds())).intValue()/60),Integer.parseInt(interval));
//		}
//	}
}
