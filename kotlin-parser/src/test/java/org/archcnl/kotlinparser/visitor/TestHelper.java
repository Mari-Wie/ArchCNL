package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestHelper {

    static String getContentOfExampleFile(String... fileName) throws IOException {
        var pathToProject = Stream.of("src", "test", "resources", "example-project", "example");
        var pathToFile = Stream.of(fileName);
        var pathArray = Stream.concat(pathToProject, pathToFile).toArray(String[]::new);
        Path filePath = Paths.get("", pathArray);
        return Files.readString(filePath);
    }
}
