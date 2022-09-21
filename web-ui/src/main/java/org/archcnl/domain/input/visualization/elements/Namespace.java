package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Namespace extends NamespaceContent {

    private List<NamespaceContent> namespaceContains = new ArrayList<>();

    public Namespace(Variable variable) {
        super(variable);
    }

    @Override
    protected String buildNameSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(getElementIdentifier() + " ");
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(variable.getName());
        return builder.toString();
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
    protected void increaseIndentation() {
        super.increaseIndentation();
        namespaceContains.forEach(NamespaceContent::increaseIndentation);
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
                break;
            case "namespaceContains":
                NamespaceContent content = (NamespaceContent) object;
                content.parentIsFound();
                addContainedElement(content);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String getHighestRankingName() {
        if (hasName.isPresent()) {
            return hasName.get();
        }
        return variable.transformToGui();
    }
}
