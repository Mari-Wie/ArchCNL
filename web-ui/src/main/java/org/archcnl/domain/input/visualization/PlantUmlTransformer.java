package org.archcnl.domain.input.visualization;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.helpers.WrappingService;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredMapping;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;
import org.archcnl.domain.input.visualization.rules.RuleVisualizer;

public class PlantUmlTransformer {

    private final ConceptManager conceptManager;
    private RelationManager relationManager;

    public PlantUmlTransformer(ConceptManager conceptManager, RelationManager relationManager) {
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
    }

    public String transformToPlantUml(ArchitectureRule rule)
            throws MappingToUmlTranslationFailedException {
        NamePicker.resetGeneratedNameCounter();
        RuleVisualizer visualizer =
                RuleVisualizer.createRuleVisualizer(rule, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        NamePicker.resetGeneratedNameCounter();
        ColoredMapping coloredMapping = flattenAndRecreate(mapping);
        ConceptVisualizer visualizer =
                new ConceptVisualizer(
                        coloredMapping,
                        conceptManager,
                        relationManager,
                        Optional.empty(),
                        new HashSet<>(),
                        ColorState.NEUTRAL);
        visualizer.enableIsTopLevelConcept();
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        NamePicker.resetGeneratedNameCounter();
        ColoredMapping coloredMapping = flattenAndRecreate(mapping);
        RelationVisualizer visualizer =
                new RelationVisualizer(coloredMapping, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    ColoredMapping flattenAndRecreate(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        setMappingsInWhenPart(mapping);
        setMappingInThenTriplet(mapping);
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
    }

    private ColoredMapping flattenAndRecreate(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        setMappingsInWhenPart(mapping);
        setMappingInThenTriplet(mapping);
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        List<ColoredVariant> wrappedVariants =
                WrappingService.wrapMapping(coloredMapping.getThenTriplet());
        coloredMapping.setVariants(wrappedVariants);

        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
    }

    private String buildPlantUmlCode(Visualizer visualizer) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildHeader(visualizer.getName()));
        builder.append(visualizer.buildPlantUmlCode());
        builder.append(buildFooter());
        return builder.toString();
    }

    private String buildHeader(String title) {
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml " + title + "\n");
        builder.append("title " + title + "\n");
        return builder.toString();
    }

    private String buildFooter() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n@enduml");
        return builder.toString();
    }

    private void setMappingInThenTriplet(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        CustomRelation predicate = (CustomRelation) mapping.getThenTriplet().getPredicate();
        try {
            predicate.setMapping(mapping, conceptManager);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private void setMappingInThenTriplet(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();
        try {
            concept.setMapping(mapping);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private void setMappingsInWhenPart(Mapping mapping)
            throws MappingToUmlTranslationFailedException {
        for (AndTriplets variant : mapping.getWhenTriplets()) {
            for (Triplet triplet : variant.getTriplets()) {
                try {
                    if (triplet.getPredicate() instanceof CustomRelation) {
                        CustomRelation predicate = (CustomRelation) triplet.getPredicate();
                        var predicateFromManager =
                                (CustomRelation)
                                        relationManager
                                                .getRelationByName(predicate.getName())
                                                .get();
                        if (predicateFromManager.getMapping().isPresent()) {
                            predicate.setMapping(
                                    predicateFromManager.getMapping().get(), conceptManager);
                        }
                    }
                    if (triplet.getObject() instanceof CustomConcept) {
                        CustomConcept object = (CustomConcept) triplet.getObject();
                        var conceptFromManager =
                                (CustomConcept)
                                        conceptManager.getConceptByName(object.getName()).get();
                        if (conceptFromManager.getMapping().isPresent()) {
                            object.setMapping(conceptFromManager.getMapping().get());
                        }
                    }
                } catch (UnrelatedMappingException e) {
                    throw new MappingToUmlTranslationFailedException(e.getMessage());
                }
            }
        }
    }
}
