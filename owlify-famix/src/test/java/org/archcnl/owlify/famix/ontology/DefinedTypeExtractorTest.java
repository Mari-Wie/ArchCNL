package org.archcnl.owlify.famix.ontology;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.junit.Before;
import org.junit.Test;

public class DefinedTypeExtractorTest {

    private SourceFile fileWithSingleType;
    private SourceFile fileWithNestedType;
    private SourceFile fileWithTwoNestedTypes;

    private final String simpleType = "example.SimpleType";
    private final String outerType = "example.OuterType";
    private final String innerType = "example.OuterType.InnerType";
    private final String innerInnerType1 = "example.OuterType.InnerType.InnermostType1";
    private final String innerInnerType2 = "example.OuterType.InnerType.InnermostType2";

    @Before
    public void setUp() {

        DefinedType type1 =
                new ClassOrInterface(
                        simpleType,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        fileWithSingleType = new SourceFile(Path.of(""), type1, Namespace.TOP, new ArrayList<>());

        DefinedType type2 =
                new ClassOrInterface(
                        innerType,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        DefinedType type3 =
                new ClassOrInterface(
                        outerType,
                        Arrays.asList(type2),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        fileWithNestedType = new SourceFile(Path.of(""), type3, Namespace.TOP, new ArrayList<>());

        DefinedType type4 =
                new ClassOrInterface(
                        innerInnerType1,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        DefinedType type5 =
                new ClassOrInterface(
                        innerInnerType2,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        DefinedType type6 =
                new ClassOrInterface(
                        innerType,
                        Arrays.asList(type4, type5),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        DefinedType type7 =
                new ClassOrInterface(
                        outerType,
                        Arrays.asList(type6),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        false,
                        new ArrayList<>());

        fileWithTwoNestedTypes =
                new SourceFile(Path.of(""), type7, Namespace.TOP, new ArrayList<>());
    }

    @Test
    public void testSingleFileSingleType() {
        List<SourceFile> files = Arrays.asList(fileWithSingleType);
        DefinedTypeExtractor extractor = new DefinedTypeExtractor(files);

        assertTrue(extractor.isUserDefinedType(simpleType));
        assertFalse(extractor.isUserDefinedType(outerType));
    }

    @Test
    public void testSingleFileNestedType() {
        List<SourceFile> files = Arrays.asList(fileWithNestedType);
        DefinedTypeExtractor extractor = new DefinedTypeExtractor(files);

        assertTrue(extractor.isUserDefinedType(outerType));
        assertTrue(extractor.isUserDefinedType(innerType));
        assertFalse(extractor.isUserDefinedType(simpleType));
    }

    @Test
    public void testMulitpleFiles() {
        List<SourceFile> files = Arrays.asList(fileWithSingleType, fileWithNestedType);
        DefinedTypeExtractor extractor = new DefinedTypeExtractor(files);

        assertTrue(extractor.isUserDefinedType(simpleType));
        assertTrue(extractor.isUserDefinedType(outerType));
        assertTrue(extractor.isUserDefinedType(innerType));
        assertFalse(extractor.isUserDefinedType(innerInnerType1));
    }

    @Test
    public void testMultipleNestedTypes() {
        List<SourceFile> files = Arrays.asList(fileWithTwoNestedTypes);
        DefinedTypeExtractor extractor = new DefinedTypeExtractor(files);

        assertTrue(extractor.isUserDefinedType(outerType));
        assertTrue(extractor.isUserDefinedType(innerType));
        assertTrue(extractor.isUserDefinedType(innerInnerType1));
        assertTrue(extractor.isUserDefinedType(innerInnerType2));
        assertFalse(extractor.isUserDefinedType(simpleType));
    }
}
