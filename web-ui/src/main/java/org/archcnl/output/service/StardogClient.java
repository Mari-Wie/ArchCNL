package org.archcnl.output.service;

import java.util.Optional;
import org.archcnl.output.model.query.Query;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;

/** Stardog Client API */
public interface StardogClient {

    /**
     * Execute native SELECT query, for example:
     *
     * <p>SELECT ?field WHERE { ?field rdf:type 'Value'^^^xsd:string. }
     *
     * @param query Query as domain object.
     * @return Result or Optional.empty when nothing found
     */
    Optional<Result> executeNativeSelectQuery(final Query query);
}
