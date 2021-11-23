package org.archcnl.domain.common;

import java.util.Objects;

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

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof StringValue) {
            final StringValue that = (StringValue) obj;
            return Objects.equals(this.getValue(), that.getValue());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(value);
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

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof StringValue) {
            final StringValue that = (StringValue) obj;
            return Objects.equals(this.getValue(), that.getValue());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(value);
    }
}
