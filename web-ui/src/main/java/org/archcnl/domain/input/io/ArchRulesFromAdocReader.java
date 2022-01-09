package org.archcnl.domain.input.io;

import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.exceptions.NoMappingException;
import org.archcnl.domain.input.exceptions.NoMatchFoundException;
import org.archcnl.domain.input.exceptions.NoObjectTypeException;
import org.archcnl.domain.input.exceptions.NoRelationException;
import org.archcnl.domain.input.exceptions.NoTripletException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class ArchRulesFromAdocReader implements ArchRulesImporter {

    private static final Logger LOG = LogManager.getLogger(ArchRulesFromAdocReader.class);

    private static final Pattern CONCEPT_MAPPING_NAME = Pattern.compile("(?<=is)\\w+(?=:)");
    private static final Pattern RELATION_MAPPING_NAME = Pattern.compile(".+(?=Mapping:)");
    private static final Pattern CONCEPT_DESCRIPTION_PATTERN =
            Pattern.compile("\\[role=\"description\"\\](\r\n?|\n)(is\\w+:)[\\w\\. ]+(\r\n?|\n)");
    private static final Pattern CONCEPT_DESCRIPTION_CONTENT =
            Pattern.compile(
                    "(?<=\\[role=\"description\"\\](\r\n?|\n)(is\\w+: ))[\\w\\. ]+((?=\r\n?|\n))");
    private static final Pattern RELATION_DESCRIPTION_PATTERN =
            Pattern.compile("\\[role=\"description\"\\](\r\n?|\n)(.+Mapping:)[\\w\\. ]+(\r\n?|\n)");
    private static final Pattern RELATION_DESCRIPTION_CONTENT =
            Pattern.compile(
                    "(?<=\\[role=\"description\"\\](\r\n?|\n)(.+Mapping: ))[\\w\\. ]+((?=\r\n?|\n))");
    private static final Pattern RULE_CONTENT_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");
    private static final Pattern CONCEPT_MAPPING_PATTERN =
            Pattern.compile(
                    "(?<=\\[role=\"mapping\"\\](\r\n?|\n))is\\w+\\: (.+ )?\\-\\> \\(.+ rdf:type .+\\)");
    private static final Pattern RELATION_MAPPING_PATTERN =
            Pattern.compile(
                    "(?<=\\[role=\"mapping\"\\](\r\n?|\n))\\w+Mapping\\: (.+ )?\\-\\> \\(.+\\)");
    private static final Pattern NORMAL_TRIPLET_PATTERN =
            Pattern.compile(
                    "\\(\\?\\w+ \\w+:\\w+ (\\?\\w+|\\w+:\\w+|'.*'|'(false|true)'\\^\\^xsd\\:boolean)\\)");
    private static final Pattern SPECIAL_TRIPLET_PATTERN =
            Pattern.compile("\\w+\\(\\?\\w+, '.+'\\)");
    private static final Pattern TRIPLET_PATTERN =
            Pattern.compile(
                    ArchRulesFromAdocReader.NORMAL_TRIPLET_PATTERN
                            + "|"
                            + ArchRulesFromAdocReader.SPECIAL_TRIPLET_PATTERN);
    private static final Pattern WHEN_PATTERN = Pattern.compile(".+(?=\\-\\>)");
    private static final Pattern THEN_PATTERN = Pattern.compile("(?<=\\-\\>).+");

    @Override
    public void readArchitectureRules(
            File file, RulesConceptsAndRelations rulesConceptsAndRelations) throws IOException {

        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        Map<String, String> conceptDescriptions = new HashMap<String, String>();
        Map<String, String> relationDescriptions = new HashMap<String, String>();

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.CONCEPT_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        conceptDescription -> {
                            try {
                                String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_MAPPING_NAME,
                                                conceptDescription);
                                String description =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_DESCRIPTION_CONTENT,
                                                conceptDescription);
                                conceptDescriptions.put(name, description);
                            } catch (NoMatchFoundException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.RELATION_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        relationDescription -> {
                            try {
                                String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.RELATION_MAPPING_NAME,
                                                relationDescription);
                                String description =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader
                                                        .RELATION_DESCRIPTION_CONTENT,
                                                relationDescription);
                                relationDescriptions.put(name, description);
                            } catch (NoMatchFoundException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        // Extract and add architecture rules
        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.RULE_CONTENT_PATTERN, fileContent)
                .stream()
                .forEach(
                        potentialRule -> {
                            try {
                                rulesConceptsAndRelations
                                        .getArchitectureRuleManager()
                                        .addArchitectureRule(parseArchitectureRule(potentialRule));
                            } catch (NoArchitectureRuleException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.CONCEPT_MAPPING_PATTERN, fileContent)
                .stream()
                .forEach(
                        potentialConceptMapping -> {
                            try {
                                String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_MAPPING_NAME,
                                                potentialConceptMapping);
                                String description = "";
                                if (conceptDescriptions.containsKey(name)) {
                                    description = conceptDescriptions.get(name);
                                }
                                CustomConcept concept = new CustomConcept(name, description);
                                concept.setMapping(parseMapping(potentialConceptMapping, concept));
                                rulesConceptsAndRelations.getConceptManager().addOrAppend(concept);
                            } catch (UnrelatedMappingException
                                    | NoMappingException
                                    | NoMatchFoundException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.RELATION_MAPPING_PATTERN, fileContent)
                .stream()
                .forEach(
                        potentialRelationMapping -> {
                            try {
                                String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.RELATION_MAPPING_NAME,
                                                potentialRelationMapping);
                                String description = "";
                                if (relationDescriptions.containsKey(name)) {
                                    description = relationDescriptions.get(name);
                                }
                                CustomRelation relation =
                                        new CustomRelation(name, description, new LinkedList<>());
                                relation.setMapping(
                                        parseMapping(potentialRelationMapping, relation));
                                rulesConceptsAndRelations
                                        .getRelationManager()
                                        .addOrAppend(relation);
                            } catch (UnrelatedMappingException
                                    | NoMappingException
                                    | NoMatchFoundException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });
    }

    private ConceptMapping parseMapping(String potentialMapping, CustomConcept thisConcept)
            throws NoMappingException {
        try {
            String whenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.WHEN_PATTERN, potentialMapping);
            String thenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.THEN_PATTERN, potentialMapping);
            AndTriplets andTriplets = parseWhenPart(whenPart);
            List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            Triplet thenTriplet = parseThenPart(thenPart);
            return new ConceptMapping(thenTriplet.getSubject(), whenTriplets, thisConcept);
        } catch (Exception e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private RelationMapping parseMapping(String potentialMapping, CustomRelation thisRelation)
            throws NoMappingException {
        try {
            String whenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.WHEN_PATTERN, potentialMapping);
            String thenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.THEN_PATTERN, potentialMapping);
            AndTriplets andTriplets = parseWhenPart(whenPart);
            List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            Triplet thenTriplet = parseThenPart(thenPart);
            return new RelationMapping(thenTriplet, whenTriplets);
        } catch (UnsupportedObjectTypeInTriplet | NoTripletException | NoMatchFoundException e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private AndTriplets parseWhenPart(String whenPart) throws NoTripletException {
        AndTriplets andTriplets = new AndTriplets();
        List<String> potentialTriplets =
                AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.TRIPLET_PATTERN, whenPart);
        for (String potentialTriplet : potentialTriplets) {
            andTriplets.addTriplet(parseTriplet(potentialTriplet, false));
        }
        return andTriplets;
    }

    private Triplet parseThenPart(String thenPart)
            throws NoTripletException, NoMatchFoundException {
        String potentialThenTriplet =
                AdocIoUtils.getFirstMatch(ArchRulesFromAdocReader.TRIPLET_PATTERN, thenPart);
        return parseTriplet(potentialThenTriplet, true);
    }

    private Triplet parseTriplet(String potentialTriplet, boolean isThenTriplet)
            throws NoTripletException {
        try {
            if (potentialTriplet.matches(
                    ArchRulesFromAdocReader.SPECIAL_TRIPLET_PATTERN.toString())) {
                return parseSpecialTriplet(potentialTriplet);
            } else {
                String subjectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=\\(\\?)\\w+(?= )"), potentialTriplet);
                String predicateString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<= )\\w+:\\w+(?= )"), potentialTriplet);
                String objectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile(
                                        "(?<= )(\\w+:\\w+|\\?\\w+|'.+'(\\^\\^xsd:boolean)?)(?=\\))"),
                                potentialTriplet);

                Variable subject = new Variable(subjectString);
                ObjectType object = ObjectType.parseObject(objectString);
                Relation predicate = Relation.parsePredicate(predicateString);
                if (isThenTriplet && predicate instanceof CustomRelation) {
                    CustomRelation customRelation = (CustomRelation) predicate;
                    customRelation.setRelatableObjectType(object);
                }
                return TripletFactory.createTriplet(subject, predicate, object);
            }
        } catch (NoRelationException
                | UnsupportedObjectTypeInTriplet
                | NoObjectTypeException
                | InvalidVariableNameException
                | NoMatchFoundException e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    private Triplet parseSpecialTriplet(String potentialTriplet) throws NoTripletException {
        try {
            String predicate =
                    AdocIoUtils.getFirstMatch(Pattern.compile("\\w+(?=\\()"), potentialTriplet);
            String subjectString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<=\\(\\?)\\w+(?=, )"), potentialTriplet);
            String objectString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<= )'.+'(?=\\))"), potentialTriplet);
            return TripletFactory.createTriplet(
                    new Variable(subjectString),
                    Relation.parsePredicate(predicate),
                    ObjectType.parseObject(objectString));
        } catch (Exception e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    @SuppressWarnings("unused")
    private ArchitectureRule parseArchitectureRule(String potentialRule)
            throws NoArchitectureRuleException {
        if (false) {
            // TODO implement actual parsing once data model for rules exists
            throw new NoArchitectureRuleException(potentialRule);
        }
        return new ArchitectureRule(potentialRule);
    }

    @VisibleForTesting
    public static Pattern getRulePattern() {
        return ArchRulesFromAdocReader.RULE_CONTENT_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getConceptMappingPattern() {
        return ArchRulesFromAdocReader.CONCEPT_MAPPING_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getRelationMappingPattern() {
        return ArchRulesFromAdocReader.RELATION_MAPPING_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getConceptDescriptionPattern() {
        return ArchRulesFromAdocReader.CONCEPT_DESCRIPTION_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getRelationDescriptionPattern() {
        return ArchRulesFromAdocReader.RELATION_DESCRIPTION_PATTERN;
    }
}
