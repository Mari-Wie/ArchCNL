package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.junit.jupiter.api.Test;

class KotlinTypeVisitorTest {
    @Test
    void testKotlinTypeOfComplexClass() throws IOException {
        var treeOfComplexClass = TestHelper.getKotlinFileContextFromFile("ComplexClass.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor();
        kotlinTypeVisitor.visit(treeOfComplexClass);

        assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);

        assertEquals("ComplexClass", definedType.getSimpleName());
        assertEquals("example.ComplexClass", definedType.getName());
    }

    @Test
    void testKotlinTypeOfClassInSubpackage() throws IOException {
        var treeOfComplexClass =
                TestHelper.getKotlinFileContextFromFile("subpackage", "ClassInSubpackage.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor();
        kotlinTypeVisitor.visit(treeOfComplexClass);

        assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);

        assertEquals("ClassInSubpackage", definedType.getSimpleName());
        assertEquals("example.subpackage.ClassInSubpackage", definedType.getName());
    }

    @Test
    void testKotlinTypeOfClassWithInnerClass() throws IOException {
        var treeOfComplexClass = TestHelper.getKotlinFileContextFromFile("ClassWithInnerClass.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor();
        kotlinTypeVisitor.visit(treeOfComplexClass);

        assertEquals(3, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType outerClass = kotlinTypeVisitor.getDefinedTypes().get(0);
        DefinedType innerClass = kotlinTypeVisitor.getDefinedTypes().get(1);
        DefinedType insideClass = kotlinTypeVisitor.getDefinedTypes().get(2);

        assertEquals("ClassWithInnerClass", outerClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass", outerClass.getName());

        assertEquals("InnerClass", innerClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass.InnerClass", innerClass.getName());

        assertEquals("InsideClass", insideClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass.InnerClass.InsideClass", insideClass.getName());
    }
}
