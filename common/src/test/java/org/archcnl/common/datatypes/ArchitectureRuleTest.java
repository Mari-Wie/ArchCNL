package org.archcnl.common.datatypes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArchitectureRuleTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testEqualsAndHashCode() {
        ArchitectureRule r1 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, "file1");
        ArchitectureRule r2 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, "file1");
        ArchitectureRule r3 = new ArchitectureRule(1, "not test", RuleType.AT_MOST, "file2");

        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertNotEquals(r1, null);
        assertNotEquals(r1, r3);

        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
