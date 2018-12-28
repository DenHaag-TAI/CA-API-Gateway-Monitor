package nl.denhaag.rest.monitor.enabled;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXB;

public class ServiceEnabledChecker {
	
	private static String filename = "index.xml";
	
	public static boolean checkExistence (String dir) {
		if (Files.notExists(Paths.get(dir+File.separator+filename))){
			return false;
		}
		
		return true;
		
	}
	
	public static boolean check (String dir) throws FileNotFoundException {
		boolean enabled = false;
		//If enabled return true
		//else return false
		
		//Bestandsnaam is index.xml
		//Op zoek naar service/enabled
//		System.out.println("FileChecker: "+dir);
		
		
		if (Files.notExists(Paths.get(dir+File.separator+filename))){
//			System.out.println("file exists");
			return enabled;
		}
		
		File f = new File(dir+File.separator+filename);
		
		
        // create new file input stream
        FileInputStream fis = new FileInputStream(f);
        
        
//        try {
//			if (fis.available()==0){         
//				System.out.println("FileChecker IV: ");
//			} else {
//				System.out.println("FileChecker IV-A: ");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("FileChecker IV-B: "+e.getMessage());
//			e.printStackTrace();
//		}
		Service s = JAXB.unmarshal(fis, Service.class);
//		System.out.println("FileChecker V: ");
//		if (s== null){
//			System.out.println("Deel van het probleem gevonden?? ");
//		} else {
//			System.out.println("s is not null");
//			if (s.getEnabled() == null){
//				System.out.println("Cannot find the enabled");
//			}
//		}
		if (s.getEnabled().equalsIgnoreCase("true")){
//			System.out.println("FileChecker VI: ");
			enabled = true;
		}
//		System.out.println("FileChecker VII: ");
		return enabled;
	}
}
