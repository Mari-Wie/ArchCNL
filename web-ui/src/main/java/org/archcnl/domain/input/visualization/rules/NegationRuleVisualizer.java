package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class NegationRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Nothing |No (a |an )?"
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
        if (isNothingRule()) {
            subjectTriplets = Arrays.asList(createNothingTriplet());
        } else {
            subjectTriplets =
                    parseConceptExpression(
                            matcher.group("subject"), Optional.empty(), Optional.empty());
        }
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

        Variable objectVar = objectTriplets.get(0).getSubject();
        Variable subjectVar = subjectTriplets.get(0).getSubject();
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

    private Triplet createNothingTriplet() {
        Variable subject = new Variable("nothing");
        subject = NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
        Relation predicate = TypeRelation.getTyperelation();
        Concept object = new FamixConcept("NothingUML", "");
        return new Triplet(subject, predicate, object);
    }
}
