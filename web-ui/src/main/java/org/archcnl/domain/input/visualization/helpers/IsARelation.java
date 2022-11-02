package org.archcnl.domain.input.visualization.helpers;

import java.util.Collections;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;

public class IsARelation extends FamixRelation {

    public IsARelation() {
        super(
                "isA",
                "Represents a is-a relationship between two classes",
                Collections.singleton(new FamixConcept("FamixClass", "")),
                Collections.singleton(new FamixConcept("FamixClass", "")));
    }
}
