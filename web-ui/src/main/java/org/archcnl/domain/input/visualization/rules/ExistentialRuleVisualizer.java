package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;

public class ExistentialRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every "
                            + SUBJECT_REGEX
                            + " must "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public ExistentialRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
        parseRule(rule.toString());
        applyRule();
    }

    private void applyRule() {}

    private void parseRule(String rule) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(rule);
        tryToFindMatch(matcher);
        subjectTriplets = parseConceptExpression(matcher.group("subject"), new Variable("subject"));
        objectTriplets = parseConceptExpression(matcher.group("object"), new Variable("object"));
        predicateTriplets = parsePredicate(matcher.group("predicate"));
    }

    private List<Triplet> parsePredicate(String group)
            throws MappingToUmlTranslationFailedException {
        String relationName = group.split(" ")[0];
        // TODO handle cardinality modifiers
        Relation relation = getRelation(relationName);
        Variable subject = subjectTriplets.get(0).getSubject();
        Variable object = objectTriplets.get(0).getSubject();
        return Arrays.asList(new Triplet(subject, relation, object));
    }

    private List<Triplet> parseConceptExpression(String expression, Variable subject)
            throws MappingToUmlTranslationFailedException {
        Matcher matcher = conceptExpression.matcher(expression);
        tryToFindMatch(matcher);
        List<Triplet> res = new ArrayList<>();
        Concept concept = getConcept(matcher.group("concept"));
        Relation typeRelation = TypeRelation.getTyperelation();
        res.add(new Triplet(subject, typeRelation, concept));
        if (matcher.group("relation") != null) {
            Relation relation = getRelation(matcher.group("relation"));
            Variable object = new Variable(NamePicker.getNextThatName());
            res.add(new Triplet(subject, relation, object));
            res.addAll(parseConceptExpression(matcher.group("that"), object));
        }
        return res;
    }

    private Relation getRelation(String relationName)
            throws MappingToUmlTranslationFailedException {
        Optional<Relation> relation = relationManager.getRelationByName(relationName);
        if (relation.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(relationName + "doesn't exist");
        }
        return relation.get();
    }

    private Concept getConcept(String conceptName) throws MappingToUmlTranslationFailedException {
        Optional<Concept> concept = conceptManager.getConceptByName(conceptName);
        if (concept.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(conceptName + "doesn't exist");
        }
        return concept.get();
    }

    private void tryToFindMatch(Matcher matcher) throws MappingToUmlTranslationFailedException {
        boolean found = matcher.matches();
        if (!found) {
            throw new MappingToUmlTranslationFailedException("No match found");
        }
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }
}
