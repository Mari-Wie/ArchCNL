package org.archcnl.domain.common;

import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableTest {

    private ConceptManager conceptManager;

    @BeforeEach
    void setup() {
        conceptManager = new ConceptManager();
    }

    @Test
    void givenVariable_whenRefineDynamicTypes_thenExpectedDynamicTypes() {
        // given
        Variable variable = new Variable("testVariable");

        // when
        Set<ActualObjectType> types = new LinkedHashSet<>();
        types.add(new FamixConcept("FamixClass", ""));
        types.add(new StringValue(""));
        variable.refineDynamicTypes(types, conceptManager);

        // then
        Assertions.assertFalse(variable.hasConflictingDynamicTypes());
        Set<ActualObjectType> dynamicTypes = variable.getDynamicTypes();
        Assertions.assertEquals(2, dynamicTypes.size());
        Assertions.assertTrue(dynamicTypes.containsAll(types));
    }

    @Test
    void givenVariableWithDynamicTypes_whenRefineDynamicTypes_thenOneRemainingType() {
        // given
        Variable variable = new Variable("testVariable");
        Set<ActualObjectType> initialTypes = new LinkedHashSet<>();
        initialTypes.add(new FamixConcept("FamixClass", ""));
        initialTypes.add(new StringValue(""));
        variable.refineDynamicTypes(initialTypes, conceptManager);

        // when
        Set<ActualObjectType> types = new LinkedHashSet<>();
        types.add(new FamixConcept("FamixClass", ""));
        types.add(new FamixConcept("Namespace", ""));
        types.add(new BooleanValue(false));
        variable.refineDynamicTypes(types, conceptManager);

        // then
        Assertions.assertFalse(variable.hasConflictingDynamicTypes());
        Set<ActualObjectType> dynamicTypes = variable.getDynamicTypes();
        Assertions.assertEquals(1, dynamicTypes.size());
        Assertions.assertTrue(dynamicTypes.contains(new FamixConcept("FamixClass", "")));
    }

    @Test
    void givenVariableWithDynamicTypes_whenRefineDynamicTypes_thenConflicDetected() {
        // given
        Variable variable = new Variable("testVariable");
        Set<ActualObjectType> initialTypes = new LinkedHashSet<>();
        initialTypes.add(new FamixConcept("FamixClass", ""));
        initialTypes.add(new StringValue(""));
        variable.refineDynamicTypes(initialTypes, conceptManager);

        // when
        Set<ActualObjectType> types = new LinkedHashSet<>();
        types.add(new FamixConcept("Namespace", ""));
        variable.refineDynamicTypes(types, conceptManager);

        // then
        Assertions.assertTrue(variable.hasConflictingDynamicTypes());
        Assertions.assertEquals(0, variable.getDynamicTypes().size());
    }
}
