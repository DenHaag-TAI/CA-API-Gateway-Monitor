package nl.denhaag.rest.monitor.folder;

import javax.xml.bind.annotation.XmlElement;

public class Resource {

	
	private DependencyList dependencyList;

	/**
	 * @return the dependencyList
	 */
	
	@XmlElement(name="DependencyList")
	public DependencyList getDependencyList() {
		return dependencyList;
	}

	/**
	 * @param dependencyList the dependencyList to set
	 */
	public void setDependencyList(DependencyList dependencyList) {
		this.dependencyList = dependencyList;
	}
}
