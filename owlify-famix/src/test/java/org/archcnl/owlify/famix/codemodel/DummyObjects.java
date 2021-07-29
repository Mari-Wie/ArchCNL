package org.archcnl.owlify.famix.codemodel;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides factory methods for codemodel dummy objects. They can be used as "dummies" in the
 * constructors of "higher-level" codemodel objects.
 */
public class DummyObjects {
    private DummyObjects() {}

    public static Modifier modifier() {
        return new Modifier("public");
    }

    public static Type primitiveType() {
        return new Type("int", "int", true);
    }

    public static Type referenceType() {
        return new Type("dummies.DummyClass", "DummyClass", false);
    }

    public static AnnotationMemberValuePair memberValuePair() {
        return new AnnotationMemberValuePair("dummyAttribute", "5");
    }

    public static AnnotationAttribute annotationAttribute() {
        return new AnnotationAttribute("dummyAttribute", primitiveType());
    }

    public static AnnotationInstance annotationInstance() {
        return new AnnotationInstance("dummies.DummyAnnotation", new ArrayList<>());
    }

    public static DefinedType definedType() {
        return new ClassOrInterface(
                "dummies.DummyClass",
                "DummyClass",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                false,
                new ArrayList<>());
    }

    public static Method method() {
        return new Method(
                "dummies.DummyClass.DummyClass",
                "dummies.DUmmyClass.DummyClass(int)",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                Type.UNUSED_VALUE,
                true,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public static LocalVariable localVariable() {
        return new LocalVariable(
                primitiveType(), "dummyVariable", Arrays.asList(new Modifier("public")));
    }

    public static Parameter parameter() {
        return new Parameter(
                "dummyParameter", primitiveType(), new ArrayList<>(), new ArrayList<>());
    }

    public static Field field() {
        return new Field("dummyField", primitiveType(), new ArrayList<>(), new ArrayList<>());
    }

    public static Namespace namespace() {
        return new Namespace("dummies", Namespace.TOP);
    }

    public static SourceFile sourceFile() {
        return new SourceFile(
                Paths.get("DUMMY-PATH"),
                Arrays.asList(definedType()),
                namespace(),
                new ArrayList<>());
    }
}
