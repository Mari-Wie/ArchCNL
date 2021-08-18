package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.webui.datatypes.RulesAndMappings;
import org.archcnl.webui.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.webui.datatypes.mappings.AndTriplets;
import org.archcnl.webui.datatypes.mappings.Concept;
import org.archcnl.webui.datatypes.mappings.Concept.ConceptType;
import org.archcnl.webui.datatypes.mappings.ConceptMapping;
import org.archcnl.webui.datatypes.mappings.Mapping;
import org.archcnl.webui.datatypes.mappings.Relation;
import org.archcnl.webui.datatypes.mappings.Relation.RelationType;
import org.archcnl.webui.datatypes.mappings.RelationMapping;
import org.archcnl.webui.datatypes.mappings.Triplet;
import org.archcnl.webui.datatypes.mappings.Variable;
import org.archcnl.webui.exceptions.NoArchitectureRuleException;
import org.archcnl.webui.exceptions.NoMappingException;
import org.archcnl.webui.exceptions.NoTripletException;

public class ArchRulesFromAdocReader implements ArchRulesImporter {

    private static final Logger LOG = LogManager.getLogger(ArchRulesFromAdocReader.class);

    private static final Pattern RULE_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");
    private static final Pattern CONCEPT_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n))is.+\\-\\> \\(.+ rdf:type .+\\)");
    private static final Pattern RELATION_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n)).+Mapping.+\\-\\> \\(.+\\)");
    private static final Pattern TRIPLET_PATTERN =
            Pattern.compile("\\(\\?\\w+ \\w+:\\w+ (\\?\\w+|\\w+:\\w+)\\)");
    private static final Pattern WHEN_PATTERN = Pattern.compile(".+(?=\\-\\>)");
    private static final Pattern THEN_PATTERN = Pattern.compile("(?<=\\-\\>).+");

    @Override
    public RulesAndMappings readArchitectureRules(
            File file, Relation typeRelation)
            throws IOException {

        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        List<ArchitectureRule> archRules = new LinkedList<>();
        List<String> potentialRules = AdocIoUtils.getAllMatches(RULE_PATTERN, fileContent);
        for (String potentialRule : potentialRules) {
            try {
                archRules.add(parseArchitectureRule(potentialRule));
            } catch (NoArchitectureRuleException e) {
                LOG.warn(e.getMessage());
            }
        }

        List<ConceptMapping> conceptMappings = new LinkedList<>();
        List<String> potentialConceptMappings =
                AdocIoUtils.getAllMatches(CONCEPT_MAPPING_PATTERN, fileContent);
        for (String potentialConceptMapping : potentialConceptMappings) {
            try {
                conceptMappings.add(
                        (ConceptMapping)
                                parseMapping(
                                        potentialConceptMapping, typeRelation));
            } catch (NoMappingException e) {
                LOG.warn(e.getMessage());
            }
        }

        List<RelationMapping> relationMappings = new LinkedList<>();
        List<String> potentialRelationMappings =
                AdocIoUtils.getAllMatches(RELATION_MAPPING_PATTERN, fileContent);
        for (String potentialRelationMapping : potentialRelationMappings) {
            try {
                relationMappings.add(
                        (RelationMapping)
                                parseMapping(
                                        potentialRelationMapping, typeRelation));
            } catch (NoMappingException e) {
                LOG.warn(e.getMessage());
            }
        }
        // TODO combine mappings with same name

        return new RulesAndMappings(archRules, conceptMappings, relationMappings);
    }

    private Mapping parseMapping(
            String potentialMapping, Relation typeRelation)
            throws NoMappingException {
        try {
            String whenPart = AdocIoUtils.getFirstMatch(WHEN_PATTERN, potentialMapping);
            String thenPart = AdocIoUtils.getFirstMatch(THEN_PATTERN, potentialMapping);

            AndTriplets andTriplets = new AndTriplets();
            List<String> potentialTriplets = AdocIoUtils.getAllMatches(TRIPLET_PATTERN, whenPart);
            for (String potentialTriplet : potentialTriplets) {
                andTriplets.addTriplet(parseTriplet(potentialTriplet));
            }

            String potentialThenTriplet = AdocIoUtils.getFirstMatch(TRIPLET_PATTERN, thenPart);
            Triplet thenTriplet = parseTriplet(potentialThenTriplet);

            List<AndTriplets> whenStatements = new LinkedList<>();
            whenStatements.add(andTriplets);
            if (potentialMapping.startsWith("is")) {
                return new ConceptMapping(
                        thenTriplet.getConceptObject().getName(),
                        thenTriplet.getSubject(),
                        whenStatements,
                        typeRelation);
            } else {
                return new RelationMapping(
                        thenTriplet.getPredicate().getName(),
                        thenTriplet.getSubject(),
                        thenTriplet.getVariableObject(),
                        whenStatements);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new NoMappingException(potentialMapping);
        }
    }

    private Triplet parseTriplet(String potentialTriplet) throws NoTripletException {
        try {
            String subjectString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<=\\(\\?)\\w+(?= )"), potentialTriplet);
            String predicateString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<= )\\w+:\\w+(?= )"), potentialTriplet);
            String predicateName =
                    AdocIoUtils.getFirstMatch(Pattern.compile("(?<=\\w+:).+"), predicateString);
            String predicateTypeString =
                    AdocIoUtils.getFirstMatch(Pattern.compile(".+(?=:.+)"), predicateString);
            String objectString =
                    AdocIoUtils.getFirstMatch(Pattern.compile("(?<= )(\\w+:\\w+|\\?\\w+|'.+')(?=\\))"), potentialTriplet);
            

            Variable subject = new Variable(subjectString);
            RelationType predicateType = RelationType.valueOf(predicateTypeString);
            Relation predicate = new Relation(predicateName, predicateType);

            if (objectString.matches("'.+'")) {
                return new Triplet(
                        subject, predicate, objectString.substring(1, objectString.length() - 1));
            } else if (objectString.startsWith("?")) {
                return new Triplet(subject, predicate, new Variable(objectString.substring(1)));
            } else {
                String objectName =
                        AdocIoUtils.getFirstMatch(Pattern.compile("(?<=\\w+:).+"), objectString);
                String objectTypeString =
                        AdocIoUtils.getFirstMatch(Pattern.compile(".+(?=:.+)"), objectString);
                ConceptType conceptType = ConceptType.valueOf(objectTypeString);
                Concept object = new Concept(objectName, conceptType);
                return new Triplet(subject, predicate, object);
            }
        } catch (Exception e) {
            throw new NoTripletException(potentialTriplet);
        }
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
