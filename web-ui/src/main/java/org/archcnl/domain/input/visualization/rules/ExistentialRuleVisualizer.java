package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

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

    private List<Triplet> subjectTriplets;
    private List<Triplet> objectTriplets;
    private Relation relation;

    public ExistentialRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
        parseRule(rule.toString());
        List<ColoredTriplet> ruleTriplets = buildRuleTriplets();
        buildUmlElements(ruleTriplets);
    }

    private List<ColoredTriplet> buildRuleTriplets() {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        objectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
        List<ColoredTriplet> correctSubject = buildColoredSubjectTriplets(ColorState.CORRECT);
        List<ColoredTriplet> wrongSubject = buildColoredSubjectTriplets(ColorState.WRONG);
        ruleTriplets.addAll(correctSubject);
        ruleTriplets.addAll(wrongSubject);

        Variable correctSubjectVar = correctSubject.get(0).getSubject();
        Variable objectVar = objectTriplets.get(0).getSubject();
        ruleTriplets.add(new ColoredTriplet(correctSubjectVar, relation, objectVar));
        return ruleTriplets;
    }

    private List<ColoredTriplet> buildColoredSubjectTriplets(ColorState state) {
        String postfix = state == ColorState.CORRECT ? "C" : "W";
        var withUniqueVariables = addPostfixToAllVariables(subjectTriplets, postfix);
        return withUniqueVariables.stream()
                .map(
                        t -> {
                            var coloredT = new ColoredTriplet(t);
                            coloredT.setColorState(state);
                            return coloredT;
                        })
                .collect(Collectors.toList());
    }

    private List<Triplet> addPostfixToAllVariables(List<Triplet> triplets, String postfix) {
        List<Triplet> newTriplets = new ArrayList<>();
        for (Triplet triplet : triplets) {
            String oldSubjectName = triplet.getSubject().getName();
            Variable subject = new Variable(oldSubjectName + postfix);
            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                String oldObjectName = object.getName();
                object = new Variable(oldObjectName + postfix);
            }
            newTriplets.add(new Triplet(subject, triplet.getPredicate(), object));
        }
        return newTriplets;
    }

    private void parseRule(String rule) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(rule);
        tryToFindMatch(matcher);
        subjectTriplets = parseConceptExpression(matcher.group("subject"), new Variable("subject"));
        objectTriplets = parseConceptExpression(matcher.group("object"), new Variable("object"));
        relation = parsePredicate(matcher.group("predicate"));
    }

    private Relation parsePredicate(String group) throws MappingToUmlTranslationFailedException {
        String relationName = group.split(" ")[0];
        // TODO handle cardinality modifiers
        return getRelation(relationName);
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
            throw new MappingToUmlTranslationFailedException(relationName + " doesn't exist");
        }
        return relation.get();
    }

    private Concept getConcept(String conceptName) throws MappingToUmlTranslationFailedException {
        Optional<Concept> concept = conceptManager.getConceptByName(conceptName);
        if (concept.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(conceptName + " doesn't exist");
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
