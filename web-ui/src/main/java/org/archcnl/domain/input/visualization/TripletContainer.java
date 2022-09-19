package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.visualization.helpers.PlantUmlElementProperty;

public class TripletContainer {

    private List<Triplet> elementPropertyTriplets;
    private List<Triplet> betweenElementTriplets;

    public TripletContainer(List<Triplet> triplets) {
        setElementPropertyTriplets(triplets);
        setObjectPropertyTriplets(triplets);
    }

    private void setElementPropertyTriplets(List<Triplet> triplets) {
        elementPropertyTriplets =
                triplets.stream()
                        .filter(t -> isElementProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setObjectPropertyTriplets(List<Triplet> triplets) {
        betweenElementTriplets =
                triplets.stream()
                        .filter(t -> !isElementProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private boolean isElementProperty(Relation relation) {
        return EnumUtils.isValidEnum(PlantUmlElementProperty.class, relation.getName());
    }

    public List<Triplet> getElementPropertyTriplets() {
        return elementPropertyTriplets;
    }
}
