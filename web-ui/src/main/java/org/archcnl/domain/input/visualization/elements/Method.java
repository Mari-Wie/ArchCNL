package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Method extends PlantUmlElement {

    private static final String METHOD_MODIFIER = "{method}";
    private static final String ABSTRACT_MODIFIER = "{abstract}";
    private static final String STATIC_MODIFIER = "{static}";

    private Optional<String> hasName = Optional.empty();
    private List<Parameter> definesParameters = new ArrayList<>();
    private ModifierContainer modifierContainer = new ModifierContainer();
    private List<AnnotationInstance> hasAnnotationInstance = new ArrayList<>();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();
    private boolean isConstructor = false;

    public Method(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildModifierSection());
        if (isConstructor) {
            builder.append("<<Create>> ");
        }
        builder.append(buildNameSection());
        if (!definesParameters.isEmpty()) {
            builder.append(buildParameterSection());
        }
        builder.append(buildTypeSection());
        builder.append(buildAnnotationSection());
        return builder.toString();
    }

    private String buildModifierSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(METHOD_MODIFIER + " ");
        if (modifierContainer.isAbstract()) {
            builder.append(ABSTRACT_MODIFIER + " ");
        }
        if (modifierContainer.isStatic()) {
            builder.append(STATIC_MODIFIER + " ");
        }
        builder.append(modifierContainer.getVisibilityPrefix());
        return builder.toString();
    }

    private String buildNameSection() {
        return hasName.isPresent() ? hasName.get() : variable.transformToGui();
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

    private String buildTypeSection() {
        if (hasDeclaredType.isPresent()) {
            return " : " + hasDeclaredType.get().getTypeRepresentation();
        }
        return "";
    }

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " "
                + hasAnnotationInstance.stream()
                        .map(AnnotationInstance::buildPlantUmlCode)
                        .collect(Collectors.joining());
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
                break;
            case "hasAnnotationInstance":
                AnnotationInstance instance = (AnnotationInstance) object;
                instance.parentIsFound();
                this.hasAnnotationInstance.add(instance);
                break;
            case "hasDeclaredType":
                this.hasDeclaredType = Optional.of((DeclaredType) object);
                break;
            case "hasModifier":
                this.modifierContainer.setModifier((String) object);
                break;
            case "definesParameters":
                Parameter parameter = (Parameter) object;
                parameter.parentIsFound();
                this.definesParameters.add(parameter);
                break;
            case "isConstructor":
                this.isConstructor = (boolean) object;
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }
}
