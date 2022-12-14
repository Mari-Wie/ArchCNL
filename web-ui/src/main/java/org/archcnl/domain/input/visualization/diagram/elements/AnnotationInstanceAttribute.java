package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public class AnnotationInstanceAttribute extends PlantUmlElement {

    private Optional<PlantUmlElement> hasValue = Optional.empty();
    private Optional<PlantUmlElement> hasAnnotationTypeAttribute = Optional.empty();

    public AnnotationInstanceAttribute(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        // This is not in accordance with the UML standard
        StringBuilder builder = new StringBuilder();
        builder.append(buildColorSection());
        builder.append(buildNameSection());
        builder.append(buildValueSection());
        builder.append(closeColorSection());
        return builder.toString();
    }

    @Override
    protected String buildNameSection() {
        if (hasAnnotationTypeAttribute.isPresent()) {
            return hasAnnotationTypeAttribute.get().buildNameSection();
        }
        return variable.getName();
    }

    private String buildValueSection() {
        return hasValue.isPresent() ? "=" + hasValue.get() : "";
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        if ("hasValue".equals(property)) {
            this.hasValue = Optional.of(object);
        } else if ("hasDeclaredType".equals(property)) {
            this.hasAnnotationTypeAttribute = Optional.of(object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        AnnotationInstance annotationInstance = new AnnotationInstance(new Variable(parentName));
        annotationInstance.setProperty("hasAnnotationInstanceAttribute", this);
        return annotationInstance;
    }

    @Override
    protected String buildColorSection() {
        if (colorState != ColorState.NEUTRAL) {
            return String.format("<color:%s>", colorState.getColorCode());
        }
        return "";
    }

    private String closeColorSection() {
        if (colorState != ColorState.NEUTRAL) {
            return "</color>";
        }
        return "";
    }
}
