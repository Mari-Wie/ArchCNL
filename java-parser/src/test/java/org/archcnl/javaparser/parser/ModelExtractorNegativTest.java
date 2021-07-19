package org.archcnl.javaparser.parser;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelExtractorNegativTest {

  private static final String PATH_TO_PACKAGE_WITH_EXAMPLE_INVALID_JAVA_PROJECT =
      "./src/test/resources/invalid-examples/";

  private Map<String, SourceFile> projectUnits = new HashMap<>();

  @Before
  public void setUp() {
    final ModelExtractor extractor = new ModelExtractor(Arrays.asList(
        Path.of(ModelExtractorNegativTest.PATH_TO_PACKAGE_WITH_EXAMPLE_INVALID_JAVA_PROJECT)));
    final Project model = extractor.extractCodeModel();
    model.getSourceFiles().stream()
        .forEach(f -> projectUnits.put(f.getPath().getFileName().toString(), f));
  }

  @Test
  public void givenInvalidJavaProject_whenModelExtract_thenOnlyJavaFilesFound() {
    Assert.assertEquals(1, projectUnits.size());
    Assert.assertTrue(projectUnits.keySet().contains("WithoutNamespace.java"));
    Assert.assertTrue(projectUnits.get("WithoutNamespace.java").getNamespace().getName().isEmpty());
  }
}
