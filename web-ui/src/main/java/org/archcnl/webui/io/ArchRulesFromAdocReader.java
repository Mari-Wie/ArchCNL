package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.webui.datatypes.RulesAndMappings;
import org.archcnl.webui.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.webui.datatypes.mappings.ConceptMapping;
import org.archcnl.webui.datatypes.mappings.Mapping;
import org.archcnl.webui.datatypes.mappings.RelationMapping;
import org.archcnl.webui.exceptions.NoArchitectureRuleException;

public class ArchRulesFromAdocReader implements ArchRulesImporter {

    private static final Logger LOG = LogManager.getLogger(ArchRulesFromAdocReader.class);

    private static final Pattern RULE_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");
    private static final Pattern CONCEPT_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n))is.+\\-\\> \\(.+ rdf:type .+\\)");
    private static final Pattern RELATION_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n)).+Mapping.+\\-\\> \\(.+\\)");

    @Override
    public RulesAndMappings readArchitectureRules(File file) throws IOException {
        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        List<ArchitectureRule> archRules = new LinkedList<>();
        Matcher matcher = RULE_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            String potentialRule = matcher.group();
            try {
                archRules.add(parseArchitectureRule(potentialRule));
            } catch (NoArchitectureRuleException e) {
                LOG.warn(e.getMessage());
            }
        }

        List<ConceptMapping> conceptMappings = new LinkedList<>();
        matcher = CONCEPT_MAPPING_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            String potentialConceptMapping = matcher.group();
            conceptMappings.add((ConceptMapping) parseMapping(potentialConceptMapping));
        }

        List<RelationMapping> relationMappings = new LinkedList<>();
        matcher = RELATION_MAPPING_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            String potentialRelationMapping = matcher.group();
            relationMappings.add((RelationMapping) parseMapping(potentialRelationMapping));
        }

        return new RulesAndMappings(archRules, conceptMappings, relationMappings);
    }

    private Mapping parseMapping(String potentialConceptMapping) {
        // TODO Auto-generated method stub
        return null;
    }

    private ArchitectureRule parseArchitectureRule(String potentialRule)
            throws NoArchitectureRuleException {
        if (false) {
            // TODO implement actual parsing once data model for rules exists
            throw new NoArchitectureRuleException(potentialRule);
        }
        return new ArchitectureRule(potentialRule);
    }

    public static Pattern getRulePattern() {
        return RULE_PATTERN;
    }

    public static Pattern getConceptMappingPattern() {
        return CONCEPT_MAPPING_PATTERN;
    }

    public static Pattern getRelationMappingPattern() {
        return RELATION_MAPPING_PATTERN;
    }
}
