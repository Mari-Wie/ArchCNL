package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public class AnnotationTypeAttribute extends PlantUmlElement {

    private static final String FIELD_MODIFIER = "{field}";

    private Optional<PlantUmlElement> hasName = Optional.empty();
    private Optional<PlantUmlElement> hasDeclaredType = Optional.empty();

    public AnnotationTypeAttribute(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildColorSection());
        builder.append(FIELD_MODIFIER + " ");
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        return builder.toString();
    }

    @Override
    protected String buildNameSection() {
        return hasName.isPresent() ? hasName.get().toString() : variable.transformToGui();
    }

    private String buildTypeSection() {
        if (hasDeclaredType.isPresent()) {
            return " : " + hasDeclaredType.get().getHighestRankingName();
        }
        return "";
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        if ("hasName".equals(property)) {
            this.hasName = Optional.of(object);
        } else if ("hasDeclaredType".equals(property)) {
            this.hasDeclaredType = Optional.of(object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        AnnotationType annotationType = new AnnotationType(new Variable(parentName));
        annotationType.setProperty("hasAnnotationTypeAttribute", this);
        return annotationType;
    }

    @Override
    protected String buildColorSection() {
        if (colorState != ColorState.NEUTRAL) {
            return String.format("<color:%s> ", colorState.getColorName());
        }
        return "";
    }
}
