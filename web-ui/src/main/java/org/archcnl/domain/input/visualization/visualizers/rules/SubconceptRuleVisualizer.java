package org.archcnl.domain.input.visualization.visualizers.rules;

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

public class SubconceptRuleVisualizer extends RuleVisualizer {

    public SubconceptRuleVisualizer(
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
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));

        correct.setPredicateToColorState(ColorState.CORRECT);
        wrong.setSubjectToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        throw new UnsupportedOperationException("The sub-concept rule type doesn't allow OR");
    }
}
