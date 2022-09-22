package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.PlantUmlPart;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public abstract class PlantUmlElement implements PlantUmlPart {

    private Optional<PlantUmlElement> parent = Optional.empty();
    private final boolean requiresParent;
    protected final Variable variable;

    protected PlantUmlElement(Variable variable, boolean requiresParent) {
        this.variable = variable;
        this.requiresParent = requiresParent;
    }

    public boolean hasRequiredParent() {
        return !requiresParent || parent.isPresent();
    }

    public abstract void setProperty(String property, Object object)
            throws PropertyNotFoundException;

    public String getIdentifier() throws MappingToUmlTranslationFailedException {
        if (!this.requiresParent) {
            return variable.getName();
        }
        PlantUmlElement current = this;
        while (current.requiresParent) {
            if (current.parent.isEmpty()) {
                throw new MappingToUmlTranslationFailedException(
                        variable.getName() + " is missing a required parent");
            }
            current = current.parent.get();
        }
        return current.getIdentifier() + "::" + removeNonAlphaNumericSymbols(buildNameSection());
    }

    private String removeNonAlphaNumericSymbols(String string) {
        return string.replaceAll("[^A-Za-z0-9]", "");
    }

    public boolean hasParentBeenFound() {
        return parent.isPresent();
    }

    public void setParent(PlantUmlElement parent) {
        this.parent = Optional.of(parent);
    }

    public Optional<PlantUmlElement> getParent() {
        return parent;
    }

    protected abstract String buildNameSection();
}
