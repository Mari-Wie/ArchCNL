package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class NegationRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Nothing|No (a |an )?"
                            + SUBJECT_REGEX
                            + ")"
                            + " can "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public NegationRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(ruleString);
        tryToFindMatch(matcher);
        relation = parsePredicate(matcher.group("predicate"));
        if (!isNothingRule()) {
            subjectTriplets =
                    parseConceptExpression(
                            matcher.group("subject"), Optional.empty(), Optional.empty());
        }
        objectTriplets =
                parseConceptExpression(matcher.group("object"), Optional.empty(), Optional.empty());
    }

    @Override
    protected List<ColoredTriplet> buildRuleTriplets()
            throws MappingToUmlTranslationFailedException {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();

        Variable subjectVar;
        if (isNothingRule()) {
            subjectVar = createNothingVariable();
        } else {
            subjectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));
            subjectVar = subjectTriplets.get(0).getSubject();
        }
        objectTriplets.forEach(t -> ruleTriplets.add(new ColoredTriplet(t)));

        Variable objectVar = objectTriplets.get(0).getSubject();
        ColoredTriplet wrongTriplet = new ColoredTriplet(subjectVar, relation, objectVar);
        wrongTriplet.setColorState(ColorState.WRONG);
        ruleTriplets.add(wrongTriplet);
        return ruleTriplets;
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    private boolean isNothingRule() {
        return cnlString.startsWith("Nothing");
    }

    private Variable createNothingVariable() {
        Variable subject = new Variable("nothing");
        return NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
    }
}
