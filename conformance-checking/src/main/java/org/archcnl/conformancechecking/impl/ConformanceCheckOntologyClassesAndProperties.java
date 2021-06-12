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

    public static DatatypeProperty getDateProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE + "hasCheckingDate");
    }

    public static Individual getArchitectureRuleIndividual(OntModel model, int id) {
        OntClass ruleClass = model.getOntClass(NAMESPACE + "ArchitectureRule");
        return model.createIndividual(NAMESPACE + "ArchitectureRule" + id, ruleClass);
    }

    public static DatatypeProperty getCNLRepresentationProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE + "hasRuleRepresentation");
    }

    public static ObjectProperty getValidatesProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "validates");
    }

    public static DatatypeProperty getHasRuleIDProperty(OntModel model) {

        return model.getDatatypeProperty(NAMESPACE + "hasRuleID");
    }

    public static Individual getArchitectureViolationIndividual(OntModel model) {
        OntClass violationClass = model.getOntClass(NAMESPACE + "ArchitectureViolation");
        return model.createIndividual(
                NAMESPACE + "ArchitectureViolation" + violationId++, violationClass);
    }

    public static ObjectProperty getViolatesProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "violates");
    }

    public static ObjectProperty getHasViolationProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasViolation");
    }

    public static ObjectProperty getHasDetectedViolationProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasDetected");
    }

    public static DatatypeProperty getHasViolationTextProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE + "hasViolationText");
    }

    public static DatatypeProperty getHasRuleTypeProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE + "hasRuleType");
    }

    public static Individual getProofIndividual(OntModel model) {
        OntClass proofClass = model.getOntClass(NAMESPACE + "Proof");
        return model.createIndividual(NAMESPACE + "Proof" + proofId++, proofClass);
    }

    public static ObjectProperty getProofsProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "proofs");
    }

    public static DatatypeProperty getProofsTextProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE + "hasProofText");
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

    public static ObjectProperty getHasSubject(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasSubject");
    }

    public static ObjectProperty getHasPredicate(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasPredicate");
    }

    public static ObjectProperty getHasObject(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasObject");
    }

    public static ObjectProperty getHasAssertedStatement(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasAssertedStatement");
    }

    public static ObjectProperty getHasNotInferredStatement(OntModel model) {
        return model.getObjectProperty(NAMESPACE + "hasNotInferredStatement");
    }
}
