package org.archcnl.domain.input.visualization;

import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationMappingVariant extends MappingVariant {

    public RelationMappingVariant(
            AndTriplets whenVariant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {

        super(whenVariant, thenTriplet, conceptManager, usedVariables, variantName);
        pickUniqueVariables();
        buildContentParts();
    }
}
