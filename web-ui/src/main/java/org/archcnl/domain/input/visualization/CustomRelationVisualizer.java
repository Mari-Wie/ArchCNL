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

public class CustomRelationVisualizer {

    private ConceptManager conceptManager;
    private List<RelationMappingVariant> variants = new ArrayList();
    private String mappingName;

    public CustomRelationVisualizer(
            RelationMapping mapping,
            ConceptManager conceptManager,
            Set<Variable> usedVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject)
            throws MappingToUmlTranslationFailedException {
        this.conceptManager = conceptManager;
        this.mappingName = mapping.getMappingNameRepresentation();
        createVariants(mapping, usedVariables, parentSubject, parentObject);
    }

    private void createVariants(
            RelationMapping mapping,
            Set<Variable> usedVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject)
            throws MappingToUmlTranslationFailedException {
        List<AndTriplets> whenTriplets = mapping.getWhenTriplets();
        throwWhenNoVariants(whenTriplets);
        boolean moreThanOne = whenTriplets.size() > 1;
        for (int i = 0; i < whenTriplets.size(); i++) {
            AndTriplets whenVariant = whenTriplets.get(i);
            String variantName = mappingName + (i + 1);
            variants.add(
                    new RelationMappingVariant(
                            moreThanOne,
                            whenVariant,
                            mapping.getThenTriplet(),
                            variantName,
                            conceptManager,
                            parentSubject,
                            parentObject,
                            usedVariables));
        }
    }

    private void throwWhenNoVariants(List<AndTriplets> whenTriplets)
            throws MappingToUmlTranslationFailedException {
        if (whenTriplets.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    "Mapping " + mappingName + " has no whenTriplets.");
        }
    }

    public String buildPlantUmlCode() {
        return variants.stream()
                .map(RelationMappingVariant::buildPlantUmlCode)
                .collect(Collectors.joining("\n"));
    }

    public List<AndTriplets> getTripletsOfVariants() {
        return null;
    }
}
