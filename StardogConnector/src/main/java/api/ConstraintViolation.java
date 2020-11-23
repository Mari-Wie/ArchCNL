package api;

import java.util.ArrayList;
import java.util.List;
//
//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.rdf.model.Property;
//import org.apache.jena.rdf.model.Resource;
//import org.apache.jena.rdf.model.Statement;

public class ConstraintViolation {


	private List<StatementTriple> asserted;
	private List<StatementTriple> notInferred;

//	private String notInferredObjectName;
//	private String notInferredSubjectName;
//	private List<String> proofs;

	public ConstraintViolation() {
		asserted = new ArrayList<>();
		notInferred = new ArrayList<>();
//		proofs = new ArrayList<>();
	}

	public void setViolation(String subjectAsserted, String predicateAsserted, String objectAsserted) {
		StatementTriple triple = new StatementTriple(subjectAsserted, predicateAsserted, objectAsserted);
		asserted.add(triple);

	}

	public void setNotInferred(String subjectNotInferred, String predicateNotInferred, String objectNotInferred) {
		StatementTriple triple = new StatementTriple(subjectNotInferred, predicateNotInferred, objectNotInferred);
		notInferred.add(triple);
	}

	public List<StatementTriple> getAsserted() {
		return asserted;
	}

//	public List<StatementTriple> getNotInferred() {
//		return notInferred;
//	}

//	private String getNameOfResource(Model model, String codeOntologyURI, String resourceAsString) {
//		Resource resource = model.getResource(resourceAsString);
//		// Code Ontology concepts and properties
//		Property codeElementHasNameProperty = model.getProperty(codeOntologyURI + "hasName");
//		Statement resourceHasName = model.getProperty(resource, codeElementHasNameProperty);
//		if (resourceHasName != null) {
//			return resourceHasName.getObject().asLiteral().toString();
//		} else {
//			return null;
//		}
//	}

//	public String getNotInferredObjectName() {
//		
//		return notInferredObjectName;
//	}

//	public void setNotInferredObjectName(String notInferredObjectName) {
//		this.notInferredObjectName = notInferredObjectName;
//	}

//	public String getNotInferredSubjectName() {
//		return notInferredSubjectName;
//	}

//	public void setNotInferredSubjectName(String notInferredSubjectName) {
//		this.notInferredSubjectName = notInferredSubjectName;
//	}

//	public void setAsserted(List<StatementTriple> asserted) {
//		this.asserted = asserted;
//	}

//	public void setNotInferred(List<StatementTriple> notInferred) {
//		this.notInferred = notInferred;
//	}
//
//	public void addProof(String proof) {
//		proofs.add(proof);
//	}

//	public List<String> getProofs() {
//		return proofs;
//	}
//	
	
}
