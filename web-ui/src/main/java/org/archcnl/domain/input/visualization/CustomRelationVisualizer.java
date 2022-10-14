package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class CustomRelationVisualizer extends MappingVisualizer {

    private List<RelationMappingVariant> variants = new ArrayList<>();

    public CustomRelationVisualizer(
            RelationMapping mapping,
            ConceptManager conceptManager,
            Set<Variable> usedVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject)
            throws MappingToUmlTranslationFailedException {
        super(mapping, conceptManager, usedVariables);
        createVariants(parentSubject, parentObject);
    }

    private void createVariants(Optional<Variable> parentSubject, Optional<Variable> parentObject)
            throws MappingToUmlTranslationFailedException {
        List<AndTriplets> whenTriplets = mapping.getWhenTriplets();
        throwWhenNoVariants(whenTriplets);
        for (int i = 0; i < whenTriplets.size(); i++) {
            AndTriplets whenVariant = whenTriplets.get(i);
            String variantName = mappingName + (i + 1);
            variants.add(
                    new RelationMappingVariant(
                            whenVariant,
                            mapping.getThenTriplet(),
                            variantName,
                            conceptManager,
                            parentSubject,
                            parentObject,
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
