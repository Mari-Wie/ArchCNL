package org.archcnl.owlify.famix.codemodel;

import com.github.javaparser.Position;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Provides factory methods for codemodel dummy objects. They can be used as "dummies" in the
 * constructors of "higher-level" codemodel objects.
 */
public class DummyObjects {

    private static final Path path = Path.of("someRootDirectory/dummyObject");
    private static final Optional<Integer> position = Optional.of(3);

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
        return new AnnotationInstance("dummies.DummyAnnotation", new ArrayList<>(), path, position);
    }

    public static DefinedType definedType() {
        return new ClassOrInterface(
                path,
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
                new ArrayList<>(),
                path,
                position);
    }

    public static LocalVariable localVariable() {
        return new LocalVariable(
                primitiveType(),
                "dummyVariable",
                Arrays.asList(new Modifier("public")),
                path,
                position);
    }

    public static Parameter parameter() {
        return new Parameter(
                "dummyParameter",
                primitiveType(),
                new ArrayList<>(),
                new ArrayList<>(),
                path,
                position);
    }

    public static Field field() {
        return new Field(
                "dummyField",
                primitiveType(),
                new ArrayList<>(),
                new ArrayList<>(),
                path,
                position);
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
