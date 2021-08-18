package org.archcnl.webui.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Mapping {

    // The "when" part
    private List<AndTriplets> orStatements;
    private VariableManager variableManager;

    protected Mapping(List<AndTriplets> whenTriplets) {
        this.orStatements = whenTriplets;
        this.variableManager = new VariableManager();
    }

    public List<AndTriplets> getWhenTriplets() {
        return orStatements;
    }

    public VariableManager getVariableManager() {
        return variableManager;
    }

    public abstract String getName();

    public abstract Triplet getThenTriplet();

    public abstract String getMappingNameRepresentation();

    public List<String> toStringRepresentation() {
        List<String> result = new LinkedList<>();
        for (AndTriplets andTriplets : orStatements) {
            result.add(
                    andTriplets.toStringRepresentation(
                            getMappingNameRepresentation(), getThenTriplet()));
        }
        return result;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Mapping)) {
            return false;
        }
        Mapping otherMapping = (Mapping) o;
        return toStringRepresentation().equals(otherMapping.toStringRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
