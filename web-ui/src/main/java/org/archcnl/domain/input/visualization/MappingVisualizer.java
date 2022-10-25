package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public abstract class MappingVisualizer implements Visualizer {

    protected Mapping mapping;
    protected ConceptManager conceptManager;
    protected String mappingName;
    protected Set<Variable> usedVariables;
    protected RelationManager relationManager;

    protected MappingVisualizer(
            Mapping mapping,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables) {
        this.mapping = mapping;
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        this.usedVariables = usedVariables;
        this.mappingName = mapping.getMappingNameRepresentation();
    }

    protected void throwWhenNoVariants(List<AndTriplets> whenTriplets)
            throws MappingToUmlTranslationFailedException {
        if (whenTriplets.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    "Mapping " + mappingName + " has no whenTriplets.");
        }
    }

    public String getName() {
        return mappingName;
    }

    protected abstract boolean moreThanOneVariant();
}
