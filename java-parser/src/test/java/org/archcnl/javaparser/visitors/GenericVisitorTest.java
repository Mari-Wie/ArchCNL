package org.archcnl.javaparser.visitors;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public abstract class GenericVisitorTest<E extends VoidVisitorAdapter<Void>> {

	private static final Logger LOG = LogManager.getLogger(GenericVisitorTest.class);

	protected static final String PATH_TO_PACKAGE_WITH_TEST_EXAMPLES = "./src/test/java/examples/";
	protected static final String PATH_TO_SRC = "./src/test/java/";

	protected E visitor;

	@Before
	public void initializeVisitor() {
		createVisitor();
		// set a symbol solver
		final CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver());
		combinedTypeSolver.add(new JavaParserTypeSolver(GenericVisitorTest.PATH_TO_SRC));
		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
	}

	private void createVisitor() {
		try {
			visitor = getVisitorClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			GenericVisitorTest.LOG.error("{} can not be initialized.", getVisitorClass().toString());
			e.printStackTrace();
		}
	}

	protected abstract Class<E> getVisitorClass();
}
