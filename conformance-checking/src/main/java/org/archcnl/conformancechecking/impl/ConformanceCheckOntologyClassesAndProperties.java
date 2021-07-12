package org.archcnl.conformancechecking.impl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
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

    public static Individual createIndividual(ConformanceCheckOntClasses clazz, OntModel model) {
        return clazz.createIndividual(model);
    }

    public static Individual createIndividual(
            ConformanceCheckOntClasses clazz, OntModel model, int id) {
        return clazz.createIndividual(model, id);
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

        private String getUri() {
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

        private String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()
                    + this.name();
        }
    }

    public enum ConformanceCheckOntClasses {
        ConformanceCheck,
        ArchitectureRule,
        ArchitectureViolation,
        Proof,
        AssertedStatement,
        NotInferredStatement;

        public Individual createIndividual(OntModel model) {
            return createIndividual(model, getId());
        }

        public Individual createIndividual(OntModel model, int id) {
        	//id == -1 indicates that no id counter exists
            if (id == -1) {
                return model.getOntClass(getUri()).createIndividual(getUri());
            } else {
                return model.getOntClass(getUri()).createIndividual(getUri() + id);
            }
        }

        private String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()
                    + this.name();
        }

        /**
         * Returns and increments the id counter for the given class
         * @return the current id counter of the class or -1 when no id counter exists
         */
        private int getId() {
        	switch(this) {
            case ArchitectureViolation:
                return violationId++;
            case Proof:
            	return proofId++;
            case AssertedStatement:
            	return assertedId++;
            case NotInferredStatement:
                return notInferredId++;
            default:
                return -1;
        	}
        }
    }
}
