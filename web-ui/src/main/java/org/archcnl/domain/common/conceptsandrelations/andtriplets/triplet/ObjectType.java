package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import org.archcnl.domain.common.FormattedDomainObject;

public abstract class ObjectType implements FormattedDomainObject {

    public abstract String getName();

    @Override
    public boolean equals(Object obj) {
        return requiredEqualsOverride(obj);
    }

    @Override
    public int hashCode() {
        return requiredHashCodeOverride();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + getName();
    }

    protected abstract boolean requiredEqualsOverride(Object obj);

    protected abstract int requiredHashCodeOverride();
}
