package org.archcnl.domain.common;

/** Common super class for all ObjectTypes except Variable */
public abstract class ActualObjectType extends ObjectType {

    public abstract boolean matchesRelatableObjectType(ActualObjectType actualObjectType);
}
