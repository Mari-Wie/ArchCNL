package org.archcnl.webui.mappings;

import java.util.LinkedList;
import java.util.List;

public class AndTriplets {

    private List<Triplet> triplets;

    public AndTriplets() {
        triplets = new LinkedList<>();
    }

    public AndTriplets(List<Triplet> triplets) {
        this();
        this.triplets = triplets;
    }

    public List<Triplet> getTriplets() {
        return triplets;
    }

    public void addTriplet(Triplet triplet) {
        triplets.add(triplet);
    }
}
