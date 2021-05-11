package org.archcnl.javaparser.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Before;
import org.junit.Test;

public class ImportDeclarationVisitorTest {

    private ImportDeclarationVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new ImportDeclarationVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testNoImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        List<String> imports = extractImportedTypes(pathToExamplePackage + "EmptyClass.java");
        assertEquals(0, imports.size());
    }

    @Test
    public void testSomeImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        List<String> imports = extractImportedTypes(pathToExamplePackage + "ComplexClass.java");

        assertEquals(3, imports.size());
        assertTrue(imports.contains("examples.subpackage.ClassInSubpackage"));
        assertTrue(imports.contains("java.util.ArrayList"));
        assertTrue(imports.contains("java.util.List"));
    }

    @Test
    public void testStaticImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        List<String> imports =
                extractImportedTypes(pathToExamplePackage + "extractortest/ClassA.java");

        assertEquals(3, imports.size());
        assertTrue(imports.contains("examples.extractortest.namespace.ClassB"));
        assertTrue(imports.contains("examples.extractortest.namespace.ClassC"));
        assertTrue(imports.contains("java.util.Arrays"));
    }

    @Test
    public void testSomeImportsSimpleNames()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<String> imports =
                extractImportedSimpleNames(pathToExamplePackage + "ComplexClass.java");

        assertEquals(3, imports.size());
        assertTrue(imports.contains("ClassInSubpackage"));
        assertTrue(imports.contains("ArrayList"));
        assertTrue(imports.contains("List"));
    }

    @Test
    public void testStaticImportsSimleNames()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<String> imports =
                extractImportedSimpleNames(pathToExamplePackage + "extractortest/ClassA.java");

        assertEquals(3, imports.size());
        assertTrue(imports.contains("ClassB"));
        assertTrue(imports.contains("ClassC"));
        assertTrue(imports.contains("Arrays"));
    }

    private List<String> extractImportedTypes(String path)
            throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit = CompilationUnitFactory.getFromPath(path);
        unit.accept(visitor, null);

        // primitive types cannot be imported
        visitor.getImports().forEach(type -> assertFalse(type.isPrimitive()));

        return visitor.getImports().stream().map(Type::getName).collect(Collectors.toList());
    }

    private List<String> extractImportedSimpleNames(String path)
            throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit = CompilationUnitFactory.getFromPath(path);
        unit.accept(visitor, null);

        // primitive types cannot be imported
        visitor.getImports().forEach(type -> assertFalse(type.isPrimitive()));

        return visitor.getImports().stream().map(Type::getSimpleName).collect(Collectors.toList());
    }
}
