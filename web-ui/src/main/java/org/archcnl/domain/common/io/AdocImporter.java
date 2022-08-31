package org.archcnl.domain.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.architecturedescriptionparser.AsciiDocCNLSentenceExtractor;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.common.io.importhelper.DescriptionParser;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.QueryParser;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;

public class AdocImporter {

    private static final Logger LOG = LogManager.getLogger(AdocImporter.class);

    private static final Pattern CONCEPT_MAPPING_NAME = Pattern.compile("(?<=is)\\w+(?=:)");
    private static final Pattern RELATION_MAPPING_NAME = Pattern.compile(".+(?=Mapping:)");

    public void readFromAdoc(
            File file,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Queue<FreeTextQuery> freeTextQueryQueue,
            Queue<Query> customQueryQueue)
            throws IOException {

        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        fileContent = fileContent.replace("\r", "");

        /*
         * //TODO probably check if the methods from the common module can be used here
         * to extract the archRules from a adoc file. It is in place for archRules
         * but for consistency, it should be done the same.
         * Also maybe there is no need for the model Object in the UI and it is possible to use the one from common.
         */
        Map<String, String> conceptDescriptions =
                DescriptionParser.extractConceptDescriptions(
                        fileContent, AdocImporter.CONCEPT_MAPPING_NAME);
        Map<String, String> relationDescriptions =
                DescriptionParser.extractRelationDescriptions(
                        fileContent, AdocImporter.RELATION_MAPPING_NAME);

        List<org.archcnl.common.datatypes.ArchitectureRule> lines =
                new AsciiDocCNLSentenceExtractor(file.toPath()).extractArchitectureRules();

        ruleManager.addAllArchitectureRules(
                lines.stream()
                        .map(
                                s ->
                                        new ArchitectureRule(
                                                s.getCnlSentence(),
                                                s.getValidFrom(),
                                                s.getValidUntil()))
                        .collect(Collectors.toList()));

        List<CustomConcept> concepts =
                MappingParser.extractCustomConcepts(
                        fileContent,
                        CONCEPT_MAPPING_NAME,
                        conceptDescriptions,
                        relationManager,
                        conceptManager);
        concepts.forEach(
                concept -> {
                    try {
                        conceptManager.addOrAppend(concept);
                    } catch (UnrelatedMappingException e) {
                        AdocImporter.LOG.warn(e.getMessage());
                    }
                });

        List<CustomRelation> relations =
                MappingParser.extractCustomRelations(
                        fileContent,
                        RELATION_MAPPING_NAME,
                        relationDescriptions,
                        relationManager,
                        conceptManager);
        relations.forEach(
                relation -> {
                    try {
                        relationManager.addOrAppend(relation);
                    } catch (UnrelatedMappingException e) {
                        AdocImporter.LOG.warn(e.getMessage());
                    }
                });

        freeTextQueryQueue.addAll(QueryParser.extractFreeTextQueries(fileContent));

        customQueryQueue.addAll(
                QueryParser.extractCustomQueries(fileContent, relationManager, conceptManager));
    }
}
