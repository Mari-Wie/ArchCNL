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

    public static Individual getConformanceCheckIndividual(OntModel model) {
        OntClass conformanceClass = model.getOntClass(NAMESPACE + "ConformanceCheck");
        return model.createIndividual(NAMESPACE + "ConformanceCheck", conformanceClass);
    }

    public static Individual getArchitectureRuleIndividual(OntModel model, int id) {
        OntClass ruleClass = model.getOntClass(NAMESPACE + "ArchitectureRule");
        return model.createIndividual(NAMESPACE + "ArchitectureRule" + id, ruleClass);
    }

    public static Individual getArchitectureViolationIndividual(OntModel model) {
        OntClass violationClass = model.getOntClass(NAMESPACE + "ArchitectureViolation");
        return model.createIndividual(
                NAMESPACE + "ArchitectureViolation" + violationId++, violationClass);
    }

    public static Individual getProofIndividual(OntModel model) {
        OntClass proofClass = model.getOntClass(NAMESPACE + "Proof");
        return model.createIndividual(NAMESPACE + "Proof" + proofId++, proofClass);
    }

    public static Individual getAssertedStatement(OntModel model) {
        OntClass statementClass = model.getOntClass(NAMESPACE + "AssertedStatement");
        return model.createIndividual(
                NAMESPACE + "AssertedStatement" + assertedId++, statementClass);
    }

    public static Individual getNotInferredStatement(OntModel model) {
        OntClass statementClass = model.getOntClass(NAMESPACE + "NotInferredStatement");
        return model.createIndividual(
                NAMESPACE + "NotInferredStatement" + notInferredId++, statementClass);
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
}
