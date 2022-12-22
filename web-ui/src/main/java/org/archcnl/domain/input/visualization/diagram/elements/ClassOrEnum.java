package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.diagram.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public abstract class ClassOrEnum extends NamespaceContent implements FamixType {

    private Optional<PlantUmlElement> hasFullQualifiedName = Optional.empty();
    protected ModifierContainer modifierContainer = new ModifierContainer();
    private List<PlantUmlElement> hasAnnotationInstance = new ArrayList<>();
    private List<PlantUmlElement> definesAttribute = new ArrayList<>();
    private List<PlantUmlElement> definesMethod = new ArrayList<>();

    protected ClassOrEnum(Variable variable) {
        super(variable);
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
            case "definesAttribute":
                object.setParent(this);
                this.definesAttribute.add(object);
                break;
            case "definesMethod":
                object.setParent(this);
                this.definesMethod.add(object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
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

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " "
                + hasAnnotationInstance.stream()
                        .map(PlantUmlElement::buildPlantUmlCode)
                        .collect(Collectors.joining());
    }

    @Override
    protected String getHighestRankingName() {
        String nameSection = variable.transformToGui();
        if (hasFullQualifiedName.isPresent()) {
            nameSection = hasFullQualifiedName.get().toString();
        }
        if (hasName.isPresent()) {
            nameSection = hasName.get().toString();
        }
        return nameSection;
    }

    @Override
    protected List<String> buildBodySectionContentLines() {
        List<String> bodyContentLines = new ArrayList<>();
        bodyContentLines.addAll(buildAttributeLines());
        bodyContentLines.addAll(buildMethodLines());
        return bodyContentLines;
    }

    private List<String> buildAttributeLines() {
        return definesAttribute.stream()
                .map(PlantUmlElement::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    private List<String> buildMethodLines() {
        return definesMethod.stream()
                .map(PlantUmlElement::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    protected String buildVisibilityPrefixSection() {
        return modifierContainer.getVisibilityPrefix();
    }
}
