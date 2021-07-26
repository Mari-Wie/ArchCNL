package examples;

import examples.subpackage.ClassInSubpackage;
import java.util.ArrayList;
import java.util.List;

@Deprecated(since = "today")
public class ComplexClass extends EmptyClass implements Interface {
    public static final String NEVER = "neverEver";
    public static final String NULL_CONSTANT;

    private static final double PI = 3.141;

    private final double radius;
    private final double area;

    @Deprecated()
    public ComplexClass(double diameter) throws Exception {
        this.radius = diameter * 0.5;
        this.area = calculateArea();
    }

    public ComplexClass(
            final double radius, @Deprecated(since = "yesterday") double otherHalfOfRadius) {
        Double diameter = radius + otherHalfOfRadius;
        this.radius = diameter * 0.5;
        this.area = calculateArea();
    }

    private double calculateArea() {
        return radius * radius * PI;
    }

    @Override
    @SuppressWarnings("some-warning")
    public String stringMethod() {
        return "Circle with area: " + String.valueOf(area);
    }

    @Override
    @Deprecated(since = NEVER)
    public SimpleClass referenceMethod(ClassInSubpackage parameter) {
        // some important comment
        return null;
    }
    
    @AnnotationWithNullConstant(key = NULL_CONSTANT)
    private double returnNull() {
      return null;
    }

    @Override
    @FictiveAnnotation(intValue= 0, doubleValue = 3.14)
    public char primitiveMethod(boolean parameter_with_long__snake_case_name) {
        List<Character> characters = new ArrayList<>();
        return 0;
    }
}
