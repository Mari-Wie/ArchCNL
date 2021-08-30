package org.archcnl.domain.input.datatypes.mappings;

public abstract class Concept extends ObjectType {

    private String name;

    protected Concept(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
