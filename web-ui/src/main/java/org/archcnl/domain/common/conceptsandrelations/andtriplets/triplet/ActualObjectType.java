package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

/** Common super class for all ObjectTypes except Variable */
public abstract class ActualObjectType extends ObjectType {

    public abstract boolean matchesRelatableObjectType(ActualObjectType actualObjectType);
}
