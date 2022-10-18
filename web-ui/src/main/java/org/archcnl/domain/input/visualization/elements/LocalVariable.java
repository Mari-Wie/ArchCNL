package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class LocalVariable extends PlantUmlElement {

    private Optional<String> hasName = Optional.empty();
    private Optional<DeclaredType> hasDeclaredType = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();

    public LocalVariable(Variable variable) {
        super(variable, false);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("note as " + variable.getName() + "\n");
        builder.append("===LocalVariable\n");
        builder.append(buildModifierSection());
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        builder.append("\nend note");
        return builder.toString();
    }

    private String buildModifierSection() {
        return modifierContainer.isFinal() ? "final " : "";
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

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
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
        throw new UnsupportedOperationException();
    }
}
