package org.archcnl.owlify.famix.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;

public class CompilationUnitFactory {

    private CompilationUnitFactory() {}

    /**
     * Creates a CompilationUnit instance corresponding to the specified Java file.
     *
     * @param path The absolute path to the Java file.
     * @return the corresponding compilation unit
     * @throws FileIsNotAJavaClassException when the specified file is not a Java file
     * @throws FileNotFoundException when the specified file cannot be accessed
     */
    public static CompilationUnit getFromPath(String path)
            throws FileIsNotAJavaClassException, FileNotFoundException {

        if (!path.endsWith(".java")) {
            throw new FileIsNotAJavaClassException(path);
        }

        return StaticJavaParser.parse(new FileInputStream(path));
    }
}
