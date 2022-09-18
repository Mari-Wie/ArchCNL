package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Parameter implements PlantUmlElement {

    private Variable variable;
    private Optional<String> hasName = Optional.empty();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();

    public Parameter(Variable variable) {
        this.variable = variable;
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        return builder.toString();
    }

    private String buildNameSection() {
        return hasName.isPresent() ? hasName.get() : variable.transformToGui();
    }

    private String buildTypeSection() {
        StringBuilder builder = new StringBuilder();
        if (hasDeclaredType.isPresent()) {
            builder.append(":");
            builder.append(hasDeclaredType.get().getTypeRepresentation());
        }
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
                break;
            case "hasDeclaredType":
                this.hasDeclaredType = Optional.of((DeclaredType) object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }
}
