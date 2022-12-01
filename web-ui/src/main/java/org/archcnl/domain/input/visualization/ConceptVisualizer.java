package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.BasicConnection;
import org.archcnl.domain.input.visualization.elements.CustomConceptPart;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredMapping;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;

public class ConceptVisualizer extends MappingVisualizer implements PlantUmlBlock {

    private CustomConceptPart conceptElement;
    private List<ConceptVariant> variants = new ArrayList<>();
    private boolean isTopLevelConcept = false;

    public ConceptVisualizer(
            ColoredMapping mapping,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables,
            ColorState colorState)
            throws MappingToUmlTranslationFailedException {
        super(mapping, conceptManager, relationManager, usedVariables);
        copyColorState(colorState);
        createConceptElement();
        createVariants(parentSubject);
    }

    private void copyColorState(ColorState colorState) {
        for (ColoredVariant variant : mapping.getVariants()) {
            for (ColoredTriplet triplet : variant.getTriplets()) {
                triplet.setColorState(colorState);
            }
        }
    }

    private void createConceptElement() {
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();

        Variable uniqueVar =
                NamePicker.pickUniqueVariable(
                        usedVariables, new HashMap<>(), new Variable(concept.getName()));

        this.conceptElement = new CustomConceptPart(concept, uniqueVar.getName());
    }

    private void createVariants(Optional<Variable> parentSubject)
            throws MappingToUmlTranslationFailedException {
        List<ColoredVariant> coloredVariants = mapping.getVariants();
        ColoredTriplet thenTriplet = mapping.getThenTriplet();
        throwWhenNoVariants(coloredVariants);
        String suffix = NamePicker.getUniqueConceptVariantSuffix(getName());

        for (int i = 0; i < coloredVariants.size(); i++) {
            ColoredVariant variant = coloredVariants.get(i);
            String variantName = getName() + (i + 1) + suffix;
            variants.add(
                    new ConceptVariant(
                            variant,
                            thenTriplet,
                            variantName,
                            conceptManager,
                            relationManager,
                            parentSubject,
                            usedVariables));
        }
    }

    @Override
    public String buildPlantUmlCode() {
        boolean printBorder = moreThanOneVariant();
        StringBuilder builder = new StringBuilder();
        builder.append(
                variants.stream()
                        .map(v -> v.buildPlantUmlCode(printBorder))
                        .collect(Collectors.joining("\n")));
        if (isTopLevelConcept) {
            addConnectionsToConceptElement();
            builder.append("\n");
            builder.append(conceptElement.buildPlantUmlCode());
        }
        return builder.toString();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        for (var variant : variants) {
            variant.setProperty(property, object);
        }
    }

    @Override
    public boolean hasParentBeenFound() {
        for (ConceptVariant variant : variants) {
            variant.removeUmlElementsWithParents();
        }
        return false;
    }

    @Override
    public List<String> getIdentifiers() {
        return variants.stream()
                .map(ConceptVariant::getIdentifiers)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<PlantUmlElement> getBaseElements() {
        return variants.stream()
                .map(ConceptVariant::getBaseElements)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    protected boolean moreThanOneVariant() {
        return variants.size() > 1;
    }

    @Override
    public PlantUmlBlock createRequiredParentOrReturnSelf() {
        for (var variant : variants) {
            variant.getBaseElements().forEach(PlantUmlBlock::createRequiredParentOrReturnSelf);
        }
        return this;
    }

    public PlantUmlPart getConceptElement() {
        addConnectionsToConceptElement();
        return conceptElement;
    }

    private void addConnectionsToConceptElement() {
        for (ConceptVariant variant : variants) {
            List<String> conceptIds = conceptElement.getIdentifiers();
            List<String> objectIds = variant.getIdentifiers();
            for (String conceptId : conceptIds) {
                for (String objectId : objectIds) {
                    BasicConnection connection = new BasicConnection(conceptId, objectId);
                    conceptElement.addConnection(connection);
                }
            }
        }
    }

    public void enableIsTopLevelConcept() {
        isTopLevelConcept = true;
    }

    @Override
    public void setColorState(ColorState colorState) {}
}
