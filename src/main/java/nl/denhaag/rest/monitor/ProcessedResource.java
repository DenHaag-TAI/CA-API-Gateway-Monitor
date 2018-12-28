package nl.denhaag.rest.monitor;

import java.util.ArrayList;

import nl.denhaag.rest.monitor.index.Bestand;

public class ProcessedResource {
	
	
	private ArrayList<Bestand> b;
	private String pretectedEndpoint;
	/**
	 * @return the b
	 */
	public ArrayList<Bestand> getB() {
		return b;
	}
	/**
	 * @param b the b to set
	 */
	public void setB(ArrayList<Bestand> b) {
		this.b = b;
	}
	/**
	 * @return the pretectedEndpoint
	 */
	public String getPretectedEndpoint() {
		return pretectedEndpoint;
	}
	/**
	 * @param pretectedEndpoint the pretectedEndpoint to set
	 */
	public void setPretectedEndpoint(String pretectedEndpoint) {
		this.pretectedEndpoint = pretectedEndpoint;
	}
	
	

}
