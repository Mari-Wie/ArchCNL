package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class ExistentialRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every "
                            + "(a |an )?"
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
    }

    @Override
    protected List<ColoredTriplet> buildRuleTriplets()
            throws MappingToUmlTranslationFailedException {
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

    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(ruleString);
        tryToFindMatch(matcher);
        subjectTriplets =
                parseConceptExpression(
                        matcher.group("subject"), Optional.empty(), Optional.empty());
        objectTriplets =
                parseConceptExpression(matcher.group("object"), Optional.empty(), Optional.empty());
        relation = parsePredicate(matcher.group("predicate"));
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }
}
