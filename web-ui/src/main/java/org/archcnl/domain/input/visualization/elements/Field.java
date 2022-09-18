package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class Field implements PlantUmlElement {

    private static final String FIELD_MODIFIER = "{field}";
    private static final String ABSTRACT_MODIFIER = "{abstract}";
    private static final String STATIC_MODIFIER = "{static}";

    private Variable variable;
    private Optional<String> hasName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    private Optional<AnnotationInstance> hasAnnotationInstance = Optional.empty();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();

    public Field(Variable variable) {
        this.variable = variable;
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

    private String buildTypeSection() {
        if (hasDeclaredType.isPresent()) {
            return " : " + hasDeclaredType.get().getTypeRepresentation();
        }
        return "";
    }

    private String buildAnnotationSection() {
        if (hasAnnotationInstance.isPresent()) {
            return " " + hasAnnotationInstance.get().buildPlantUmlCode();
        }
        return "";
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
                break;
            case "hasAnnotationInstance":
                this.hasAnnotationInstance = Optional.of((AnnotationInstance) object);
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
}
