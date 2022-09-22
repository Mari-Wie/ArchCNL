package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationTypeAttribute extends PlantUmlElement {

    private static final String FIELD_MODIFIER = "{field}";

    private Optional<String> hasName = Optional.empty();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();

    public AnnotationTypeAttribute(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIELD_MODIFIER + " ");
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        return builder.toString();
    }

    @Override
    protected String buildNameSection() {
        return hasName.isPresent() ? hasName.get() : variable.transformToGui();
    }

    private String buildTypeSection() {
        if (hasDeclaredType.isPresent()) {
            return " : " + hasDeclaredType.get().getTypeRepresentation();
        }
        return "";
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("hasName".equals(property)) {
            this.hasName = Optional.of((String) object);
        } else if ("hasDeclaredType".equals(property)) {
            this.hasDeclaredType = Optional.of((DeclaredType) object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }
}
