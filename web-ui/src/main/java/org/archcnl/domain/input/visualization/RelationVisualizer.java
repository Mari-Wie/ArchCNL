package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationVisualizer extends MappingVisualizer {

    private List<RelationVariant> variants = new ArrayList<>();

    public RelationVisualizer(RelationMapping mapping, ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        super(mapping, conceptManager, new HashSet<>());
        createVariants();
    }

    private void createVariants() throws MappingToUmlTranslationFailedException {
        List<AndTriplets> whenTriplets = mapping.getWhenTriplets();
        throwWhenNoVariants(whenTriplets);
        for (int i = 0; i < whenTriplets.size(); i++) {
            AndTriplets whenVariant = whenTriplets.get(i);
            String variantName = mappingName + (i + 1);
            variants.add(
                    new RelationVariant(
                            whenVariant,
                            mapping.getThenTriplet(),
                            variantName,
                            conceptManager,
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
