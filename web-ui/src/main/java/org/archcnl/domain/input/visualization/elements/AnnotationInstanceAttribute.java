package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationInstanceAttribute extends PlantUmlElement {

    private Optional<String> hasValue = Optional.empty();
    private Optional<AnnotationTypeAttribute> hasAnnotationTypeAttribute = Optional.empty();

    public AnnotationInstanceAttribute(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        // This is not in accordance with the UML standard
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        builder.append(buildValueSection());
        return builder.toString();
    }

    private String buildNameSection() {
        if (hasAnnotationTypeAttribute.isPresent()) {
            return hasAnnotationTypeAttribute.get().buildNameSection();
        }
        return variable.getName();
    }

    private String buildValueSection() {
        return hasValue.isPresent() ? "=" + hasValue.get() : "";
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("hasValue".equals(property)) {
            this.hasValue = Optional.of((String) object);
        } else if ("hasDeclaredType".equals(property)) {
            this.hasAnnotationTypeAttribute = Optional.of((AnnotationTypeAttribute) object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }
}
