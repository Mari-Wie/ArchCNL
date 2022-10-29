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

public class DomainRangeRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Only "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " can "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public DomainRangeRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected List<ColoredTriplet> buildRuleTriplets()
            throws MappingToUmlTranslationFailedException {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        subjectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
        objectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
        ColoredTriplet subjectBaseTriplet = getTripletWithBaseType(subjectTriplets.get(0));
        ruleTriplets.add(subjectBaseTriplet);

        Variable realSubjectVar = subjectTriplets.get(0).getSubject();
        Variable baseSubjectVar = subjectBaseTriplet.getSubject();
        Variable objectVar = objectTriplets.get(0).getSubject();
        ColoredTriplet correctTriplet = new ColoredTriplet(realSubjectVar, relation, objectVar);
        correctTriplet.setColorState(ColorState.CORRECT);
        ColoredTriplet wrongTriplet = new ColoredTriplet(baseSubjectVar, relation, objectVar);
        wrongTriplet.setColorState(ColorState.WRONG);
        ruleTriplets.add(correctTriplet);
        ruleTriplets.add(wrongTriplet);
        return ruleTriplets;
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
}
