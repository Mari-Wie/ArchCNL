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

  public static final String PATH_TO_SRC = "./src/test/resources/";
  public static final String PATH_TO_PACKAGE_WITH_TEST_EXAMPLES =
      GenericVisitorTest.PATH_TO_SRC + "examples/";
  public static final String SIMPLE_CLASS = "SimpleClass.java";
  public static final String EMPTY_CLASS = "EmptyClass.java";
  public static final String COMPLEX_CLASS = "ComplexClass.java";
  public static final String CLASS_A = "ClassA.java";
  public static final String TEST_SUBPACKAGE = "extractortest/";
  public static final String ENUM_WITH_FIELDS = "EnumWithField.java";
  public static final String ENUM = "Enumeration.java";
  public static final String INNER_CLASS = "ClassWithInnerClass.java";
  public static final String INTERFACE = "Interface.java";
  public static final String ANNOTATION = "Annotation.java";
  public static final String TWO_CLASSES = "TwoTopLevelClasses.java";
  public static final String CLASS_WITH_EXCEPTIONS = "ExampleClassWithExceptions.java";

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
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      GenericVisitorTest.LOG.error("{} can not be initialized.", getVisitorClass().toString());
      e.printStackTrace();
    }
  }

  protected abstract Class<E> getVisitorClass();
}
