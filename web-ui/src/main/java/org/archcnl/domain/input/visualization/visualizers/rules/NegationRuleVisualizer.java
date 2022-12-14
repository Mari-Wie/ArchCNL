package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RuleVariant;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public class NegationRuleVisualizer extends RuleVisualizer {

    public NegationRuleVisualizer(
            String cnlString,
            List<Triplet> subjectTriplets,
            VerbPhraseContainer verbPhrases,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        super(
                cnlString,
                subjectTriplets,
                verbPhrases,
                conceptManager,
                relationManager,
                usedVariables);
        transformToDiagram();
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsAnd() {
        RuleVariant variant = new RuleVariant();
        variant.setSubjectTriplets(
                subjectTriplets.stream().map(ColoredTriplet::new).collect(Collectors.toList()));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            variant.addObjectTriplets(
                    phrase.getObjectTriplets().stream()
                            .map(ColoredTriplet::new)
                            .collect(Collectors.toList()));
            variant.addCopyOfPredicate(phrase.getPredicate());
        }

        variant.setPredicateToColorState(ColorState.WRONG);
        return Arrays.asList(variant);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr() {
        StringBuilder postfix = new StringBuilder();
        List<RuleVariant> variants = new ArrayList<>();
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            RuleVariant variant = new RuleVariant();

            variant.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, postfix.toString()));
            variant.addObjectTriplets(
                    addPostfixToAllVariables(phrase.getObjectTriplets(), postfix.toString()));
            variant.addCopyOfPredicate(phrase.getPredicate());

            variant.setPredicateToColorState(ColorState.WRONG);

            variants.add(variant);
            postfix.append("1");
        }
        return variants;
    }
}
