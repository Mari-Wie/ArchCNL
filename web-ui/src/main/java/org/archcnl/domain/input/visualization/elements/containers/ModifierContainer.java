package org.archcnl.domain.input.visualization.elements.containers;

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
        if ("abstract".equals(modifier)) {
            isAbstract = true;
        } else if ("static".equals(modifier)) {
            isStatic = true;
        } else if ("final".equals(modifier)) {
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

    public String getVisibilityPrefix() {
        if (visibilityModifer.isPresent()) {
            return visibilityModifer.get().getVisibilityPrefix();
        }
        return "";
    }
}