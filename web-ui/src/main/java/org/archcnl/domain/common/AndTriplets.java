package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AndTriplets implements FormattedQueryDomainObject {

    private List<Triplet> triplets;

    public AndTriplets() {
        triplets = new LinkedList<>();
    }

    public AndTriplets(List<Triplet> triplets) {
        this.triplets = triplets;
    }

    public List<Triplet> getTriplets() {
        return triplets;
    }

    public void addTriplet(Triplet triplet) {
        triplets.add(triplet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(triplets);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AndTriplets) {
            final AndTriplets that = (AndTriplets) obj;
            return Objects.equals(this.triplets, that.triplets);
        }
        return false;
    }

    @Override
    public String transformToSparqlQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        triplets.stream().forEach(t -> stringBuilder.append(" " + t.transformToSparqlQuery()));
        return stringBuilder.toString();
    }

    public String transformToGui(String tab, String lineSeparator) {
        StringBuilder stringBuilder = new StringBuilder();
        triplets.stream()
                .forEach(t -> stringBuilder.append(tab + tab + t.transformToGui() + lineSeparator));
        return stringBuilder.toString();
    }

    public String transformToAdoc(String mappingName, Triplet thenTriplet) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mappingName + ": ");
        for (Triplet triplet : triplets) {
            stringBuilder.append(triplet.transformToAdoc() + " ");
        }
        stringBuilder.append("-> ");
        stringBuilder.append(thenTriplet.transformToAdoc());
        return stringBuilder.toString();
    }
}
