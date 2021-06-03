package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.archcnl.kotlinparser.parser.KtParser;
import org.archcnl.kotlinparser.parser.NamedFileContext;

public class TestHelper {
    static NamedFileContext getKotlinFileContextFromFile(String... fileName) throws IOException {
        var parser = new KtParser();
        var contentOfFile = TestHelper.getContentOfExampleFile(fileName);
        return parser.parse(contentOfFile);
    }

    static String getContentOfExampleFile(String... fileName) throws IOException {
        var pathToProject = Stream.of("src", "test", "resources", "example-project", "example");
        var pathToFile = Stream.of(fileName);
        var pathArray = Stream.concat(pathToProject, pathToFile).toArray(String[]::new);
        Path filePath = Paths.get("", pathArray);
        return Files.readString(filePath);
    }
}
