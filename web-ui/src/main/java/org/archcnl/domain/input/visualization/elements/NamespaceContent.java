package org.archcnl.domain.input.visualization.elements;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public abstract class NamespaceContent extends PlantUmlElement {

    private static final String ONE_INDENTATION_LEVEL = "/t";

    private int indentationDepth = 0;
    protected Optional<String> hasName = Optional.empty();

    protected NamespaceContent(Variable variable) {
        super(variable, false);
    }

    @Override
    public String buildPlantUmlCode() {
        String identationPrefix = ONE_INDENTATION_LEVEL.repeat(indentationDepth);
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection(identationPrefix));
        builder.append(buildBodySection(identationPrefix));
        return builder.toString();
    }

    private String buildNameSection(String identationPrefix) {
        StringBuilder builder = new StringBuilder();
        builder.append(identationPrefix);
        builder.append(getElementIdentifier() + " ");
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(variable.getName());
        builder.append(buildAnnotationSection());
        return builder.toString();
    }

    protected abstract String buildAnnotationSection();

    protected abstract String getHighestRankingName();

    protected abstract String getElementIdentifier();

    private String buildBodySection(String identationPrefix) {
        StringBuilder builder = new StringBuilder();
        builder.append(" {\n");
        for (String contentLine : buildBodySectionContentLines()) {
            builder.append(identationPrefix + ONE_INDENTATION_LEVEL);
            builder.append(contentLine);
            builder.append("\n");
        }
        builder.append(identationPrefix);
        builder.append("}");
        return builder.toString();
    }

    protected abstract List<String> buildBodySectionContentLines();

    protected void increaseIndentation() {
        indentationDepth++;
    }
}
