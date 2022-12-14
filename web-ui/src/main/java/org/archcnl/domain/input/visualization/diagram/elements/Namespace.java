package org.archcnl.domain.input.visualization.diagram.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Namespace extends NamespaceContent {

    private List<PlantUmlElement> namespaceContains = new ArrayList<>();

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
                .map(PlantUmlElement::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    @Override
    protected String getElementIdentifier() {
        return "folder";
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of(object);
                break;
            case "namespaceContains":
                object.setParent(this);
                namespaceContains.add(object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String getHighestRankingName() {
        if (hasName.isPresent()) {
            return hasName.get().toString();
        }
        return variable.transformToGui();
    }

    @Override
    protected String buildParentSection() {
        return "";
    }
}
