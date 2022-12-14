package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.diagram.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationType extends NamespaceContent implements FamixType {

    private Optional<PlantUmlElement> hasFullQualifiedName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    // PlantUml does not allow for multiple stereotypes
    private List<PlantUmlElement> hasAnnotationInstance = new ArrayList<>();
    private List<PlantUmlElement> hasAnnotationTypeAttribute = new ArrayList<>();

    public AnnotationType(Variable variable) {
        super(variable);
    }

    @Override
    protected String buildNameSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildVisibilityPrefixSection());
        builder.append(getElementIdentifier() + " ");
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(variable.getName());
        builder.append(buildAnnotationSection());
        return builder.toString();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of(object);
                break;
            case "hasFullQualifiedName":
                this.hasFullQualifiedName = Optional.of(object);
                break;
            case "hasModifier":
                this.modifierContainer.setModifier(object.toString());
                break;
            case "hasAnnotationInstance":
                object.setParent(this);
                this.hasAnnotationInstance.add(object);
                break;
            case "hasAnnotationTypeAttribute":
                object.setParent(this);
                this.hasAnnotationTypeAttribute.add(object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String getHighestRankingName() {
        String nameSection = variable.transformToGui();
        if (hasFullQualifiedName.isPresent()) {
            nameSection = hasFullQualifiedName.get().buildPlantUmlCode();
        }
        if (hasName.isPresent()) {
            nameSection = hasName.get().buildPlantUmlCode();
        }
        return nameSection;
    }

    @Override
    protected String getElementIdentifier() {
        return "annotation";
    }

    @Override
    protected List<String> buildBodySectionContentLines() {
        return hasAnnotationTypeAttribute.stream()
                .map(PlantUmlElement::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " "
                + hasAnnotationInstance.stream()
                        .map(PlantUmlElement::buildPlantUmlCode)
                        .collect(Collectors.joining());
    }

    protected String buildVisibilityPrefixSection() {
        return modifierContainer.getVisibilityPrefix();
    }

    @Override
    protected String buildParentSection() {
        return "";
    }
}
