package org.archcnl.domain.output.repository;

import java.util.Optional;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;

/** Result repository (Stardog Client API) */
public interface ResultRepository {

    /**
     * Execute native SELECT query, for example:
     *
     * <p>
     * SELECT ?field WHERE { ?field rdf:type 'Value'^^^xsd:string. }
     *
     * @param query Query as domain object.
     * @return Result or Optional.empty when nothing found
     */
    Optional<Result> executeNativeSelectQuery(final Query query);
}
