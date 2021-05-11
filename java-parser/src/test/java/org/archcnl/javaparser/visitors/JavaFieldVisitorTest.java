package org.archcnl.javaparser.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Field;
import org.junit.Before;
import org.junit.Test;

public class JavaFieldVisitorTest {

    private JavaFieldVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new JavaFieldVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/"));

        StaticJavaParser.setConfiguration(
                new ParserConfiguration()
                        .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver)));
    }

    @Test
    public void testFieldVisiting() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "SimpleClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getFields().size());

        Field field = visitor.getFields().get(0);

        assertEquals("field", field.getName());
        assertEquals("int", field.getType().getName());
        assertTrue(field.getType().isPrimitive());
        assertEquals(1, field.getModifiers().size());
        assertEquals("private", field.getModifiers().get(0).getName());
        assertTrue(field.getAnnotations().isEmpty());
    }

    @Test
    public void testFieldReferenceType()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EnumWithField.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getFields().size());

        Field field = visitor.getFields().get(0);

        assertEquals("field", field.getName());
        assertEquals("examples.subpackage.ClassInSubpackage", field.getType().getName());
        assertEquals("ClassInSubpackage", field.getType().getSimpleName());
        assertFalse(field.getType().isPrimitive());
        assertEquals(1, field.getModifiers().size());
        assertEquals("private", field.getModifiers().get(0).getName());
        assertTrue(field.getAnnotations().isEmpty());
    }

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EmptyClass.java");
        unit.accept(visitor, null);

        assertEquals(0, visitor.getFields().size());
    }

    @Test
    public void testNestedClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "ClassWithInnerClass.java");
        unit.accept(visitor, null);

        assertEquals(3, visitor.getFields().size());

        Field field1 = visitor.getFields().get(0);
        Field field2 = visitor.getFields().get(1);
        Field field3 = visitor.getFields().get(2);

        assertEquals("field1", field1.getName());
        assertEquals("field2", field2.getName());
        assertEquals("innerField", field3.getName());

        assertEquals("examples.ClassWithInnerClass.InnerClass", field1.getType().getName());
        assertEquals("InnerClass", field1.getType().getSimpleName());
        assertEquals("float", field2.getType().getName());

        assertFalse(field1.getType().isPrimitive());
        assertTrue(field2.getType().isPrimitive());
    }
}
