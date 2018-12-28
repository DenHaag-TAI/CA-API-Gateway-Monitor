package nl.denhaag.rest.runner;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

import nl.denhaag.rest.monitor.properties.CaProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunRunner {
//	keytool -import -alias ca -file somecert.cer -keystore cacerts -storepass changeit [Return]
	
	private static final Logger logger = LogManager.getLogger();
	/* real main class */
	public static void start (String[] args) {
		logger.info ("RunRunner.start: Start start");
		CaProperties ca = new CaProperties();
		java.util.Properties cap = null;
		cap = ca.getPropertyValue();
		String starttime = cap.getProperty("starttime");
		String interval = cap.getProperty("interval");
		Duration d = null;
		if (starttime.equalsIgnoreCase("now")){
			d = Duration.ofSeconds(15);
		} else {
			LocalTime t = LocalTime.parse(starttime);
			LocalTime tt = LocalTime.now();
			if (t.isBefore(tt)){
				d = Duration.between(t, tt);
			} else {
				d = Duration.between(tt, t);
			}
		}
		
		MonitorTask.scheduledTask(((new Long(d.getSeconds())).intValue()/60),Integer.parseInt(interval));
		logger.info("RunRunner.start: End monitor");
	}
	
	
	public static void stop (String[] args){
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		if ("start".equals(args[0])) {
            start(args);
        } else if ("stop".equals(args[0])) {
            stop(args);
        }	
	}
}
