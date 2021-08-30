package org.archcnl.webui.datatypes.mappings;

public class StringValue extends ObjectType {

    private String value;

    public StringValue(String value) {
        this.setValue(value);
    }

    @Override
    public String toStringRepresentation() {
        return "'" + value + "'";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return getValue();
    }
}
