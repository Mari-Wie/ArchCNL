package org.archcnl.domain.input.visualization.elements;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
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

    protected abstract String buildNameSection();

    protected abstract String getHighestRankingName();

    protected abstract String getElementIdentifier();

    private String buildBodySection() {
        StringBuilder builder = new StringBuilder();
        builder.append(" {\n");
        for (String contentLine : buildBodySectionContentLines()) {
            builder.append(contentLine);
            builder.append("\n");
        }
        builder.append("}");
        return builder.toString();
    }

    protected abstract List<String> buildBodySectionContentLines();

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }
}
