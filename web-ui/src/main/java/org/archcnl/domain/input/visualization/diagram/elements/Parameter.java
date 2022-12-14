package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public class Parameter extends PlantUmlElement {

    private Optional<PlantUmlElement> hasName = Optional.empty();
    private Optional<PlantUmlElement> hasDeclaredType = Optional.empty();

    public Parameter(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildColorSection());
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        builder.append(closeColorSection());
        return builder.toString();
    }

    @Override
    protected String buildNameSection() {
        return hasName.isPresent() ? hasName.get().toString() : variable.transformToGui();
    }

    private String buildTypeSection() {
        StringBuilder builder = new StringBuilder();
        if (hasDeclaredType.isPresent()) {
            builder.append(":");
            builder.append(hasDeclaredType.get().getHighestRankingName());
        }
        return builder.toString();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of(object);
                break;
            case "hasDeclaredType":
                this.hasDeclaredType = Optional.of(object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        Method parent = new Method(new Variable(parentName));
        parent.setProperty("definesParameter", this);
        return parent;
    }

    @Override
    public List<String> getIdentifiers() {
        return parent.get().getIdentifiers();
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
