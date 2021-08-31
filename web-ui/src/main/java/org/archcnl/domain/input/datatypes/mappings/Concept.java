package org.archcnl.domain.input.datatypes.mappings;

import java.util.Objects;

public abstract class Concept extends ObjectType {

    private String name;

    protected Concept(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Concept)) {
            return false;
        }
        Concept otherConcept = (Concept) o;
        return name.equals(otherConcept.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
