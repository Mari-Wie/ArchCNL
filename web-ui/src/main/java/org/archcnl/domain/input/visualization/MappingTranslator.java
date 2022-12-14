package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.diagram.ConceptMapper;
import org.archcnl.domain.input.visualization.diagram.PlantUmlBlock;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.mappings.ConceptVisualizer;

public class MappingTranslator {

    private List<ColoredTriplet> whenTriplets;
    private ConceptManager conceptManager;
    private RelationManager relationManager;

    public MappingTranslator(
            List<ColoredTriplet> whenTriplets,
            ConceptManager conceptManager,
            RelationManager relationManager) {
        this.whenTriplets = whenTriplets;
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
    }

    public Map<Variable, PlantUmlBlock> createElementMap(Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        Set<Variable> variables = prepareMappingForTranslation();
        Map<Variable, ColorState> elementColorState = createElementColorMap();
        return createPlantUmlModels(variables, usedVariables, elementColorState);
    }

    public List<PlantUmlPart> translateToPlantUmlModel(Map<Variable, PlantUmlBlock> elementMap)
            throws MappingToUmlTranslationFailedException {
        List<PlantUmlPart> parts = new ArrayList<>();
        TripletContainer container = new TripletContainer(whenTriplets);
        container.applyElementProperties(elementMap);

        parts.addAll(replaceWithRequiredParents(getTopLevelElements(elementMap.values())));
        parts.addAll(container.createConnections(elementMap));
        parts.addAll(getConceptBlocks(elementMap));
        return parts;
    }

    private Set<Variable> prepareMappingForTranslation()
            throws MappingToUmlTranslationFailedException {
        Set<Variable> variables = inferVariableTypes();
        whenTriplets = new TripletReducer(whenTriplets, variables).reduce();
        return inferVariableTypes();
    }

    private Set<Variable> inferVariableTypes() throws MappingToUmlTranslationFailedException {
        VariableManager variableManager = new VariableManager();
        AndTriplets container = new ColoredVariant(whenTriplets).toAndTriplets();

        if (variableManager.hasConflictingDynamicTypes(container, conceptManager)) {
            throw new MappingToUmlTranslationFailedException(
                    "Variable with conflicting type usage in mapping.");
        }
        return variableManager.getVariables();
    }

    private Map<Variable, ColorState> createElementColorMap() {
        Map<Variable, ColorState> elementColorState = new HashMap<>();
        for (ColoredTriplet triplet : whenTriplets) {
            elementColorState.putIfAbsent(triplet.getSubject(), triplet.getColorState());
            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                elementColorState.putIfAbsent((Variable) object, triplet.getColorState());
            }
        }
        return elementColorState;
    }

    private Map<Variable, PlantUmlBlock> createPlantUmlModels(
            Set<Variable> variables,
            Set<Variable> usedVariables,
            Map<Variable, ColorState> elementColorState)
            throws MappingToUmlTranslationFailedException {
        Map<Variable, PlantUmlBlock> elementMap = new HashMap<>();
        for (Variable variable : variables) {
            Concept elementType = selectRepresentativeElementType(variable.getDynamicTypes());
            ColorState colorState = elementColorState.get(variable);

            PlantUmlBlock block;
            if (elementType instanceof CustomConcept) {
                ConceptMapping mapping = tryToGetMapping((CustomConcept) elementType);
                block =
                        new ConceptVisualizer(
                                mapping,
                                conceptManager,
                                relationManager,
                                Optional.of(variable),
                                usedVariables,
                                colorState);
            } else {
                block = ConceptMapper.createElement(elementType, variable);
                block.setColorState(colorState);
            }
            elementMap.put(variable, block);
        }
        return elementMap;
    }

    private ConceptMapping tryToGetMapping(CustomConcept concept)
            throws MappingToUmlTranslationFailedException {
        Optional<ConceptMapping> mappingOpt = concept.getMapping();
        if (mappingOpt.isPresent()) {
            return mappingOpt.get();
        }
        throw new MappingToUmlTranslationFailedException(concept.getName() + " has no mapping");
    }

    private Concept selectRepresentativeElementType(Set<ActualObjectType> options)
            throws MappingToUmlTranslationFailedException {
        // TODO add support for other element types aside from Concept
        if (options.size() == 1) {
            return (Concept) options.iterator().next();
        }
        Concept famixClass = new FamixConcept("FamixClass", "");
        Set<CustomConcept> customOptions = getCustomConceptOptions(options);

        if (!customOptions.isEmpty()) {
            return pickFromCustomOptions(customOptions);
        } else if (options.contains(famixClass)) {
            // FamixClass is generally a good default
            return famixClass;
        }
        throw new MappingToUmlTranslationFailedException(
                "No representative type could be picked from: " + options);
    }

    private Set<CustomConcept> getCustomConceptOptions(Set<ActualObjectType> options) {
        return options.stream()
                .filter(CustomConcept.class::isInstance)
                .map(CustomConcept.class::cast)
                .collect(Collectors.toSet());
    }

    private CustomConcept pickFromCustomOptions(Set<CustomConcept> customOptions)
            throws MappingToUmlTranslationFailedException {
        if (customOptions.size() == 1) {
            return customOptions.iterator().next();
        }
        for (CustomConcept concept : customOptions) {
            if (isPredecessorToAllOtherOptions(concept, customOptions)) {
                return concept;
            }
        }
        throw new MappingToUmlTranslationFailedException(
                "No representative type could be picked from: " + customOptions);
    }

    private boolean isPredecessorToAllOtherOptions(
            CustomConcept concept, Set<CustomConcept> options) {
        Set<CustomConcept> otherOptions = new HashSet<>(options);
        otherOptions.remove(concept);
        Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
        int otherOptionsCount = otherOptions.size();
        otherOptions.retainAll(baseTypes);
        return otherOptionsCount == otherOptions.size();
    }

    private List<PlantUmlBlock> getConceptBlocks(Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlBlock> conceptBlocks = new ArrayList<>();
        for (PlantUmlBlock element : elementMap.values()) {
            if (element instanceof ConceptVisualizer) {
                ConceptVisualizer visualizer = (ConceptVisualizer) element;
                conceptBlocks.add(visualizer.getConceptBlock());
            }
        }
        return conceptBlocks;
    }

    private List<PlantUmlBlock> getTopLevelElements(Collection<PlantUmlBlock> elements) {
        return elements.stream()
                .filter(Predicate.not(PlantUmlBlock::hasParentBeenFound))
                .collect(Collectors.toList());
    }

    private List<PlantUmlBlock> replaceWithRequiredParents(List<PlantUmlBlock> topLevelElements) {
        return topLevelElements.stream()
                .map(PlantUmlBlock::createRequiredParentOrReturnSelf)
                .collect(Collectors.toList());
    }
}
