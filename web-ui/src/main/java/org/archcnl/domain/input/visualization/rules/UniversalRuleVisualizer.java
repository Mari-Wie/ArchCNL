package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class UniversalRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " can-only "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public UniversalRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
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

    @Override
    protected List<ColoredTriplet> buildRuleTriplets()
            throws MappingToUmlTranslationFailedException {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        subjectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
        objectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
        ColoredTriplet objectBaseTriplet = getTripletWithBaseType(objectTriplets.get(0));
        ruleTriplets.add(objectBaseTriplet);

        Variable realObjectVar = objectTriplets.get(0).getSubject();
        Variable baseObjectVar = objectBaseTriplet.getSubject();
        Variable subjectVar = subjectTriplets.get(0).getSubject();
        ColoredTriplet correctTriplet = new ColoredTriplet(subjectVar, relation, realObjectVar);
        correctTriplet.setColorState(ColorState.CORRECT);
        ColoredTriplet wrongTriplet = new ColoredTriplet(subjectVar, relation, baseObjectVar);
        wrongTriplet.setColorState(ColorState.WRONG);
        ruleTriplets.add(correctTriplet);
        ruleTriplets.add(wrongTriplet);
        return ruleTriplets;
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }
}
