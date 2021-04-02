package org.archcnl.architecturereasoning.impl;

import java.io.BufferedReader;
import java.util.List;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.reasoner.rulesys.Rule.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ArchitectureRule;

public class ArchitectureToCodeMapper {

    private static final Logger LOG = LogManager.getLogger(ArchitectureToCodeMapper.class);

    /**
     * Maps the architecture and code models onto each other using the given mapping rules.
     *
     * @param codeModel Ontology modeling the source code/implemented architecture.
     * @param architectureModel List of architecture rules modeling the (desired) architecture.
     * @param mappingRules BufferedReader containing SWRL rules which describe the
     *     architecture-to-code mapping. Must be closed by the caller.
     * @return An ontology modeling the code, architecture, and relations between their elements.
     */
    public Model executeMapping(
            Model codeModel,
            List<ArchitectureRule> architectureModel,
            BufferedReader mappingRules) {
        Model unitedModel = createUnitedModel(codeModel, architectureModel);
        Reasoner reasoner = createMappingReasoner(mappingRules);

        return ModelFactory.createInfModel(reasoner, unitedModel);
    }

    private Model createUnitedModel(Model codeModel, List<ArchitectureRule> architectureModel) {
        Model unitedModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        unitedModel.add(codeModel);

        for (ArchitectureRule rule : architectureModel) {
            unitedModel.add(rule.getRuleModel());
        }

        return unitedModel;
    }

    private Reasoner createMappingReasoner(BufferedReader reader) {
        LOG.debug("reading mapping rules");
        Parser parser = Rule.rulesParserFromReader(reader);
        return new GenericRuleReasoner(Rule.parseRules(parser));
    }
}
