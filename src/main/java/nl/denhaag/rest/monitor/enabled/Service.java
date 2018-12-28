package nl.denhaag.rest.monitor.enabled;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@XmlRootElement(name="service")
//@XmlType(namespace="http://ns.l7tech.com/2010/04/gateway-management")
public class Service {
	
	private static final Logger logger = LogManager.getLogger();
	private String enabled;
	
	public static Logger getLogger() {
		return logger;
	}
	
	
	@XmlElement(name="enabled")
	public String getEnabled(){
		logger.debug("getEnabled:start");
		return enabled;
	}

	public void setEnabled(String enabled){
		logger.debug("setEnabled:start");
		this.enabled = enabled;
		logger.debug("setEnabled:end");
	}


}
