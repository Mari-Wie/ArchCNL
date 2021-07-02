package org.archcnl.conformancechecking.impl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class ConformanceCheckOntologyClassesAndProperties {
    private static final String NAMESPACE =
            "http://arch-ont.org/ontologies/architectureconformance#";
    private static int violationId;
    private static int proofId;
    private static int assertedId;
    private static int notInferredId;

    public static String getOntologyNamespace() {
        return NAMESPACE;
    }

    public static DatatypeProperty get(ConformanceCheckDatatypeProperties prop, OntModel model) {
        return prop.getProperty(model);
    }

    public static ObjectProperty get(ConformanceCheckObjectProperties prop, OntModel model) {
        return prop.getProperty(model);
    }

    public enum ConformanceCheckDatatypeProperties {
        hasCheckingDate,
        hasRuleRepresentation,
        hasRuleID,
        hasProofText, // unused right now
        hasRuleType,
        hasViolationText; // unused right now

        public DatatypeProperty getProperty(OntModel model) {
            return model.getDatatypeProperty(getUri());
        }

        public String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()
                    + this.name();
        }
    }

    public enum ConformanceCheckObjectProperties {
        hasNotInferredStatement,
        hasAssertedStatement,
        hasObject,
        hasPredicate,
        hasSubject,
        proofs,
        hasDetected,
        hasViolation,
        violates,
        validates;

        public ObjectProperty getProperty(OntModel model) {
            return model.getObjectProperty(getUri());
        }

        public String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()
                    + this.name();
        }
    }
    
    public static Individual createIndividual(ConformanceCheckOntClasses clazz, OntModel model) {
        return clazz.createIndividual(model);
    }
    
    public static Individual createIndividual(ConformanceCheckOntClasses clazz, OntModel model, int id) {
        return clazz.createIndividual(model, Integer.toString(id));
    }
    
    public enum ConformanceCheckOntClasses {
        ConformanceCheck,
    	ArchitectureRule,
    	ArchitectureViolation,
    	Proof,
    	AssertedStatement,
    	NotInferredStatement;
    	
        public Individual createIndividual(OntModel model) {
            String id = getId();
            return createIndividual(model, id);
        }
    	
        public Individual createIndividual(OntModel model, String id) {
            return model.getOntClass(getUri()).createIndividual(getUri() + id);
        }
        
        public String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()  + this.name();
        }
        
        public String getId() {
        	String id;
        	
        	switch(this) {
        	case ArchitectureViolation:
        		id = Integer.toString(violationId++);
        		break;
        	case Proof:
        		id = Integer.toString(proofId++);
        		break;
        	case AssertedStatement:
        		id = Integer.toString(assertedId++);
        		break;
        	case NotInferredStatement:
        		id = Integer.toString(notInferredId++);
        		break;
        	default:
        		id = "";
        		break;
        	}
        	return id;
        }
    }
}
