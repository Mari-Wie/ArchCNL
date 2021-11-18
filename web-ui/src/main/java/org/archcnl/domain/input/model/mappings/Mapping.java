package org.archcnl.domain.input.model.mappings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.Triplet;

public abstract class Mapping {

    // The "when" part
    private List<AndTriplets> orStatements;

    protected Mapping(List<AndTriplets> whenTriplets) {
        this.orStatements = whenTriplets;
    }

    public List<AndTriplets> getWhenTriplets() {
        return orStatements;
    }

    public abstract Triplet getThenTriplet();

    public abstract String getMappingNameRepresentation();

    public List<String> toStringRepresentation() {
        if (orStatements.isEmpty()) {
            return new LinkedList<>(
                    Arrays.asList(
                            new AndTriplets()
                                    .toStringRepresentation(
                                            getMappingNameRepresentation(), getThenTriplet())));
        } else {
            return orStatements.stream()
                    .map(
                            andTriplets ->
                                    andTriplets.toStringRepresentation(
                                            getMappingNameRepresentation(), getThenTriplet()))
                    .collect(Collectors.toList());
        }
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
        return toStringRepresentation().stream()
                .allMatch(s -> otherMapping.toStringRepresentation().contains(s));
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
