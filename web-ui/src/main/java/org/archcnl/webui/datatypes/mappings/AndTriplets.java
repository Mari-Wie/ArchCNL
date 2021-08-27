package org.archcnl.webui.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;

public class AndTriplets {

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

    public String toStringRepresentation(String mappingName, Triplet thenTriplet) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mappingName + ": ");
        for (Triplet triplet : triplets) {
            stringBuilder.append(triplet.toStringRepresentation() + " ");
        }
        stringBuilder.append("-> ");
        stringBuilder.append(thenTriplet.toStringRepresentation());
        return stringBuilder.toString();
    }
}
