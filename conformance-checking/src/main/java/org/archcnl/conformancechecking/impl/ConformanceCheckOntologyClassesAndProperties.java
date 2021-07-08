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
            ConformanceCheckOntClasses clazz, OntModel model, Integer id) {
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

    public enum ConformanceCheckOntClasses {
        ConformanceCheck,
        ArchitectureRule,
        ArchitectureViolation {
            @Override
            protected Integer getId() {
                return violationId++;
            }
        },
        Proof {
            @Override
            protected Integer getId() {
                return proofId++;
            }
        },
        AssertedStatement {
            @Override
            protected Integer getId() {
                return assertedId++;
            }
        },
        NotInferredStatement {
            @Override
            protected Integer getId() {
                return notInferredId++;
            }
        };

        public Individual createIndividual(OntModel model) {
            Integer id = getId();
            return createIndividual(model, id);
        }

        public Individual createIndividual(OntModel model, Integer id) {
            if (id == null) {
                return model.getOntClass(getUri()).createIndividual(getUri());
            } else {
                return model.getOntClass(getUri()).createIndividual(getUri() + id);
            }
        }

        public String getUri() {
            return ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace()
                    + this.name();
        }

        protected Integer getId() {
            return null;
        }
    }
}
