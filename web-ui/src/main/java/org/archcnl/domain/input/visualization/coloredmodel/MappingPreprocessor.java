package org.archcnl.domain.input.visualization.coloredmodel;

import java.util.List;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.WrappingService;

public class MappingPreprocessor {

    private MappingPreprocessor() {}

    public static ColoredMapping preprocess(
            ConceptMapping mapping, RelationManager relationManager, ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        MappingSetter.setMappingsInWhenPart(
                mapping.getWhenTriplets(), relationManager, conceptManager);
        MappingSetter.setMappingInThenTriplet(mapping);
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
    }

    public static ColoredMapping preprocess(
            RelationMapping mapping, RelationManager relationManager, ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        MappingSetter.setMappingsInWhenPart(
                mapping.getWhenTriplets(), relationManager, conceptManager);
        MappingSetter.setMappingInThenTriplet(mapping, conceptManager);
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        List<ColoredVariant> wrappedVariants =
                WrappingService.wrapMapping(coloredMapping.getThenTriplet());
        coloredMapping.setVariants(wrappedVariants);

        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
    }
}
