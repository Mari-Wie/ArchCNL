package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.input.datatypes.RulesAndMappings;
import org.archcnl.domain.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.datatypes.mappings.AndTriplets;
import org.archcnl.domain.input.datatypes.mappings.Concept;
import org.archcnl.domain.input.datatypes.mappings.ConceptMapping;
import org.archcnl.domain.input.datatypes.mappings.Mapping;
import org.archcnl.domain.input.datatypes.mappings.Relation;
import org.archcnl.domain.input.datatypes.mappings.RelationMapping;
import org.archcnl.domain.input.datatypes.mappings.Triplet;
import org.archcnl.domain.input.datatypes.mappings.Variable;
import org.archcnl.domain.input.datatypes.mappings.Concept.ConceptType;
import org.archcnl.domain.input.datatypes.mappings.Relation.RelationType;
import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.exceptions.NoMappingException;
import org.archcnl.domain.input.exceptions.NoTripletException;

public class ArchRulesFromAdocReader implements ArchRulesImporter {

    private static final Logger LOG = LogManager.getLogger(ArchRulesFromAdocReader.class);

    private static final Pattern RULE_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");
    private static final Pattern CONCEPT_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n))is.+\\-\\> \\(.+ rdf:type .+\\)");
    private static final Pattern RELATION_MAPPING_PATTERN =
            Pattern.compile("(?<=\\[role=\"mapping\"\\](\r\n?|\n)).+Mapping.+\\-\\> \\(.+\\)");
    private static final Pattern NORMAL_TRIPLET_PATTERN =
            Pattern.compile("\\(\\?\\w+ \\w+:\\w+ (\\?\\w+|\\w+:\\w+)\\)");
    private static final Pattern SPECIAL_TRIPLET_PATTERN =
            Pattern.compile("\\w+\\(\\?\\w+, '.+'\\)");
    private static final Pattern TRIPLET_PATTERN =
            Pattern.compile(NORMAL_TRIPLET_PATTERN + "|" + SPECIAL_TRIPLET_PATTERN);
    private static final Pattern WHEN_PATTERN = Pattern.compile(".+(?=\\-\\>)");
    private static final Pattern THEN_PATTERN = Pattern.compile("(?<=\\-\\>).+");

    @Override
    public RulesAndMappings readArchitectureRules(
            File file, Relation typeRelation, Relation matchesRelation) throws IOException {

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
                                        potentialConceptMapping, typeRelation, matchesRelation));
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
                                        potentialRelationMapping, typeRelation, matchesRelation));
            } catch (NoMappingException e) {
                LOG.warn(e.getMessage());
            }
        }

        // combine mappings with the same name
        List<String> conceptMappingNames = new LinkedList<>();
        List<ConceptMapping> conceptMappingsWithouDuplicates = new LinkedList<>();
        for (ConceptMapping conceptMapping : conceptMappings) {
            if (conceptMappingNames.contains(conceptMapping.getName())) {
                for (ConceptMapping mapping : conceptMappingsWithouDuplicates) {
                    if (mapping.getName().equals(conceptMapping.getName())) {
                        mapping.addAllAndTriplets(conceptMapping.getWhenTriplets());
                    }
                }
            } else {
                conceptMappingNames.add(conceptMapping.getName());
                conceptMappingsWithouDuplicates.add(conceptMapping);
            }
        }
        List<String> relationMappingNames = new LinkedList<>();
        List<RelationMapping> relationMappingsWithouDuplicates = new LinkedList<>();
        for (RelationMapping relationMapping : relationMappings) {
            if (relationMappingNames.contains(relationMapping.getName())) {
                for (RelationMapping mapping : relationMappingsWithouDuplicates) {
                    if (mapping.getName().equals(relationMapping.getName())) {
                        mapping.addAllAndTriplets(relationMapping.getWhenTriplets());
                    }
                }
            } else {
                relationMappingNames.add(relationMapping.getName());
                relationMappingsWithouDuplicates.add(relationMapping);
            }
        }

        return new RulesAndMappings(
                archRules, conceptMappingsWithouDuplicates, relationMappingsWithouDuplicates);
    }

    private Mapping parseMapping(
            String potentialMapping, Relation typeRelation, Relation matchesRelation)
            throws NoMappingException {
        try {
            String whenPart = AdocIoUtils.getFirstMatch(WHEN_PATTERN, potentialMapping);
            String thenPart = AdocIoUtils.getFirstMatch(THEN_PATTERN, potentialMapping);

            AndTriplets andTriplets = new AndTriplets();
            List<String> potentialTriplets = AdocIoUtils.getAllMatches(TRIPLET_PATTERN, whenPart);
            for (String potentialTriplet : potentialTriplets) {
                andTriplets.addTriplet(parseTriplet(potentialTriplet, matchesRelation));
            }

            String potentialThenTriplet = AdocIoUtils.getFirstMatch(TRIPLET_PATTERN, thenPart);
            Triplet thenTriplet = parseTriplet(potentialThenTriplet, matchesRelation);

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

    private Triplet parseTriplet(String potentialTriplet, Relation matchesRelation)
            throws NoTripletException {
        try {
            if (potentialTriplet.matches(SPECIAL_TRIPLET_PATTERN.toString())) {
                return parseSpecialTriplet(potentialTriplet, matchesRelation);
            } else {
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
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<= )(\\w+:\\w+|\\?\\w+|'.+')(?=\\))"),
                                potentialTriplet);

                Variable subject = new Variable(subjectString);
                RelationType predicateType = RelationType.valueOf(predicateTypeString);
                Relation predicate = new Relation(predicateName, predicateType);

                if (objectString.matches("'.+'")) {
                    return new Triplet(
                            subject,
                            predicate,
                            objectString.substring(1, objectString.length() - 1));
                } else if (objectString.startsWith("?")) {
                    return new Triplet(subject, predicate, new Variable(objectString.substring(1)));
                } else {
                    String objectName =
                            AdocIoUtils.getFirstMatch(
                                    Pattern.compile("(?<=\\w+:).+"), objectString);
                    String objectTypeString =
                            AdocIoUtils.getFirstMatch(Pattern.compile(".+(?=:.+)"), objectString);
                    ConceptType conceptType = ConceptType.valueOf(objectTypeString);
                    Concept object = new Concept(objectName, conceptType);
                    return new Triplet(subject, predicate, object);
                }
            }
        } catch (Exception e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    private Triplet parseSpecialTriplet(String potentialTriplet, Relation matchesRelation)
            throws NoTripletException {
        try {
            if (potentialTriplet.startsWith("regex")) {
                String subjectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=\\(\\?)\\w+(?=, )"), potentialTriplet);
                String objectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<= )'.+'(?=\\))"), potentialTriplet);
                Variable subject = new Variable(subjectString);
                return new Triplet(
                        subject,
                        matchesRelation,
                        objectString.substring(1, objectString.length() - 1));
            } else {
                throw new NoTripletException(potentialTriplet);
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
