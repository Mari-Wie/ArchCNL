package org.archcnl.domain.input.visualization.elements;

import java.util.List;

public abstract class NamespaceContent extends NamedEntity implements PlantUmlElement {

    private static final String ONE_INDENTATION_LEVEL = "/t";

    protected int indentationDepth = 0;

    protected NamespaceContent(String variableName) {
        super(variableName);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        if (!isBodyEmpty()) {
            builder.append(buildBodySection());
        }
        return builder.toString();
    }

    private String buildNameSection() {
        String identationPrefix = ONE_INDENTATION_LEVEL.repeat(indentationDepth);

        StringBuilder builder = new StringBuilder();
        builder.append(identationPrefix);
        builder.append(getElementIdentifier() + " ");
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(getVariableName());
        return builder.toString();
    }

    protected abstract String getElementIdentifier();

    protected abstract boolean isBodyEmpty();

    private String buildBodySection() {
        String identationPrefix = ONE_INDENTATION_LEVEL.repeat(indentationDepth);

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

    protected abstract void increaseIndentation();
}
