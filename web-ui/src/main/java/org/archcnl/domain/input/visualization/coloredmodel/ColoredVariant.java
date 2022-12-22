package org.archcnl.domain.input.visualization.coloredmodel;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;

public class ColoredVariant {

    private List<ColoredTriplet> triplets;

    public ColoredVariant(List<ColoredTriplet> triplets) {
        this.triplets = triplets;
    }

    public List<ColoredTriplet> getTriplets() {
        return triplets;
    }

    public static ColoredVariant fromAndTriplets(AndTriplets variant) {
        var triplets =
                variant.getTriplets().stream()
                        .map(ColoredTriplet::new)
                        .collect(Collectors.toList());
        return new ColoredVariant(triplets);
    }

    public AndTriplets toAndTriplets() {
        return new AndTriplets(
                triplets.stream().map(Triplet.class::cast).collect(Collectors.toList()));
    }
}
