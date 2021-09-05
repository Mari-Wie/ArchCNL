package org.archcnl.domain.input.io;

import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.datatypes.mappings.AndTriplets;
import org.archcnl.domain.input.datatypes.mappings.ConceptMapping;
import org.archcnl.domain.input.datatypes.mappings.CustomConcept;
import org.archcnl.domain.input.datatypes.mappings.CustomRelation;
import org.archcnl.domain.input.datatypes.mappings.ObjectType;
import org.archcnl.domain.input.datatypes.mappings.Relation;
import org.archcnl.domain.input.datatypes.mappings.RelationMapping;
import org.archcnl.domain.input.datatypes.mappings.Triplet;
import org.archcnl.domain.input.datatypes.mappings.Variable;
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
    private static final Pattern CONCEPT_MAPPING_NAME = Pattern.compile("(?<=is)\\w+(?=:)");
    private static final Pattern RELATION_MAPPING_NAME = Pattern.compile(".+(?=Mapping:)");

    @Override
    public void readArchitectureRules(
            File file, RulesConceptsAndRelations rulesConceptsAndRelations) throws IOException {

        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        // Extract and add architecture rules
        List<ArchitectureRule> archRules = new LinkedList<>();
        List<String> potentialRules = AdocIoUtils.getAllMatches(RULE_PATTERN, fileContent);
        for (String potentialRule : potentialRules) {
            try {
                archRules.add(parseArchitectureRule(potentialRule));
            } catch (NoArchitectureRuleException e) {
                LOG.warn(e.getMessage());
            }
        }
        rulesConceptsAndRelations.getArchitectureRuleManager().addAllArchitectureRules(archRules);

        List<String> potentialConceptMappings =
                AdocIoUtils.getAllMatches(CONCEPT_MAPPING_PATTERN, fileContent);
        for (String potentialConceptMapping : potentialConceptMappings) {
            try {
                String name =
                        AdocIoUtils.getFirstMatch(CONCEPT_MAPPING_NAME, potentialConceptMapping);
                CustomConcept concept = new CustomConcept(name);
                concept.setMapping(parseMapping(potentialConceptMapping, concept));
                rulesConceptsAndRelations.getConceptManager().addOrAppend(concept);
            } catch (Exception e) {
                LOG.warn(e.getMessage());
            }
        }

        List<String> potentialRelationMappings =
                AdocIoUtils.getAllMatches(RELATION_MAPPING_PATTERN, fileContent);
        for (String potentialRelationMapping : potentialRelationMappings) {
            try {
                String name =
                        AdocIoUtils.getFirstMatch(RELATION_MAPPING_NAME, potentialRelationMapping);
                CustomRelation relation = new CustomRelation(name);
                relation.setMapping(parseMapping(potentialRelationMapping, relation));
                rulesConceptsAndRelations.getRelationManager().addOrAppend(relation);
            } catch (Exception e) {
                LOG.warn(e.getMessage());
            }
        }
    }

    private ConceptMapping parseMapping(String potentialMapping, CustomConcept thisConcept)
            throws NoMappingException {
        try {
            String whenPart = AdocIoUtils.getFirstMatch(WHEN_PATTERN, potentialMapping);
            String thenPart = AdocIoUtils.getFirstMatch(THEN_PATTERN, potentialMapping);
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
            String whenPart = AdocIoUtils.getFirstMatch(WHEN_PATTERN, potentialMapping);
            String thenPart = AdocIoUtils.getFirstMatch(THEN_PATTERN, potentialMapping);
            AndTriplets andTriplets = parseWhenPart(whenPart);
            List<AndTriplets> whenTriplets = new LinkedList<>();
            whenTriplets.add(andTriplets);
            Triplet thenTriplet = parseThenPart(thenPart);
            return new RelationMapping(
                    thenTriplet.getSubject(), thenTriplet.getObject(), whenTriplets, thisRelation);
        } catch (Exception e) {
            throw new NoMappingException(potentialMapping);
        }
    }

    private AndTriplets parseWhenPart(String whenPart) throws NoTripletException {
        AndTriplets andTriplets = new AndTriplets();
        List<String> potentialTriplets = AdocIoUtils.getAllMatches(TRIPLET_PATTERN, whenPart);
        for (String potentialTriplet : potentialTriplets) {
            andTriplets.addTriplet(parseTriplet(potentialTriplet));
        }
        return andTriplets;
    }

    private Triplet parseThenPart(String thenPart) throws NoTripletException {
        String potentialThenTriplet = AdocIoUtils.getFirstMatch(TRIPLET_PATTERN, thenPart);
        return parseTriplet(potentialThenTriplet);
    }

    private Triplet parseTriplet(String potentialTriplet) throws NoTripletException {
        try {
            if (potentialTriplet.matches(SPECIAL_TRIPLET_PATTERN.toString())) {
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
                return new Triplet(subject, predicate, object);
            }
        } catch (Exception e) {
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
            return new Triplet(
                    new Variable(subjectString),
                    Relation.parsePredicate(predicate),
                    ObjectType.parseObject(objectString));
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

    @VisibleForTesting
    public static Pattern getRulePattern() {
        return RULE_PATTERN;
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
