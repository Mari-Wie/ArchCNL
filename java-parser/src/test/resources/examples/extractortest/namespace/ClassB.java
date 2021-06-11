package examples.extractortest.namespace;

@SuppressWarnings("all")
public class ClassB {
    private final int b;

    public ClassB(int b) {
        this.b = b;
    }

    @Deprecated
    public ClassB(@Annotation(description = "text") Integer b) {
        int local = b;
        this.b = local;
    }
}
