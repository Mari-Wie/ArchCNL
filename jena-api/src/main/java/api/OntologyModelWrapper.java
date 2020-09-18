package api;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;

public class OntologyModelWrapper {
	private OntModel model;
	private String pathToOWLFile;
	
	public OntologyModelWrapper(OntModel ontModel, String filePath) {
		this.model = ontModel;
		this.pathToOWLFile = filePath;
	}

	public OntModel getModel() {
		return model;
	}

	public String getPath() {
		return pathToOWLFile;
	}

	public boolean containsConcept(String serviceComponentConceptName) {
		ExtendedIterator<OntClass> iter = model.listClasses();
		while(iter.hasNext()) {
			OntClass next = iter.next();
			if(next.getLocalName().equals(serviceComponentConceptName)) {
				return true;
			}
		}
		return false;
	}
}
