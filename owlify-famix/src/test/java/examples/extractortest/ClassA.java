package examples.extractortest;

import static examples.extractortest.namespace.ClassC.staticMethod;

import examples.extractortest.namespace.ClassB;
import java.util.Arrays;

public class ClassA implements Interface {

    Enumeration fieldEnum;
    private final ClassB fieldClassB = new ClassB(42);

    public void method() {
        try {
            staticMethod(Arrays.asList(fieldClassB));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void someMethod() throws Exception {
        Exception e = new UnsupportedOperationException();
        throw e; // special case: first create the exception, then throw it
    }

    public static class NestedClass {
        public int nestedField;

        public void nestedMethod() {}
    }
}
