package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.junit.Before;
import org.junit.Test;

public class NormalAnnotationVisitorTest
        extends GenericVisitorTest<NormalAnnotationExpressionVisitor> {

    private ClassOrInterfaceDeclaration complexClass;

    @Before
    public void setUp() throws FileNotFoundException, FileIsNotAJavaClassException {
        final String className = "ComplexClass";

        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + className
                                + ".java");

        complexClass = unit.getClassByName(className).get();
    }

    @Test
    public void testCorrectAnnotationVisited()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<AnnotationInstance> annos = visit(complexClass.getAnnotations());

        assertEquals(1, annos.size());

        AnnotationInstance anno = annos.get(0);

        assertEquals("Deprecated", anno.getName());
        assertEquals(1, anno.getValues().size());
        assertEquals("since", anno.getValues().get(0).getName());
        assertEquals("\"today\"", anno.getValues().get(0).getValue());
    }

    @Test
    public void testNoAnnotationPresent()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<AnnotationInstance> annos = visitMethodDeclaration("calculateArea");

        assertEquals(0, annos.size());
    }

    @Test
    public void testOtherAnnotationTypesPresent()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<AnnotationInstance> annos = visitMethodDeclaration("stringMethod");

        assertEquals(0, annos.size());
    }

    @Test
    public void testAnnotationWithoutValuePairButWithParentheses() {
        List<AnnotationInstance> annos =
                visit(complexClass.getConstructorByParameterTypes("double").get().getAnnotations());

        assertEquals(1, annos.size());

        AnnotationInstance anno = annos.get(0);
        assertEquals("Deprecated", anno.getName());
        assertEquals(0, anno.getValues().size());
    }

    @Test
    public void testAnnotationWithMultipleValues()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<AnnotationInstance> annos = visitMethodDeclaration("primitiveMethod");

        assertEquals(1, annos.size());

        AnnotationInstance anno = annos.get(0);

        assertEquals("FictiveAnnotation", anno.getName());
        assertEquals(2, anno.getValues().size());
        assertEquals("intValue", anno.getValues().get(0).getName());
        assertEquals("0", anno.getValues().get(0).getValue());
        assertEquals("doubleValue", anno.getValues().get(1).getName());
        assertEquals("3.14", anno.getValues().get(1).getValue());
    }

    @Test
    public void testConstantValueIsReplaced()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        List<AnnotationInstance> annos = visitMethodDeclaration("referenceMethod");

        assertEquals(1, annos.size());

        AnnotationInstance anno = annos.get(0);

        assertNotNull(anno);
        assertEquals("Deprecated", anno.getName());
        assertEquals(1, anno.getValues().size());
        assertEquals("since", anno.getValues().get(0).getName());
        assertEquals("\"neverEver\"", anno.getValues().get(0).getValue());
    }

    private List<AnnotationInstance> visit(List<AnnotationExpr> annotationExpressions) {
        return annotationExpressions.stream()
                .map(
                        a -> {
                            a.accept(visitor, null);
                            return visitor.getAnnotationInstance();
                        })
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }

    private List<AnnotationInstance> visitMethodDeclaration(String methodName)
            throws FileNotFoundException, FileIsNotAJavaClassException {
        MethodDeclaration method = complexClass.getMethodsByName(methodName).get(0);

        return visit(method.getAnnotations());
    }

    @Override
    protected Class<NormalAnnotationExpressionVisitor> getVisitorClass() {
        return NormalAnnotationExpressionVisitor.class;
    }
}
