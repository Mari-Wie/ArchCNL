package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Class extends NamespaceContent implements PlantUmlElement {

    private boolean isInterface = false;
    private List<Field> definesAttributes = new ArrayList<>();
    private List<Method> definesMethods = new ArrayList<>();

    public Class(String variableName) {
        super(variableName);
    }

    @Override
    protected List<String> buildBodySectionContentLines() {
        List<String> bodyContentLines = new ArrayList<>();
        bodyContentLines.addAll(buildAttributeLines());
        bodyContentLines.addAll(buildMethodLines());
        return bodyContentLines;
    }

    private List<String> buildAttributeLines() {
        return definesAttributes.stream()
                .map(Field::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    private List<String> buildMethodLines() {
        return definesMethods.stream().map(Method::buildPlantUmlCode).collect(Collectors.toList());
    }

    @Override
    protected String getElementIdentifier() {
        return isInterface ? "interface" : "class";
    }

    @Override
    protected boolean isBodyEmpty() {
        return definesAttributes.isEmpty() && definesMethods.isEmpty();
    }

    @Override
    protected void increaseIndentation() {
        indentationDepth++;
    }
}
