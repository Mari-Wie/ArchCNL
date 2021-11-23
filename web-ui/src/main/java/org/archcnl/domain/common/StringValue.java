package org.archcnl.domain.common;

public class StringValue extends ObjectType {

    private String value;

    public StringValue(String value) {
        this.setValue(value);
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

    @Override
    /** Warning: Not a real equals method! Only checks if o is instance of this class. */
    public boolean equals(Object o) {
        return o instanceof StringValue;
    }

    @Override
    /** Warning: Not a real hasCode method! Will always return 0. */
    public int hashCode() {
        return 0;
    }

    @Override
    public String transformToSparqlQuery() {
        return "\"" + value + "\"" + "^^xsd:string";
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return "'" + value + "'";
    }
}
