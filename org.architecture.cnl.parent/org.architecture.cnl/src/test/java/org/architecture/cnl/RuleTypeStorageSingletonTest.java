package org.architecture.cnl;

import static org.junit.Assert.*;

import org.archcnl.common.datatypes.RuleType;
import org.junit.Test;

public class RuleTypeStorageSingletonTest {

    @Test
    public void givenRuleTypeStorage_whenStoringRule_thenRulePresent() {
        // given
        RuleTypeStorageSingleton instance = RuleTypeStorageSingleton.getInstance();
        // when
        instance.storeTypeOfRule(0, RuleType.EXISTENTIAL);
        // then
        assertEquals(RuleType.EXISTENTIAL, instance.retrieveTypeOfRule(0));
    }
}
