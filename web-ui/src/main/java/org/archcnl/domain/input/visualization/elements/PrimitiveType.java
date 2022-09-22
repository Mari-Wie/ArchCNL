package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class PrimitiveType extends PlantUmlElement implements DeclaredType {

    private Optional<String> hasName = Optional.empty();

    public PrimitiveType(Variable variable) {
        super(variable, false);
    }

    @Override
    public String getTypeRepresentation() {
        return hasName.isPresent() ? hasName.get() : variable.transformToGui();
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("class ");
        builder.append(buildNameSection());
        builder.append(variable.getName());
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("hasName".equals(property)) {
            this.hasName = Optional.of((String) object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String buildNameSection() {
        if (hasName.isEmpty()) {
            return "";
        }
        return " \"" + hasName.get() + "\" as ";
    }
}
