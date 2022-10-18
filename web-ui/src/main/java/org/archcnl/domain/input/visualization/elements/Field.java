package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Field extends PlantUmlElement {

    private static final String FIELD_MODIFIER = "{field}";
    private static final String STATIC_MODIFIER = "{static}";

    private Optional<String> hasName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    private List<AnnotationInstance> hasAnnotationInstance = new ArrayList<>();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();

    public Field(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildModifierSection());
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        builder.append(buildAnnotationSection());
        return builder.toString();
    }

    private String buildModifierSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIELD_MODIFIER + " ");
        if (modifierContainer.isStatic()) {
            builder.append(STATIC_MODIFIER + " ");
        }
        builder.append(modifierContainer.getVisibilityPrefix());
        return builder.toString();
    }

    @Override
    protected String buildNameSection() {
        return hasName.isPresent() ? hasName.get() : variable.transformToGui();
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
                instance.setParent(this);
                this.hasAnnotationInstance.add(instance);
                break;
            case "hasDeclaredType":
                this.hasDeclaredType = Optional.of((DeclaredType) object);
                break;
            case "hasModifier":
                this.modifierContainer.setModifier((String) object);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        FamixClass famixClass = new FamixClass(new Variable(parentName));
        famixClass.setProperty("definesAttribute", this);
        return famixClass;
    }
}
