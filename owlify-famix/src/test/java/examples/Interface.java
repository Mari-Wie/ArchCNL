package examples;

import examples.subpackage.ClassInSubpackage;

public interface Interface {
    public String stringMethod();

    public SimpleClass referenceMethod(ClassInSubpackage parameter);

    public char primitiveMethod(boolean parameter_with_long__snake_case_name);
}
