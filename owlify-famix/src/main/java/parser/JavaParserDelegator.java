package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;

public class JavaParserDelegator {
	
	/**
	 * 
	 * @param path The absolute path to the Java file.
	 * @return the corresponding compilation unit
	 * @throws FileIsNotAJavaClassException 
	 */
	public CompilationUnit getCompilationUnitFromFilePath(String path) throws FileIsNotAJavaClassException {
		FileInputStream in;
		CompilationUnit unit = null;
		
		if(!path.endsWith(".java")) {
			throw new FileIsNotAJavaClassException(path);
		}
		
		try {
			in = new FileInputStream(path);
			unit = JavaParser.parse(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unit;
	}
	
}
