package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Class extends NamedEntity implements PlantUmlElement {

    private boolean isInterface = false;
    private List<Field> definesAttributes = new ArrayList<>();
    private List<Method> definesMethods = new ArrayList<>();

    public Class(String variableName) {
        super(variableName);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        builder.append(buildClassBodySection());
        return builder.toString();
    }

    private String buildNameSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(getElementIdentifier() + " "); //
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(getVariableName());
        return builder.toString();
    }

    private String getElementIdentifier() {
        return isInterface ? "interface" : "class";
    }

    private String buildClassBodySection() {
        StringBuilder builder = new StringBuilder();
        builder.append(" {\n");
        builder.append(buildAttributeSection());
        builder.append(buildMethodSection());
        builder.append("}");
        return builder.toString();
    }

    private String buildAttributeSection() {
        return definesAttributes.stream()
                .map(field -> "\t" + field.buildPlantUmlCode() + "\n")
                .collect(Collectors.joining());
    }

    private String buildMethodSection() {
        return definesMethods.stream()
                .map(method -> "\t" + method.buildPlantUmlCode() + "\n")
                .collect(Collectors.joining());
    }
}
