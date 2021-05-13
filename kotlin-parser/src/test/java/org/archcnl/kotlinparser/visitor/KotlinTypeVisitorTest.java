package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class KotlinTypeVisitorTest {
    @Test
    @Disabled
    void testKotlinTypeOfComplexClass() throws IOException {
        var treeOfComplexClass = TestHelper.getKotlinFileContextFromFile("ComplexClass.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor();
        kotlinTypeVisitor.visit(treeOfComplexClass);
    }
}
