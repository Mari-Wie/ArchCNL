package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RuleVariant;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public class DomainRangeRuleVisualizer extends RuleVisualizer {

    public DomainRangeRuleVisualizer(
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
    protected List<RuleVariant> buildRuleVariantsAnd()
            throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            correct.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "C"));
            correct.addCopyOfPredicate(phrase.getPredicate());
        }

        RuleVariant wrong = new RuleVariant();
        var triplet =
                RuleHelper.getTripletWithBaseType(
                        subjectTriplets.get(0), conceptManager, usedVariables);
        wrong.setSubjectTriplets(addPostfixToAllVariables(Arrays.asList(triplet), "W"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            wrong.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "W"));
            wrong.addCopyOfPredicate(phrase.getPredicate());
        }

        correct.setPredicateToColorState(ColorState.CORRECT);
        wrong.setPredicateToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        StringBuilder correctPostfix = new StringBuilder("C");
        StringBuilder wrongPostfix = new StringBuilder("W");
        List<RuleVariant> variants = new ArrayList<>();

        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            RuleVariant correct = new RuleVariant();
            correct.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, correctPostfix.toString()));
            correct.addObjectTriplets(
                    addPostfixToAllVariables(
                            phrase.getObjectTriplets(), correctPostfix.toString()));
            correct.addCopyOfPredicate(phrase.getPredicate());

            RuleVariant wrong = new RuleVariant();
            var triplet =
                    RuleHelper.getTripletWithBaseType(
                            subjectTriplets.get(0), conceptManager, usedVariables);
            wrong.setSubjectTriplets(
                    addPostfixToAllVariables(Arrays.asList(triplet), wrongPostfix.toString()));
            wrong.addObjectTriplets(
                    addPostfixToAllVariables(phrase.getObjectTriplets(), wrongPostfix.toString()));
            wrong.addCopyOfPredicate(phrase.getPredicate());

            correct.setPredicateToColorState(ColorState.CORRECT);
            wrong.setPredicateToColorState(ColorState.WRONG);

            variants.add(correct);
            variants.add(wrong);
            correctPostfix.append("C");
            wrongPostfix.append("W");
        }
        return variants;
    }
}
