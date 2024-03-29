package org.archcnl.domain.input.visualization.visualizers.mappings;

import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationVariant extends MappingVariant {

    public RelationVariant(
            ColoredVariant variant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {

        super(
                variant.getTriplets(),
                thenTriplet,
                conceptManager,
                relationManager,
                usedVariables,
                variantName);
        pickUniqueVariables();
        buildContentParts();
    }
}
