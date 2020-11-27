package core;

import java.util.List;
import java.util.Map;

public interface OwlifyComponent {
	public void transform();
	public String getResultPath();
	public List<String> getSourcePaths();
	public void addSourcePath(String path);
	
	/**
	 * @return 
	 * 	Returns a map which contains all OWL namespaces which are provided by the component.
	 * 	The keys are the abbreviations, and the values are the full URIs of these namespaces.
	 */
	public Map<String, String> getProvidedNamespaces();
}
