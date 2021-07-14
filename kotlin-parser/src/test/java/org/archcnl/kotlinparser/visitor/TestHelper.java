package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.archcnl.kotlinparser.parser.KtParser;
import org.archcnl.kotlinparser.parser.NamedFileContext;

public class TestHelper {

  public static final String COMPLEX_CLASS = "ComplexClass.kt";
  public static final String CLASS_IN_SUBPACKAGE = "ClassInSubpackage.kt";
  public static final String CLASS_WITH_INNER_CLASS = "ClassWithInnerClass.kt";
  public static final String INTERFACE = "Interface.kt";
  public static final String SUBPACKAGE = "subpackage";

  static NamedFileContext getKotlinFileContextFromFile(final String... fileName)
      throws IOException {
    final var parser = new KtParser();
    final var contentOfFile = TestHelper.getContentOfExampleFile(fileName);
    return parser.parse(contentOfFile);
  }

  static String getContentOfExampleFile(final String... fileName) throws IOException {
    final var pathToProject = Stream.of("src", "test", "resources", "example-project", "example");
    final var pathToFile = Stream.of(fileName);
    final var pathArray = Stream.concat(pathToProject, pathToFile).toArray(String[]::new);
    final Path filePath = Paths.get("", pathArray);
    return Files.readString(filePath);
  }
}
