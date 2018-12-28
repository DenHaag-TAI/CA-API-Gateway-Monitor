package nl.denhaag.rest.monitor.folder;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Dependencies {
	
	private ArrayList<Dependency> dependencies;

	/**
	 * @return the dependencies
	 */
	@XmlElement(name="Dependency")
	public ArrayList<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies the dependencies to set
	 */
	public void setDependencies(ArrayList<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
}
