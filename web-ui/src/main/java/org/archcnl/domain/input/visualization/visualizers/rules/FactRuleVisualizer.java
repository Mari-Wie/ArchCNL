package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RuleVariant;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public class FactRuleVisualizer extends RuleVisualizer {

    public FactRuleVisualizer(
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
        return Arrays.asList(variant);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        throw new UnsupportedOperationException("The fact rule type doesn't allow OR");
    }
}
