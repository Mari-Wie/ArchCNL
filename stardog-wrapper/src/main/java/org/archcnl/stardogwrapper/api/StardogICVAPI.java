package org.archcnl.stardogwrapper.api;

import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.archcnl.stardogwrapper.api.exceptions.DBAccessException;

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
     * ontology to the database.
     *
     * @param constraintModel The ontology modeling the constraints.
     * @return A string representation of the added constraint.
     * @throws DBAccessException When accessing the database fails.
     */
    public String addIntegrityConstraint(Model constraintModel) throws DBAccessException;

    /** Connects to the given database and removes all integrity constraints from it. */
    void removeIntegrityConstraints();
}
