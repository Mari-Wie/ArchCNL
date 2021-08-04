package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NormalAnnotationVisitorTest
        extends GenericVisitorTest<NormalAnnotationExpressionVisitor> {

    private ClassOrInterfaceDeclaration complexClass;

    private EnumDeclaration enumType;

    @Before
    public void setUp() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.COMPLEX_CLASS);
        complexClass =
                unit.getClassByName(
                                GenericVisitorTest.COMPLEX_CLASS.substring(
                                        0, GenericVisitorTest.COMPLEX_CLASS.indexOf(".")))
                        .get();
        final CompilationUnit enumUnit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.ENUM);
        enumType =
                enumUnit.getEnumByName(
                                GenericVisitorTest.ENUM.substring(
                                        0, GenericVisitorTest.ENUM.indexOf(".")))
                        .get();
    }

    @Test
    public void
            givenValidJavaClassWithMethodAnnotationWithConstantInParam_whenAnnotationVisitorVisit_thenAnnotationWithReplacedValueFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visitMethodDeclarationInClass("referenceMethod");
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertNotNull(anno);
        Assert.assertEquals("Deprecated", anno.getName());
        Assert.assertEquals(1, anno.getValues().size());
        Assert.assertEquals("since", anno.getValues().get(0).getName());
        Assert.assertEquals("\"neverEver\"", anno.getValues().get(0).getValue());
    }

    @Test
    public void
            givenValidJavaClassWithMethodAnnotationWithMultipleParams_whenAnnotationVisitorVisit_thenAnnotationFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visitMethodDeclarationInClass("primitiveMethod");
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertEquals("FictiveAnnotation", anno.getName());
        Assert.assertEquals(2, anno.getValues().size());
        Assert.assertEquals("intValue", anno.getValues().get(0).getName());
        Assert.assertEquals("0", anno.getValues().get(0).getValue());
        Assert.assertEquals("doubleValue", anno.getValues().get(1).getName());
        Assert.assertEquals("3.14", anno.getValues().get(1).getValue());
    }

    @Test
    public void
            givenValidJavaClassWithParamAnnotation_whenAnnotationVisitorVisit_thenAnnotationWithoutValuePairButWithParenthesesFound() {
        // given, when
        final List<AnnotationInstance> annos =
                visit(complexClass.getConstructorByParameterTypes("double").get().getAnnotations());
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertEquals("Deprecated", anno.getName());
        Assert.assertEquals(0, anno.getValues().size());
    }

    @Test
    public void
            givenValidJavaClassWithMethodWithoutNonNormalAnnotations_whenAnnotationVisitorVisit_thenNoNormalMethodAnnotationsFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        // Annotations in 'calculateArea' method aren't normal {@NormalAnnotationExpr}
        final List<AnnotationInstance> annos = visitMethodDeclarationInClass("stringMethod");
        // then
        Assert.assertEquals(0, annos.size());
    }

    @Test
    public void
            givenValidJavaClassWithMethodWithoutAnnotations_whenAnnotationVisitorVisit_thenNoMethodAnnotationsFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visitMethodDeclarationInClass("calculateArea");
        // then
        Assert.assertEquals(0, annos.size());
    }

    @Test
    public void
            givenValidJavaClassWithAnnotations_whenAnnotationVisitorVisit_thenTypeAnnotationFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visit(complexClass.getAnnotations());
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertEquals("Deprecated", anno.getName());
        Assert.assertEquals(1, anno.getValues().size());
        Assert.assertEquals("since", anno.getValues().get(0).getName());
        Assert.assertEquals("\"today\"", anno.getValues().get(0).getValue());
    }

    @Test
    public void
            givenValidEnumWithMethodsAnnotations_whenAnnotationVisitorVisit_thenMethodAnnotationFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visitMethodDeclarationInEnum("getIndex");
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertEquals("SomeAnnotation", anno.getName());
        Assert.assertEquals(1, anno.getValues().size());
        Assert.assertEquals("key", anno.getValues().get(0).getName());
        Assert.assertEquals("A", anno.getValues().get(0).getValue());
    }

    @Test
    public void
            givenValidJavaTypeWithMethodAnnotationWithNullConstantInParam_whenAnnotationVisitorVisit_thenAnnotationWithNullValueFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given, when
        final List<AnnotationInstance> annos = visitMethodDeclarationInClass("returnNull");
        // then
        Assert.assertEquals(1, annos.size());

        final AnnotationInstance anno = annos.get(0);
        Assert.assertEquals("AnnotationWithNullConstant", anno.getName());
        Assert.assertEquals(1, anno.getValues().size());
        Assert.assertEquals("key", anno.getValues().get(0).getName());
        Assert.assertEquals("NULL_CONSTANT", anno.getValues().get(0).getValue());
    }

    private List<AnnotationInstance> visit(final List<AnnotationExpr> annotationExpressions) {
        return annotationExpressions.stream()
                .map(
                        a -> {
                            a.accept(visitor, null);
                            return visitor.getAnnotationInstance();
                        })
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }

    private List<AnnotationInstance> visitMethodDeclarationInClass(final String methodName)
            throws FileNotFoundException, FileIsNotAJavaClassException {
        final MethodDeclaration method = complexClass.getMethodsByName(methodName).get(0);
        return visit(method.getAnnotations());
    }

    private List<AnnotationInstance> visitMethodDeclarationInEnum(final String methodName)
            throws FileNotFoundException, FileIsNotAJavaClassException {
        final MethodDeclaration method = enumType.getMethodsByName(methodName).get(0);
        return visit(method.getAnnotations());
    }

    @Override
    protected Class<NormalAnnotationExpressionVisitor> getVisitorClass() {
        return NormalAnnotationExpressionVisitor.class;
    }
}
