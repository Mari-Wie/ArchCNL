package org.archcnl.common.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a violation of an OWL integrity constraint in OWL syntax.
 *
 * <p>Instances are immutable (value objects).
 */
public class ConstraintViolation {
    private final List<StatementTriple> asserted;
    private final List<StatementTriple> notInferred;

    /**
     * Constructor.
     *
     * @param assertedStatements List of statements which have been asserted.
     * @param notInferredStatements List of statements which could not be inferred.
     */
    public ConstraintViolation(
            List<StatementTriple> assertedStatements, List<StatementTriple> notInferredStatements) {
        // copy to prevent modifications
        asserted = new ArrayList<>(assertedStatements);
        notInferred = new ArrayList<>(notInferredStatements);
    }

    /** Returns the list of asserted statements. */
    public List<StatementTriple> getAsserted() {
        // return a copy to prevent modifications
        return new ArrayList<>(asserted);
    }

    /** Returns the list of statements which could not be inferred. */
    public List<StatementTriple> getNotInferred() {
        // return a copy to prevent modifications
        return new ArrayList<>(notInferred);
    }

    /** Builder for ConstraintViolations */
    public static class ConstraintViolationBuilder {
        private List<StatementTriple> assertedStatements = new ArrayList<>();
        private List<StatementTriple> notInferredStatements = new ArrayList<>();

        /**
         * Add an asserted OWL statement (subject predicate object) to this violation.
         *
         * @param subjectAsserted the URI of the subject
         * @param predicateAsserted the URI of the predicate
         * @param objectAsserted the URI of the object
         */
        public void addViolation(
                String subjectAsserted, String predicateAsserted, String objectAsserted) {
            StatementTriple triple =
                    new StatementTriple(subjectAsserted, predicateAsserted, objectAsserted);
            assertedStatements.add(triple);
        }

        /**
         * Add a OWL statement (subject predicate object) which could not be inferred to this
         * violation.
         *
         * @param subjectAsserted the URI of the subject
         * @param predicateAsserted the URI of the predicate
         * @param objectAsserted the URI of the object
         */
        public void addNotInferredStatement(
                String subjectNotInferred, String predicateNotInferred, String objectNotInferred) {
            StatementTriple triple =
                    new StatementTriple(
                            subjectNotInferred, predicateNotInferred, objectNotInferred);
            notInferredStatements.add(triple);
        }

        /** Builds an instance from the current state. */
        public ConstraintViolation build() {
            return new ConstraintViolation(assertedStatements, notInferredStatements);
        }
    }
}
