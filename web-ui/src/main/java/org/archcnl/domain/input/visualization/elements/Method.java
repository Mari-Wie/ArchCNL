package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Method extends ClassContent implements PlantUmlElement {

    private static final String METHOD_MODIFIER = "{method}";

    private List<Parameter> definesParameters = new ArrayList<>();

    protected Method(String variableName) {
        super(variableName);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildModifierSection());
        builder.append(buildNameSection());
        builder.append(buildParameterSection());
        builder.append(buildTypeSection());
        return builder.toString();
    }

    private String buildParameterSection() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        // (a:String, b:String, c:String, d:int)
        String joinedParameters =
                definesParameters.stream()
                        .map(Parameter::buildPlantUmlCode)
                        .collect(Collectors.joining(", "));
        builder.append(joinedParameters);
        builder.append(")");
        return builder.toString();
    }

    @Override
    protected String getContentTypeModifier() {
        return METHOD_MODIFIER;
    }
}
