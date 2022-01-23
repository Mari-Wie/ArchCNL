package org.archcnl.domain.common.io;

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
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
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
            final File file, final RulesConceptsAndRelations rulesConceptsAndRelations)
            throws IOException {

        final String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        final Map<String, String> conceptDescriptions = new HashMap<String, String>();
        final Map<String, String> relationDescriptions = new HashMap<String, String>();

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.CONCEPT_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        conceptDescription -> {
                            try {
                                final String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_MAPPING_NAME,
                                                conceptDescription);
                                final String description =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_DESCRIPTION_CONTENT,
                                                conceptDescription);
                                conceptDescriptions.put(name, description);
                            } catch (final NoMatchFoundException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.RELATION_DESCRIPTION_PATTERN, fileContent)
                .stream()
                .forEach(
                        relationDescription -> {
                            try {
                                final String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.RELATION_MAPPING_NAME,
                                                relationDescription);
                                final String description =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader
                                                        .RELATION_DESCRIPTION_CONTENT,
                                                relationDescription);
                                relationDescriptions.put(name, description);
                            } catch (final NoMatchFoundException e) {
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
                            } catch (final NoArchitectureRuleException e) {
                                ArchRulesFromAdocReader.LOG.warn(e.getMessage());
                            }
                        });

        AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.CONCEPT_MAPPING_PATTERN, fileContent)
                .stream()
                .forEach(
                        potentialConceptMapping -> {
                            try {
                                final String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.CONCEPT_MAPPING_NAME,
                                                potentialConceptMapping);
                                String description = "";
                                if (conceptDescriptions.containsKey(name)) {
                                    description = conceptDescriptions.get(name);
                                }
                                final CustomConcept concept = new CustomConcept(name, description);
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
                                final String name =
                                        AdocIoUtils.getFirstMatch(
                                                ArchRulesFromAdocReader.RELATION_MAPPING_NAME,
                                                potentialRelationMapping);
                                String description = "";
                                if (relationDescriptions.containsKey(name)) {
                                    description = relationDescriptions.get(name);
                                }
                                final CustomRelation relation =
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

    private ConceptMapping parseMapping(
            final String potentialMapping, final CustomConcept thisConcept)
            throws NoMappingException {
        try {
            final String whenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.WHEN_PATTERN, potentialMapping);
            final String thenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.THEN_PATTERN, potentialMapping);
            final AndTriplets andTriplets = parseWhenPart(whenPart);
            final List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            final Triplet thenTriplet = parseThenPart(thenPart);
            return new ConceptMapping(thenTriplet.getSubject(), whenTriplets, thisConcept);
        } catch (final Exception e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private RelationMapping parseMapping(
            final String potentialMapping, final CustomRelation thisRelation)
            throws NoMappingException {
        try {
            final String whenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.WHEN_PATTERN, potentialMapping);
            final String thenPart =
                    AdocIoUtils.getFirstMatch(
                            ArchRulesFromAdocReader.THEN_PATTERN, potentialMapping);
            final AndTriplets andTriplets = parseWhenPart(whenPart);
            final List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            final Triplet thenTriplet = parseThenPart(thenPart);
            return new RelationMapping(thenTriplet, whenTriplets);
        } catch (UnsupportedObjectTypeInTriplet | NoTripletException | NoMatchFoundException e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private AndTriplets parseWhenPart(final String whenPart) throws NoTripletException {
        final AndTriplets andTriplets = new AndTriplets();
        final List<String> potentialTriplets =
                AdocIoUtils.getAllMatches(ArchRulesFromAdocReader.TRIPLET_PATTERN, whenPart);
        for (final String potentialTriplet : potentialTriplets) {
            andTriplets.addTriplet(parseTriplet(potentialTriplet, false));
        }
        return andTriplets;
    }

    private Triplet parseThenPart(final String thenPart)
            throws NoTripletException, NoMatchFoundException {
        final String potentialThenTriplet =
                AdocIoUtils.getFirstMatch(ArchRulesFromAdocReader.TRIPLET_PATTERN, thenPart);
        return parseTriplet(potentialThenTriplet, true);
    }

    private Triplet parseTriplet(final String potentialTriplet, final boolean isThenTriplet)
            throws NoTripletException {
        try {
            if (potentialTriplet.matches(
                    ArchRulesFromAdocReader.SPECIAL_TRIPLET_PATTERN.toString())) {
                return parseSpecialTriplet(potentialTriplet);
            } else {
                final String subjectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=\\(\\?)\\w+(?= )"), potentialTriplet);
                final String predicateString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<= )\\w+:\\w+(?= )"), potentialTriplet);
                final String objectString =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile(
                                        "(?<= )(\\w+:\\w+|\\?\\w+|'.+'(\\^\\^xsd:boolean)?)(?=\\))"),
                                potentialTriplet);

                final Variable subject = new Variable(subjectString);
                final ObjectType object = ObjectType.parseObject(objectString);
                final Relation predicate = Relation.parsePredicate(predicateString);
                if (isThenTriplet && predicate instanceof CustomRelation) {
                    final CustomRelation customRelation = (CustomRelation) predicate;
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

    private Triplet parseSpecialTriplet(final String potentialTriplet) throws NoTripletException {
        try {
            final String predicate =
                    AdocIoUtils.getFirstMatch(Pattern.compile("\\w+(?=\\()"), potentialTriplet);
            final String subjectString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<=\\(\\?)\\w+(?=, )"), potentialTriplet);
            final String objectString =
                    AdocIoUtils.getFirstMatch(
                            Pattern.compile("(?<= )'.+'(?=\\))"), potentialTriplet);
            return TripletFactory.createTriplet(
                    new Variable(subjectString),
                    Relation.parsePredicate(predicate),
                    ObjectType.parseObject(objectString));
        } catch (final Exception e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    @SuppressWarnings("unused")
    private ArchitectureRule parseArchitectureRule(final String potentialRule)
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
