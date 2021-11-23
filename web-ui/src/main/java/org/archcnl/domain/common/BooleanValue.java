package org.archcnl.domain.common;

import java.util.Objects;

public class BooleanValue extends ObjectType {

    private boolean value;
    private static final String XSD_BOOLEAN = "^^xsd:boolean";

    public BooleanValue(boolean value) {
        this.setValue(value);
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
    public String transformToSparqlQuery() {
        return "\"" + value + "\"" + XSD_BOOLEAN;
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return "'" + value + "'" + XSD_BOOLEAN;
    }

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof BooleanValue) {
            final BooleanValue that = (BooleanValue) obj;
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
        return "\"" + value + "\"" + "^^xsd:boolean";
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return "'" + value + "'" + "^^xsd:boolean";
    }

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof BooleanValue) {
            final BooleanValue that = (BooleanValue) obj;
            return Objects.equals(this.getValue(), that.getValue());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(value);
    }
}
