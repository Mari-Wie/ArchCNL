package org.archcnl.conformancechecking.impl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class ConformanceCheckOntologyClassesAndProperties {
    private static final String namespace =
            "http://arch-ont.org/ontologies/architectureconformance#";
    private static int violationId;
    private static int proofId;
    private static int assertedId;
    private static int notInferredId;

    public static String getOntologyNamespace() {
        return namespace;
    }

    public static Individual getConformanceCheckIndividual(OntModel model) {
        OntClass conformanceClass = model.getOntClass(namespace + "ConformanceCheck");
        return model.createIndividual(namespace + "ConformanceCheck", conformanceClass);
    }

    public static DatatypeProperty getDateProperty(OntModel model) {
        return model.getDatatypeProperty(namespace + "hasCheckingDate");
    }

    public static Individual getArchitectureRuleIndividual(OntModel model, int id) {
        OntClass ruleClass = model.getOntClass(namespace + "ArchitectureRule");
        return model.createIndividual(namespace + "ArchitectureRule" + id, ruleClass);
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
        return model.createIndividual(
                namespace + "ArchitectureViolation" + violationId++, violationClass);
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

    public static Individual getAssertedStatement(OntModel model) {
        OntClass statementClass = model.getOntClass(namespace + "AssertedStatement");
        return model.createIndividual(
                namespace + "AssertedStatement" + assertedId++, statementClass);
    }

    public static Individual getNotInferredStatement(OntModel model) {
        OntClass statementClass = model.getOntClass(namespace + "NotInferredStatement");
        return model.createIndividual(
                namespace + "NotInferredStatement" + notInferredId++, statementClass);
    }

    public static ObjectProperty getHasSubject(OntModel model) {
        return model.getObjectProperty(namespace + "hasSubject");
    }

    public static ObjectProperty getHasPredicate(OntModel model) {
        return model.getObjectProperty(namespace + "hasPredicate");
    }

    public static ObjectProperty getHasObject(OntModel model) {
        return model.getObjectProperty(namespace + "hasObject");
    }

    public static ObjectProperty getHasAssertedStatement(OntModel model) {
        return model.getObjectProperty(namespace + "hasAssertedStatement");
    }

    public static ObjectProperty getHasNotInferredStatement(OntModel model) {
        return model.getObjectProperty(namespace + "hasNotInferredStatement");
    }
}
