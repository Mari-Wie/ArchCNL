package conformancecheck.impl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
//import org.apache.jena.rdf.model.Property;

public class ConformanceCheckOntologyClassesAndProperties {

	//TODO: remove hard-coded string
	private static String namespace = "http://www.semanticweb.org/sandr/ontologies/2018/4/architectureconformance#";
	private static int violationId;
	private static int proofId;

	public static Individual getConformanceCheckIndividual(OntModel model) {
		OntClass conformanceClass = model.getOntClass(namespace + "ConformanceCheck");
		return model.createIndividual(namespace + "ConformanceCheck", conformanceClass);
	}

	public static DatatypeProperty getDateProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasCheckingDate");
	}

	public static Individual getArchitectureRuleIndividual(OntModel model, int id) {
		OntClass ruleClass = model.getOntClass(namespace + "ArchitectureRule");
		Individual ruleIndividual = model.createIndividual(namespace + "ArchitectureRule" + id, ruleClass);
		return ruleIndividual;
	}

	public static DatatypeProperty getCNLRepresentationProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasRuleRepresentation");

	}

	public static ObjectProperty getValidatesProperty(OntModel model) {
		return model.getObjectProperty(namespace + "validates");

	}

	public static DatatypeProperty getHasRuleIDProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasRuleID");
	}

	public static Individual getArchitectureViolationIndividual(OntModel model) {
		OntClass violationClass = model.getOntClass(namespace + "ArchitectureViolation");
		return model.createIndividual(namespace + "ArchitectureViolation" + violationId++, violationClass);
	}

	public static ObjectProperty getViolatesProperty(OntModel model) {
		return model.getObjectProperty(namespace + "violates");

	}

	public static ObjectProperty getHasViolationProperty(OntModel model) {
		return model.getObjectProperty(namespace + "hasViolation");
	}

	public static ObjectProperty getHasDetectedViolationProperty(OntModel model) {
		return model.getObjectProperty(namespace + "hasDetected");
	}
	
	public static DatatypeProperty getHasViolationTextProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasViolationText");
	}
	
	public static ObjectProperty getCodeElementIsPartOfViolationSubject(OntModel model) {
		
		return model.getObjectProperty(namespace + "isPartOfViolationSubject");
	}
	
	public static ObjectProperty getCodeElementIsPartOfViolationObject(OntModel model) {
		return model.getObjectProperty(namespace + "isPartOfViolationObject");
	}
	
	public static DatatypeProperty getHasRuleTypeProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasRuleType");
	}

	public static Individual getProofIndividual(OntModel model) {
		OntClass proofClass = model.getOntClass(namespace + "Proof");
		return model.createIndividual(namespace + "Proof" + proofId++, proofClass);
	}

	public static ObjectProperty getProofsProperty(OntModel model) {
		return model.getObjectProperty(namespace + "proofs");
	}

	public static DatatypeProperty getProofsTextProperty(OntModel model) {
		return model.getDatatypeProperty(namespace + "hasProofText");
	}
	
	

}
