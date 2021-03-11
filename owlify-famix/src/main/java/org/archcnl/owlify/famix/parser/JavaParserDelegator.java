package org.archcnl.owlify.famix.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;

public class JavaParserDelegator {

    /**
     * @param path The absolute path to the Java file.
     * @return the corresponding compilation unit
     * @throws FileIsNotAJavaClassException
     */
    public CompilationUnit getCompilationUnitFromFilePath(String path)
            throws FileIsNotAJavaClassException {

        CompilationUnit unit = null;
        if (!path.endsWith(".java")) {
            throw new FileIsNotAJavaClassException(path);
        }

        try {
            //			in = new FileInputStream(path);
            unit = StaticJavaParser.parse(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return unit;
    }
}
