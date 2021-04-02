package org.archcnl.architecturereasoning.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.architecturereasoning.api.ExecuteMappingAPI;
import org.archcnl.common.datatypes.ArchitectureRule;

/**
 * Implementation of the ExecuteMappingAPI interface. Can be instantiated via the
 * ExecuteMappingAPIFactory.
 */
public class ExecuteMappingAPIImpl implements ExecuteMappingAPI {
    private static final Logger LOG = LogManager.getLogger(ExecuteMappingAPIImpl.class);

    /**
     * Maps the architecture and code models onto each other using the given mapping rules.
     *
     * @param codeModel Ontology modeling the source code/implemented architecture.
     * @param architectureModel List of architecture rules modeling the (desired) architecture.
     * @param pathToMapping Path to a file containing SWRL rules which describe the
     *     architecture-to-code mapping.
     * @return An ontology modeling the code, architecture, and relations between their elements.
     */
    public Model executeMapping(
            Model codeModel, List<ArchitectureRule> architectureModel, String pathToMapping)
            throws IOException {
        ArchitectureToCodeMapper mapper = new ArchitectureToCodeMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToMapping))) {
            return mapper.executeMapping(codeModel, architectureModel, reader);

        } catch (IOException e) {
            e.printStackTrace();
            LOG.fatal(
                    "Error while accessing the architecture to code mapping \""
                            + pathToMapping
                            + "\":",
                    e);
            throw e;
        }
    }
}
