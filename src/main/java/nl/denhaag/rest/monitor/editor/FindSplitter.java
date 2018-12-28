package nl.denhaag.rest.monitor.editor;

//import static java.lang.Character.*;

public class FindSplitter {
	
	public String check(String waarde, String splitter){
		
		String newWaarde = waarde;
		String newDir = splitter;
		//http://schemas.dmtf.org/wbem/wsman/1
		//file://__ssginternal
		
		//Let op: indien een service bestanden niet op file:// worden gevonden maar op http; dan moeten we een gekke splitter teruggeven
		//die zeker niet gevonden gaat worden de service.
		
		if (waarde.startsWith("http") || newDir.equalsIgnoreCase("NOTWITHFILE")){
			return "NOTWITHFILE";
		}

		
		//Moet helaas met een loop ofzo, want nu komen er teveel waarden terug
		boolean continueWhile = true;
		String value = null;
		while (continueWhile){
			if (newWaarde.startsWith(newDir)){
				value = newDir;
				continueWhile = false;
			}
			newDir = subdir (newDir);
		}
		return value;
	}
	
	
	private String subdir (String longdir){
		int x = longdir.lastIndexOf("/");
		String y = longdir.substring (0,x);
		return y;
	}
	
	
//	public static void main(String[] args) throws InterruptedException {
//		FindSplitter f = new FindSplitter();
////		
////		
////		f.check("file:/Kimmie/pietjepuk");
////		String g = "äghfdjhsagd".replace("d", char(3));
//		
////		System.out.println ("brr");
////		for (int i=0; i<MAX_CODE_POINT; i++) {
////		    int t = getType(i);
////		    boolean p = t == CONTROL || t == CONNECTOR_PUNCTUATION || t == CURRENCY_SYMBOL || t == DASH_PUNCTUATION || t == DECIMAL_DIGIT_NUMBER || t == ENCLOSING_MARK || t == END_PUNCTUATION || t == FINAL_QUOTE_PUNCTUATION || t == INITIAL_QUOTE_PUNCTUATION || t == LETTER_NUMBER || t == LOWERCASE_LETTER || t == MATH_SYMBOL || t == MODIFIER_LETTER || t == MODIFIER_SYMBOL || t == OTHER_LETTER || t == OTHER_NUMBER || t == OTHER_PUNCTUATION || t == OTHER_SYMBOL || t == START_PUNCTUATION || t == TITLECASE_LETTER || t == UPPERCASE_LETTER;
////		    if (!p) {
////		        System.out.println("Non printable codepoint " + i);
////		        Thread.sleep(100);
////		    }
////		}   
//		
//		
//		String file = "file:/Y:/wsdl/a/INTERN/idc/planon/Webservices_PlanonV1.0%20/InterfaceEventus.wsdl".replace("(","\32").replace(")", "\33");
//		
//		System.out.println("Bestand tot directory "+file.substring(0,file.lastIndexOf("/")));
//		
//		String directory = "file:/Y:/wsdl/a/INTERN/idc/planon/Webservices_PlanonV1.0%20".replace ("(","\32").replace(")", "\33");
//		
//		
//		String [] split =  file.split(directory);
//		System.err.println ("splitter: "+directory);
//		System.err.println ("resource: "+file);
//		System.err.println ("result: "+split[1].replace ("\32","(").replace("\33",")" ));
//		System.err.println ("size: "+split.length);
//
//		
////		f.subdir (directory);
////		System.out.println(f.check("file:/Y:/soapui/a/INTERN/bsd/abk/Formulier%20webservice%20INTERNE%20WSG%20A%20OntvangMutaties%20GUCT1A-M13041836/OntvangAsynchroon/BAG/BAG-schemasOfficieel/gml/GML-3.1.1/base", "file:/Y:/soapui/a/INTERN/bsd/abk/Formulier%20webservice%20INTERNE%20WSG%20A%20OntvangMutaties%20GUCT1A-M13041836/OntvangAsynchroon/bg0310/mutatie"));
//		
//		
//	}

}
