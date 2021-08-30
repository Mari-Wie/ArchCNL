package org.archcnl.webui.datatypes.mappings;

import java.util.Objects;

public abstract class ObjectType {

    public abstract String toStringRepresentation();

    public abstract String getName();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ObjectType)) {
            return false;
        }
        ObjectType otherObjectType = (ObjectType) o;
        return toStringRepresentation().equals(otherObjectType.toStringRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
