package org.archcnl.stardogwrapper.api;

import java.io.FileNotFoundException;
import java.util.List;

public interface StardogICVAPI {

    /**
     * Connects to the given database and checks whether each of the current integrity constraints
     * is violated in the given context. The explanations/proofs of identified violations are
     * returned in a list. The list contains one <code>ConstraintViolationsResultSet</code> for each
     * integrity constraint present in the database.
     *
     * @param context The URI of the RDF context to use.
     * @return the list of all identified violations and their explanations
     */
    public List<ConstraintViolationsResultSet> explainViolationsForContext(String context);

    /**
     * Connects to the given database and adds the integrity constraints stored in the specified
     * file to the database.
     *
     * @param pathToConstraints The path to the RDF-file in XML format which stores the integrity
     *     constraints to add.
     * @return A string representation of the added constraint.
     * @throws FileNotFoundException When the input file cannot be accessed.
     */
    public String addIntegrityConstraint(String pathToConstraint) throws FileNotFoundException;

    /** Connects to the given database and removes all integrity constraints from it. */
    void removeIntegrityConstraints();
}
