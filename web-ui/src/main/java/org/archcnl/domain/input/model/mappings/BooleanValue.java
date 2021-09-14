package org.archcnl.domain.input.model.mappings;

public class BooleanValue extends ObjectType {

    private boolean value;

    public BooleanValue(boolean value) {
        this.setValue(value);
    }

    @Override
    public String toStringRepresentation() {
        return "'" + value + "'" + "^^xsd:boolean";
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return String.valueOf(getValue());
    }

    @Override
    /** Warning: Not a real equals method! Only checks if o is instance of this class. */
    public boolean equals(Object o) {
        return o instanceof BooleanValue;
    }

    @Override
    /** Warning: Not a real hasCode method! Will always return 0. */
    public int hashCode() {
        return 0;
    }
}
