package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
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
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.visualization.connections.PlantUmlConnection;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredMapping;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

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
        TripletContainer container = new TripletContainer(whenTriplets);
        container.applyElementProperties(elementMap);

        List<PlantUmlBlock> topLevelElements = getTopLevelElements(elementMap);
        topLevelElements = createRequiredParents(topLevelElements);
        List<PlantUmlConnection> connections = container.createConnections(elementMap);
        List<PlantUmlPart> conceptElements = getConceptElements(elementMap);

        List<PlantUmlPart> parts = new ArrayList<>();
        parts.addAll(topLevelElements);
        parts.addAll(connections);
        parts.addAll(conceptElements);
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
        List<Triplet> triplets =
                whenTriplets.stream().map(Triplet.class::cast).collect(Collectors.toList());
        AndTriplets container = new AndTriplets(triplets);
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

            if (elementType instanceof CustomConcept) {
                ConceptMapping mapping = tryToGetMapping((CustomConcept) elementType);
                ColoredMapping coloredMapping =
                        new PlantUmlTransformer(conceptManager, relationManager)
                                .flattenAndRecreate(mapping);
                ConceptVisualizer visualizer =
                        new ConceptVisualizer(
                                coloredMapping,
                                conceptManager,
                                relationManager,
                                Optional.of(variable),
                                usedVariables,
                                colorState);
                elementMap.put(variable, visualizer);
            } else {
                PlantUmlElement element = PlantUmlMapper.createElement(elementType, variable);
                element.setColorState(colorState);
                elementMap.put(variable, element);
            }
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
        if (options.size() == 1) {
            return (Concept) options.iterator().next();
        }
        Concept famixClass = new FamixConcept("FamixClass", "");
        Set<CustomConcept> customOptions =
                options.stream()
                        .filter(CustomConcept.class::isInstance)
                        .map(CustomConcept.class::cast)
                        .collect(Collectors.toSet());
        if (!customOptions.isEmpty()) {
            return pickFromCustomOptions(customOptions);
        } else if (options.contains(famixClass)) {
            // This should mostly occur with objects of the following relations:
            // imports, namespaceContains, definesNestedType, hasDeclaredType
            // FamixClass is a good default for all of them
            return famixClass;
        }
        throw new MappingToUmlTranslationFailedException(
                "No representative type could be picked from: " + options);
    }

    private CustomConcept pickFromCustomOptions(Set<CustomConcept> customOptions)
            throws MappingToUmlTranslationFailedException {
        if (customOptions.size() == 1) {
            return customOptions.iterator().next();
        }
        for (CustomConcept concept : customOptions) {
            Set<CustomConcept> otherOptions = new HashSet<>(customOptions);
            otherOptions.remove(concept);
            Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
            int otherOptionsCount = otherOptions.size();
            otherOptions.retainAll(baseTypes);
            if (otherOptionsCount == otherOptions.size()) {
                return concept;
            }
        }
        throw new MappingToUmlTranslationFailedException(
                "No representative type could be picked from: " + customOptions);
    }

    private List<PlantUmlPart> getConceptElements(Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlPart> conceptElements = new ArrayList<>();
        for (PlantUmlBlock block : elementMap.values()) {
            if (block instanceof ConceptVisualizer) {
                ConceptVisualizer visualizer = (ConceptVisualizer) block;
                conceptElements.add(visualizer.getConceptElement());
            }
        }
        return conceptElements;
    }

    private List<PlantUmlBlock> getTopLevelElements(Map<Variable, PlantUmlBlock> elementMap) {
        return elementMap.values().stream()
                .filter(Predicate.not(PlantUmlBlock::hasParentBeenFound))
                .collect(Collectors.toList());
    }

    private List<PlantUmlBlock> createRequiredParents(List<PlantUmlBlock> topLevelElements) {
        return topLevelElements.stream()
                .map(PlantUmlBlock::createRequiredParentOrReturnSelf)
                .collect(Collectors.toList());
    }
}
