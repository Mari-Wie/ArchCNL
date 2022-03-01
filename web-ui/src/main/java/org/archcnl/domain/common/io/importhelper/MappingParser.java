package org.archcnl.domain.common.io.importhelper;

import com.google.common.annotations.VisibleForTesting;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.common.io.exceptions.NoMatchFoundException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoMappingException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoObjectTypeException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoRelationException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoTripletException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class MappingParser {

    private static final Logger LOG = LogManager.getLogger(MappingParser.class);

    private static final Pattern CONCEPT_MAPPING_PATTERN =
            Pattern.compile(
                    "(?<=\\[role=\"mapping\"\\](\r\n?|\n))is\\w+\\: (.+ )?\\-\\> \\(.+ rdf:type .+\\)");
    private static final Pattern RELATION_MAPPING_PATTERN =
            Pattern.compile(
                    "(?<=\\[role=\"mapping\"\\](\r\n?|\n))\\w+Mapping\\: (.+ )?\\-\\> \\(.+\\)");

    private static final Pattern WHEN_PATTERN = Pattern.compile(".+(?=\\-\\>)");
    private static final Pattern THEN_PATTERN = Pattern.compile("(?<=\\-\\>).+");

    private static final Pattern NORMAL_TRIPLET_PATTERN =
            Pattern.compile(
                    "\\(\\?\\w+ \\w+:\\w+ (\\?\\w+|\\w+:\\w+|'.*'|'(false|true)'\\^\\^xsd\\:boolean)\\)");
    private static final Pattern JENA_BUILTIN_TRIPLET_PATTERN =
            Pattern.compile("\\w+\\(\\?\\w+, '.+'\\)");
    private static final Pattern TRIPLET_PATTERN =
            Pattern.compile(
                    MappingParser.NORMAL_TRIPLET_PATTERN
                            + "|"
                            + MappingParser.JENA_BUILTIN_TRIPLET_PATTERN);

    private MappingParser() {}

    public static List<CustomConcept> extractCustomConcepts(
            String fileContent,
            Pattern conceptMappingName,
            Map<String, String> conceptDescriptions,
            final RelationManager relationManager,
            final ConceptManager conceptManager) {
        List<CustomConcept> concepts = new LinkedList<>();

        RegexUtils.getAllMatches(MappingParser.CONCEPT_MAPPING_PATTERN, fileContent).stream()
                .forEach(
                        potentialConceptMapping -> {
                            try {
                                final String name =
                                        RegexUtils.getFirstMatch(
                                                conceptMappingName, potentialConceptMapping);
                                String description = "";
                                if (conceptDescriptions.containsKey(name)) {
                                    description = conceptDescriptions.get(name);
                                }
                                final CustomConcept concept = new CustomConcept(name, description);
                                concept.setMapping(
                                        parseMapping(
                                                potentialConceptMapping,
                                                concept,
                                                relationManager,
                                                conceptManager));
                                concepts.add(concept);
                            } catch (UnrelatedMappingException
                                    | NoMappingException
                                    | NoMatchFoundException e) {
                                MappingParser.LOG.warn(e.getMessage());
                            }
                        });

        return concepts;
    }

    public static List<CustomRelation> extractCustomRelations(
            String fileContent,
            Pattern relationMappingName,
            Map<String, String> relationDescriptions,
            final RelationManager relationManager,
            final ConceptManager conceptManager) {
        List<CustomRelation> relations = new LinkedList<>();

        RegexUtils.getAllMatches(MappingParser.RELATION_MAPPING_PATTERN, fileContent).stream()
                .forEach(
                        potentialRelationMapping -> {
                            try {
                                final String name =
                                        RegexUtils.getFirstMatch(
                                                relationMappingName, potentialRelationMapping);
                                String description = "";
                                if (relationDescriptions.containsKey(name)) {
                                    description = relationDescriptions.get(name);
                                }
                                final CustomRelation relation =
                                        new CustomRelation(name, description, new LinkedList<>());
                                relation.setMapping(
                                        parseMapping(
                                                potentialRelationMapping,
                                                relationManager,
                                                conceptManager));
                                relations.add(relation);
                            } catch (UnrelatedMappingException
                                    | NoMappingException
                                    | NoMatchFoundException e) {
                                MappingParser.LOG.warn(e.getMessage());
                            }
                        });

        return relations;
    }

    private static ConceptMapping parseMapping(
            final String potentialMapping,
            final CustomConcept thisConcept,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoMappingException {
        try {
            final String whenPart =
                    RegexUtils.getFirstMatch(MappingParser.WHEN_PATTERN, potentialMapping);
            final String thenPart =
                    RegexUtils.getFirstMatch(MappingParser.THEN_PATTERN, potentialMapping);
            final AndTriplets andTriplets =
                    parseWhenPart(whenPart, relationManager, conceptManager);
            final List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            final Triplet thenTriplet = parseThenPart(thenPart, relationManager, conceptManager);
            return new ConceptMapping(thenTriplet.getSubject(), whenTriplets, thisConcept);
        } catch (final NoTripletException | NoMatchFoundException e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private static RelationMapping parseMapping(
            final String potentialMapping,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoMappingException {
        try {
            final String whenPart =
                    RegexUtils.getFirstMatch(MappingParser.WHEN_PATTERN, potentialMapping);
            final String thenPart =
                    RegexUtils.getFirstMatch(MappingParser.THEN_PATTERN, potentialMapping);
            final AndTriplets andTriplets =
                    parseWhenPart(whenPart, relationManager, conceptManager);
            final List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            final Triplet thenTriplet = parseThenPart(thenPart, relationManager, conceptManager);
            return new RelationMapping(thenTriplet, whenTriplets);
        } catch (NoTripletException | NoMatchFoundException e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    protected static AndTriplets parseWhenPart(
            final String whenPart,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoTripletException {
        final AndTriplets andTriplets = new AndTriplets();
        final List<String> potentialTriplets =
                RegexUtils.getAllMatches(MappingParser.TRIPLET_PATTERN, whenPart);
        for (final String potentialTriplet : potentialTriplets) {
            andTriplets.addTriplet(
                    parseTriplet(potentialTriplet, false, relationManager, conceptManager));
        }
        return andTriplets;
    }

    private static Triplet parseThenPart(
            final String thenPart,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoTripletException, NoMatchFoundException {
        final String potentialThenTriplet =
                RegexUtils.getFirstMatch(MappingParser.TRIPLET_PATTERN, thenPart);
        return parseTriplet(potentialThenTriplet, true, relationManager, conceptManager);
    }

    private static Triplet parseTriplet(
            final String potentialTriplet,
            final boolean isThenTriplet,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoTripletException {
        try {
            if (potentialTriplet.matches(MappingParser.JENA_BUILTIN_TRIPLET_PATTERN.toString())) {
                return parseJenaBuiltinTriplet(potentialTriplet, relationManager, conceptManager);
            } else {
                final String subjectString =
                        RegexUtils.getFirstMatch(
                                Pattern.compile("(?<=\\(\\?)\\w+(?= )"), potentialTriplet);
                final String predicateString =
                        RegexUtils.getFirstMatch(
                                Pattern.compile("(?<= )\\w+:\\w+(?= )"), potentialTriplet);
                final String objectString =
                        RegexUtils.getFirstMatch(
                                Pattern.compile(
                                        "(?<= )(\\w+:\\w+|\\?\\w+|'.+'(\\^\\^xsd:boolean)?)(?=\\))"),
                                potentialTriplet);

                final Variable subject = new Variable(subjectString);
                final ObjectType object = ObjectParser.parseObject(objectString, conceptManager);
                final Relation predicate =
                        PredicateParser.parsePredicate(predicateString, relationManager);
                if (isThenTriplet && predicate instanceof CustomRelation) {
                    final CustomRelation customRelation = (CustomRelation) predicate;
                    customRelation.setRelatableObjectType(object);
                }
                return TripletFactory.createTriplet(subject, predicate, object);
            }
        } catch (NoRelationException
                | UnsupportedObjectTypeException
                | NoObjectTypeException
                | NoMatchFoundException e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    private static Triplet parseJenaBuiltinTriplet(
            final String potentialTriplet,
            final RelationManager relationManager,
            final ConceptManager conceptManager)
            throws NoTripletException {
        try {
            final String predicate =
                    RegexUtils.getFirstMatch(Pattern.compile("\\w+(?=\\()"), potentialTriplet);
            final String subjectString =
                    RegexUtils.getFirstMatch(
                            Pattern.compile("(?<=\\(\\?)\\w+(?=, )"), potentialTriplet);
            final String objectString =
                    RegexUtils.getFirstMatch(
                            Pattern.compile("(?<= )'.+'(?=\\))"), potentialTriplet);
            return TripletFactory.createTriplet(
                    new Variable(subjectString),
                    PredicateParser.parsePredicate(predicate, relationManager),
                    ObjectParser.parseObject(objectString, conceptManager));
        } catch (final UnsupportedObjectTypeException
                | NoRelationException
                | NoObjectTypeException
                | NoMatchFoundException e) {
            throw new NoTripletException(potentialTriplet);
        }
    }

    @VisibleForTesting
    public static Pattern getConceptMappingPattern() {
        return CONCEPT_MAPPING_PATTERN;
    }

    @VisibleForTesting
    public static Pattern getRelationMappingPattern() {
        return RELATION_MAPPING_PATTERN;
    }
}
