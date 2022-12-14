package org.archcnl.domain.input.visualization.diagram.elements.containers;

import java.util.Optional;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class ModifierContainer {

    private Optional<VisibilityModifier> visibilityModifer = Optional.empty();
    private boolean isAbstract = false;
    private boolean isStatic = false;
    // The "final" keyword can't be correctly visualized in PlantUML
    // as the ontology doesn't provide access to the default value
    private boolean isFinal = false;

    public void setModifier(String modifier) throws PropertyNotFoundException {
        // toUpperCase() is needed as "private" can't be used an enum member.
        modifier = modifier.toUpperCase();
        if ("ABSTRACT".equals(modifier)) {
            isAbstract = true;
        } else if ("STATIC".equals(modifier)) {
            isStatic = true;
        } else if ("FINAL".equals(modifier)) {
            isFinal = true;
        } else if (isVisibilityModifier(modifier)) {
            visibilityModifer = Optional.of(VisibilityModifier.valueOf(modifier));
        } else {
            throw new PropertyNotFoundException(modifier + " couldn't be set");
        }
    }

    private boolean isVisibilityModifier(String modifier) {
        return EnumUtils.isValidEnum(VisibilityModifier.class, modifier);
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getVisibilityPrefix() {
        if (visibilityModifer.isPresent()) {
            return visibilityModifer.get().getVisibilityPrefix();
        }
        return "";
    }
}
