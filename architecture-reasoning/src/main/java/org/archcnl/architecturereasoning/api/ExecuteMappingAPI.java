package org.archcnl.architecturereasoning.api;

import java.io.IOException;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.archcnl.common.datatypes.ArchitectureRule;

/**
 * API for the mapping between architectural concepts used in architecture rules and the code model.
 * Generates an OWL ontology file in which the code model, the architecture model, and the mapping
 * between them are contained. This mapping is performed according to some architecture-to-code
 * mapping rules, which must be specified in an SWRL file.
 */
public interface ExecuteMappingAPI {
    /**
     * Maps the code and architecture models onto each other.
     *
     * @param codeModel The ontology model describing the source code.
     * @param architectureModel List containing all architecture rules.
     * @param pathToMapping Path to the SWRL file containing the architecture-to-code mapping.
     * @return The mapped model describing the code and architecture as well as the relation between
     *     their elements.
     * @throws IOException When accessing the mapping file fails.
     */
    public Model executeMapping(
            Model codeModel, List<ArchitectureRule> architectureModel, String pathToMapping)
            throws IOException;
}
