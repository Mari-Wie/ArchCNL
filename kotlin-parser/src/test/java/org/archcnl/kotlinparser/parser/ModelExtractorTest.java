package org.archcnl.kotlinparser.parser;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ModelExtractorTest {

    private final Path pathToPackage = Path.of("./src/test/resources/example-project/example/");
    private final Path pathToPackageWithInvalidProject =
            Path.of("./src/test/resources/example-project/example-invalid/");

    private final String interfaceType = "Interface";
    private final String getType = "GET";
    private final String complexClass = "ComplexClass";
    private final String classWithInnerClass = "ClassWithInnerClass";
    private final String innerClass = "InnerClass";
    private final String classInSubpackage = "ClassInSubpackage";
    private final String anotherClass = "AnotherClass";

    private final String baseNamespace = "example";

    @Test
    void givenExampleKotlinProject_whenModelExtractorVisitProject_thenAllSourceFilesFound() {
        // given
        final ModelExtractor modelExtractor = new ModelExtractor(Arrays.asList(pathToPackage));
        // when
        final Project project = modelExtractor.extractCodeModel();
        // then
        final List<SourceFile> sourceFiles = project.getSourceFiles();
        Assert.assertNotNull(sourceFiles);
        Assert.assertEquals(sourceFiles.size(), 6);

        final SourceFile interfaceSourceFile =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), interfaceType))
                        .findAny()
                        .get();
        Assert.assertEquals(interfaceSourceFile.getNamespace().getName(), baseNamespace);

        final SourceFile getSourceFile =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), getType))
                        .findAny()
                        .get();
        Assert.assertEquals(getSourceFile.getNamespace().getName(), baseNamespace);

        final SourceFile complexSourceFile =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), complexClass))
                        .findAny()
                        .get();
        Assert.assertEquals(complexSourceFile.getNamespace().getName(), baseNamespace);

        final SourceFile sourceFileWithInnerClass =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), classWithInnerClass))
                        .findAny()
                        .get();
        Assert.assertEquals(sourceFileWithInnerClass.getNamespace().getName(), baseNamespace);
        Assert.assertTrue(
                sourceFileWithInnerClass.getDefinedTypes().stream()
                        .map(DefinedType::getSimpleName)
                        .collect(Collectors.toList())
                        .contains(innerClass));

        final SourceFile sourceFileInSubpackage =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), classInSubpackage))
                        .findAny()
                        .get();
        Assert.assertEquals(
                sourceFileInSubpackage.getNamespace().getName(), baseNamespace + ".subpackage");

        final SourceFile sourceFileInAnotherPackage =
                sourceFiles.stream()
                        .filter(f -> isTypeInFileDefined(f.getDefinedTypes(), anotherClass))
                        .findAny()
                        .get();
        Assert.assertEquals(
                sourceFileInAnotherPackage.getNamespace().getName(),
                baseNamespace + ".anotherPackage");
    }

    @Test
    void givenInvalidKotlinProject_whenModelExtractorVisitProject_thenNoTypesFound() {
        // given
        final ModelExtractor modelExtractor =
                new ModelExtractor(Arrays.asList(pathToPackageWithInvalidProject));
        // when
        final Project project = modelExtractor.extractCodeModel();
        // then
        final List<SourceFile> sourceFiles = project.getSourceFiles();
        Assert.assertNotNull(sourceFiles);
        Assert.assertEquals(sourceFiles.size(), 1);
        Assert.assertTrue(sourceFiles.get(0).getDefinedTypes().isEmpty());
    }

    private boolean isTypeInFileDefined(
            final List<DefinedType> definedTypes, final String typeName) {
        final Optional<DefinedType> definedType =
                definedTypes.stream().filter(t -> t.getSimpleName().equals(typeName)).findAny();
        return definedType.isPresent();
    }
}
