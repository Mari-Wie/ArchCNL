package conformancecheck.impl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

public class CodeModel {

	private String context; /* The context of the code model of interest */
	private Model codeModel;

	private String codeOntologyURI;
	
	public CodeModel(String context, Model codeModel) {
		
		this.context = context;
		this.codeModel = codeModel;
		//this.codeOntologyURI = "http://arch-ont.org/ontologies/javacodeontology.owl#";
		this.codeOntologyURI = "http://arch-ont.org/ontologies/famix.owl#";
	}

	public String getNameOfResource(String resourceAsString) {
		Resource resource = codeModel.getResource(resourceAsString);
		// Code Ontology concepts and properties
		Property codeElementHasNameProperty = codeModel.getProperty(this.codeOntologyURI + "hasName"); // TODO hard
																										// coded
																										// entfernen
		Statement resourceHasName = codeModel.getProperty(resource, codeElementHasNameProperty);
		if (resourceHasName != null) {
			return resourceHasName.getObject().asLiteral().toString();
		} else {
			return null;
		}
	}
	
	public Model getCodeModel() {
		return codeModel;
	}
	
	public String getContext() {
		return context;
	}

	public Resource getResource(String uriOfResource) {
		return codeModel.getResource(uriOfResource);
	}
}
