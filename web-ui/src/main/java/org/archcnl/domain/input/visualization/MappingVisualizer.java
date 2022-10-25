package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColoredMapping;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;

public abstract class MappingVisualizer implements Visualizer {

    protected ColoredMapping mapping;
    protected ConceptManager conceptManager;
    protected Set<Variable> usedVariables;
    protected RelationManager relationManager;

    protected MappingVisualizer(
            ColoredMapping mapping,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables) {
        this.mapping = mapping;
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        this.usedVariables = usedVariables;
    }

    protected void throwWhenNoVariants(List<ColoredVariant> variants)
            throws MappingToUmlTranslationFailedException {
        if (variants.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    "Mapping " + getName() + " has no whenTriplets.");
        }
    }

    public String getName() {
        return mapping.getName();
    }

    protected abstract boolean moreThanOneVariant();
}
