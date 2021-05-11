package examples.extractortest.namespace;

import java.util.ArrayList;
import java.util.List;

public class ClassC {
    public static List<Integer> staticMethod(List<ClassB> items) throws Exception {
        List<Integer> localVariable = new ArrayList<>();
        throw new NullPointerException();
    }
}
