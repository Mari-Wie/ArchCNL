package org.archcnl.domain.input.visualization.visualizers.mappings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.MappingPreprocessor;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationVisualizer extends MappingVisualizer {

    private List<RelationVariant> variants = new ArrayList<>();

    public RelationVisualizer(
            RelationMapping mapping, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(
                MappingPreprocessor.preprocess(mapping, relationManager, conceptManager),
                conceptManager,
                relationManager,
                new HashSet<>());
        createVariants();
    }

    private void createVariants() throws MappingToUmlTranslationFailedException {
        List<ColoredVariant> coloredVariants = mapping.getVariants();
        throwWhenNoVariants(coloredVariants);
        for (int i = 0; i < coloredVariants.size(); i++) {
            ColoredVariant variant = coloredVariants.get(i);
            String variantName = getName() + (i + 1);
            variants.add(
                    new RelationVariant(
                            variant,
                            mapping.getThenTriplet(),
                            variantName,
                            conceptManager,
                            relationManager,
                            usedVariables));
        }
    }

    @Override
    public String buildPlantUmlCode() {
        boolean printBorder = moreThanOneVariant();
        return variants.stream()
                .map(v -> v.buildPlantUmlCode(printBorder))
                .collect(Collectors.joining("\n"));
    }

    @Override
    protected boolean moreThanOneVariant() {
        return variants.size() > 1;
    }
}
