package org.archcnl.input.datatypes.mappings;

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

    public void addAndTriplets(AndTriplets andTriplets) {
        orStatements.add(andTriplets);
    }

    public void addAllAndTriplets(List<AndTriplets> andTripletsList) {
        orStatements.addAll(andTripletsList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Mapping)) {
            return false;
        }
        Mapping otherMapping = (Mapping) o;
        if (toStringRepresentation().size() != otherMapping.toStringRepresentation().size()) {
            return false;
        }
        for (String s : toStringRepresentation()) {
            if (!otherMapping.toStringRepresentation().contains(s)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
