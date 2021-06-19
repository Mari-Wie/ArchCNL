package examples;

public @interface Annotation {
    String string();

    int integer();

    float floatingPoint() default 2.0f;
}
