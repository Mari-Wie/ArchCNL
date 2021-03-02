package org.architecture.cnl;

import static org.junit.Assert.*;

import org.archcnl.common.datatypes.RuleType;
import org.junit.Test;

public class RuleTypeStorageSingletonTest {

	@Test
	public void test() {
		RuleTypeStorageSingleton instance = RuleTypeStorageSingleton.getInstance();
		
		instance.storeTypeOfRule(0, RuleType.EXISTENTIAL);
		
		assertEquals(RuleType.EXISTENTIAL, instance.retrieveTypeOfRule(0));
	}

}
