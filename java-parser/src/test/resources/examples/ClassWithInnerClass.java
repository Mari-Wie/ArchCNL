package examples;

public class ClassWithInnerClass {
    private InnerClass field1;
    private float field2;

    protected ClassWithInnerClass(int parameter1, float parameter2) {
        field1 = new InnerClass(parameter1);
        field2 = parameter2;
    }

    public double method() {
        return field1.get() * field2;
    }

    private static class InnerClass {
        private final int innerField;

        public InnerClass(int innerParameter) {
            innerField = innerParameter;
        }

        public int get() {
            return innerField;
        }
    }
}
