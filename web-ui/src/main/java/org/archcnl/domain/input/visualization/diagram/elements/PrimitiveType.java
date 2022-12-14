package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class PrimitiveType extends PlantUmlElement {

    private Optional<PlantUmlElement> hasName = Optional.empty();

    public PrimitiveType(Variable variable) {
        super(variable, false);
    }

    @Override
    protected String getHighestRankingName() {
        String nameSection = variable.transformToGui();
        if (hasName.isPresent()) {
            nameSection = hasName.get().toString();
        }
        return nameSection;
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("class ");
        builder.append(buildNameSection());
        builder.append(variable.getName());
        builder.append(" <<primitive>>");
        builder.append(buildColorSection());
        return builder.toString();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        if ("hasName".equals(property)) {
            this.hasName = Optional.of(object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String buildNameSection() {
        if (hasName.isEmpty()) {
            return "";
        }
        return "\"" + hasName.get() + "\" as ";
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String buildColorSection() {
        if (colorState != ColorState.NEUTRAL) {
            return " " + colorState.getColorCode();
        }
        return "";
    }
}
