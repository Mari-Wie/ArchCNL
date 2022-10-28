package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class FamixClass extends ClassOrEnum implements FamixType {

    private boolean isInterface = false;
    private List<PlantUmlElement> inheritsFrom = new ArrayList<>();

    public FamixClass(Variable variable) {
        super(variable);
    }

    @Override
    protected String getElementIdentifier() {
        if (isInterface) {
            return "interface";
        } else if (modifierContainer.isAbstract()) {
            return "abstract";
        }
        return "class";
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        if ("isInterface".equals(property)) {
            this.isInterface = ((BooleanElement) object).getValue();
        } else if ("inheritsFrom".equals(property)) {
            inheritsFrom.add(object);
        } else {
            super.setProperty(property, object);
        }
    }

    @Override
    protected String buildParentSection() {
        if (inheritsFrom.isEmpty()) {
            return "";
        }
        List<FamixClass> classParents =
                inheritsFrom.stream()
                        .filter(FamixClass.class::isInstance)
                        .map(FamixClass.class::cast)
                        .filter(c -> !c.isInterface)
                        .collect(Collectors.toList());
        List<FamixClass> interfaceParents =
                inheritsFrom.stream()
                        .filter(FamixClass.class::isInstance)
                        .map(FamixClass.class::cast)
                        .filter(c -> c.isInterface)
                        .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        if (!classParents.isEmpty()) {
            builder.append(" extends ");
            builder.append(
                    classParents.stream()
                            .map(c -> c.variable.getName())
                            .collect(Collectors.joining(", ")));
        }
        if (!interfaceParents.isEmpty()) {
            builder.append(" implements ");
            builder.append(
                    interfaceParents.stream()
                            .map(c -> c.variable.getName())
                            .collect(Collectors.joining(", ")));
        }
        return builder.toString();
    }
}
