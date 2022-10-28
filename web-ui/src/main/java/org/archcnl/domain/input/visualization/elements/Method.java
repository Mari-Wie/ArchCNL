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

    private Optional<PlantUmlElement> hasName = Optional.empty();
    private List<PlantUmlElement> definesParameter = new ArrayList<>();
    private ModifierContainer modifierContainer = new ModifierContainer();
    private List<PlantUmlElement> hasAnnotationInstance = new ArrayList<>();
    private Optional<PlantUmlElement> hasDeclaredType = Optional.empty();
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
        builder.append(buildParameterSection());
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

    @Override
    protected String buildNameSection() {
        return hasName.isPresent() ? hasName.get().toString() : variable.transformToGui();
    }

    private String buildParameterSection() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        // (a:String, b:String, c:String, d:int)
        String joinedParameters =
                definesParameter.stream()
                        .map(PlantUmlElement::buildPlantUmlCode)
                        .collect(Collectors.joining(", "));
        builder.append(joinedParameters);
        builder.append(")");
        return builder.toString();
    }

    private String buildTypeSection() {
        if (hasDeclaredType.isPresent()) {
            return " : " + hasDeclaredType.get().getHighestRankingName();
        }
        return "";
    }

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " "
                + hasAnnotationInstance.stream()
                        .map(PlantUmlElement::buildPlantUmlCode)
                        .collect(Collectors.joining());
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of(object);
                break;
            case "hasAnnotationInstance":
                object.setParent(this);
                this.hasAnnotationInstance.add(object);
                break;
            case "hasDeclaredType":
                this.hasDeclaredType = Optional.of(object);
                break;
            case "hasModifier":
                this.modifierContainer.setModifier(object.toString());
                break;
            case "definesParameter":
                object.setParent(this);
                this.definesParameter.add(object);
                break;
            case "isConstructor":
                this.isConstructor = ((BooleanElement) object).getValue();
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        FamixClass parent = new FamixClass(new Variable(parentName));
        parent.setProperty("definesMethod", this);
        return parent;
    }
}
