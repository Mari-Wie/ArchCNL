package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.PlantUmlBlock;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;

public abstract class PlantUmlElement implements PlantUmlBlock {

    protected Optional<PlantUmlElement> parent = Optional.empty();
    private final boolean requiresParent;
    protected final Variable variable;

    protected PlantUmlElement(Variable variable, boolean requiresParent) {
        this.variable = variable;
        this.requiresParent = requiresParent;
    }

    private boolean hasParentIfRequired() {
        return !requiresParent || parent.isPresent();
    }

    public PlantUmlBlock createRequiredParentOrReturnSelf() {
        if (hasParentIfRequired()) {
            return this;
        }
        try {
            String parentName = NamePicker.getNextGeneratedName();
            PlantUmlElement createdParent = createParent(parentName);
            setParent(createdParent);
            return createdParent.createRequiredParentOrReturnSelf();
        } catch (PropertyNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<String> getIdentifiers() {
        if (!this.requiresParent) {
            return Arrays.asList(variable.getName());
        }
        PlantUmlElement current = this;
        while (current.requiresParent) {
            if (current.parent.isEmpty()) {
                return null;
            }
            current = current.parent.get();
        }
        List<String> identifiers = new ArrayList<>();
        for (String parentIdentifier : current.getIdentifiers()) {
            identifiers.add(
                    parentIdentifier + "::" + removeNonAlphaNumericSymbols(buildNameSection()));
        }
        return identifiers;
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

    protected abstract PlantUmlElement createParent(String parentName)
            throws PropertyNotFoundException;
}
