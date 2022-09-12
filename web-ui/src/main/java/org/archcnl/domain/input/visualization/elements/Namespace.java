package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Namespace extends NamespaceContent implements PlantUmlElement {

    private List<NamespaceContent> namespaceContains = new ArrayList<>();

    public Namespace(String variableName) {
        super(variableName);
    }

    @Override
    protected List<String> buildBodySectionContentLines() {
        return namespaceContains.stream()
                .map(NamespaceContent::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    public void addContainedElement(NamespaceContent element) {
        element.increaseIndentation();
        namespaceContains.add(element);
    }

    @Override
    protected String getElementIdentifier() {
        return "folder";
    }

    @Override
    protected boolean isBodyEmpty() {
        return namespaceContains.isEmpty();
    }

    @Override
    protected void increaseIndentation() {
        indentationDepth++;
        namespaceContains.forEach(NamespaceContent::increaseIndentation);
    }
}
