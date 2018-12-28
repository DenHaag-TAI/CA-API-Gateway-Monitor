package nl.denhaag.rest.monitor.folder;

import javax.xml.bind.annotation.XmlElement;

public class DependencyList {
	
	
	private Reference reference;
	
	private Dependencies dependencies;
	
	@XmlElement(name="Dependencies")
	public Dependencies getDependencies() {
		return dependencies;
	}
	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(Dependencies dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @return the reference
	 */
	@XmlElement(name="Reference")
	public Reference getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
	
	

}
