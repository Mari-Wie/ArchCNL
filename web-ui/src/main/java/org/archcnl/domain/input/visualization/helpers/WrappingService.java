package org.archcnl.domain.input.visualization.helpers;

import java.util.Arrays;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;

public class WrappingService {

    private WrappingService() {}

    public static List<AndTriplets> wrapMapping(Triplet thenTriplet) {
        // This is a little trick that enables the visualization of mappings
        // with multiple when variants.
        // The mapping is flattened as: (thenTriplet) -> (thenTriplet)
        // Without this, later variants would not contain the same variables that
        // are used in the thenTriplet (as the flattener chooses unique variables),
        // making a visualization impossible.
        // This workaround requires the CustomConept/CustomRelation in the thenTriplet
        // to have a reference to its mapping.
        return Arrays.asList(new AndTriplets(Arrays.asList(thenTriplet)));
    }
}
