package examples.extractortest;

public enum Enumeration {
    VALUE,
    OTHER_VALUE;

    private String field;

    public boolean isOtherValue() {
        return this == OTHER_VALUE;
    }
}
