package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.visualization.connections.BasicConnection;
import org.archcnl.domain.input.visualization.elements.CustomConceptElement;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.MultipleBaseElementsException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class CustomConceptVisualizer implements PlantUmlBlock {

    private final String mappingName;
    private final CustomConceptElement conceptElement;
    private List<ConceptMappingVariant> variants = new ArrayList<>();
    private ConceptManager conceptManager;

    public CustomConceptVisualizer(
            ConceptMapping mapping,
            ConceptManager conceptManager,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        this.conceptManager = conceptManager;
        this.mappingName = mapping.getMappingNameRepresentation();

        // Cast is safe as a ConceptMapping will always have a CustomConcept in the object of it's
        // thenTriplet
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();
        this.conceptElement =
                new CustomConceptElement(
                        concept,
                        UniqueNamePicker.pickUniqueVariable(
                                        usedVariables,
                                        new HashMap<>(),
                                        new Variable(concept.getName()))
                                .getName());
        Variable thenSubject = mapping.getThenTriplet().getSubject();
        createVariants(mapping.getWhenTriplets(), thenSubject, parentSubject, usedVariables);
    }

    private void throwWhenNoVariants(List<AndTriplets> whenTriplets)
            throws MappingToUmlTranslationFailedException {
        if (whenTriplets.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    "Mapping " + mappingName + " has no whenTriplets.");
        }
    }

    private void createVariants(
            List<AndTriplets> whenTriplets,
            Variable thenSubject,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        throwWhenNoVariants(whenTriplets);
        for (int i = 0; i < whenTriplets.size(); i++) {
            AndTriplets whenVariant = whenTriplets.get(i);
            String variantName = mappingName + (i + 1);
            variants.add(
                    new ConceptMappingVariant(
                            whenVariant,
                            thenSubject,
                            variantName,
                            conceptManager,
                            parentSubject,
                            usedVariables));
        }
    }

    @Override
    public String buildPlantUmlCode() {
        boolean moreThanOne = variants.size() > 1;
        StringBuilder builder = new StringBuilder();
        for (ConceptMappingVariant variant : variants) {
            builder.append(variant.buildPlantUmlCode(moreThanOne));
            builder.append("\n");
        }
        builder.append(conceptElement.buildPlantUmlCode());
        for (ConceptMappingVariant variant : variants) {
            builder.append("\n");
            List<String> conceptIds = conceptElement.getIdentifier();
            String objectId = variant.getIdentifier();
            for (String conceptId : conceptIds) {
                BasicConnection connection = new BasicConnection(conceptId, objectId);
                builder.append(connection.buildPlantUmlCode());
            }
        }
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        for (var variant : variants) {
            variant.setProperty(property, object);
        }
    }

    @Override
    public boolean hasParentBeenFound() {
        // TODO check how this works with below class level mappings
        return false;
    }

    @Override
    public boolean hasRequiredParent() {
        return true;
    }

    @Override
    public List<String> getIdentifier() {
        return variants.stream()
                .map(ConceptMappingVariant::getIdentifier)
                .collect(Collectors.toList());
    }

    public PlantUmlElement getBaseElement() throws MultipleBaseElementsException {
        if (variants.size() > 1) {
            throw new MultipleBaseElementsException(mappingName + " has multiple base elements");
        }
        return variants.get(0).getBaseElement();
    }
}
