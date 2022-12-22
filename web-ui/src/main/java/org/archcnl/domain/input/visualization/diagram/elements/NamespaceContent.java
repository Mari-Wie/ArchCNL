package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public abstract class NamespaceContent extends PlantUmlElement {

    protected Optional<PlantUmlElement> hasName = Optional.empty();

    protected NamespaceContent(Variable variable) {
        super(variable, false);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        builder.append(buildBodySection());
        return builder.toString();
    }

    private String buildBodySection() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildColorSection());
        builder.append(buildParentSection());
        builder.append(" {\n");
        for (String contentLine : buildBodySectionContentLines()) {
            builder.append(contentLine);
            builder.append("\n");
        }
        builder.append("}");
        return builder.toString();
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

    protected abstract List<String> buildBodySectionContentLines();

    protected abstract String buildNameSection();

    protected abstract String buildParentSection();

    protected abstract String getElementIdentifier();
}
