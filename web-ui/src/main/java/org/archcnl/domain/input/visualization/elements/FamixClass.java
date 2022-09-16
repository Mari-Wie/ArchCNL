package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FamixClass extends NamespaceContent implements PlantUmlElement {

    private boolean isInterface = false;
    private boolean isAbstract = false;
    private VisibilityModifier visibility;
    private List<Field> definesAttributes = new ArrayList<>();
    private List<Method> definesMethods = new ArrayList<>();

    public FamixClass(String variableName) {
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
    protected void increaseIndentation() {
        indentationDepth++;
    }
}
