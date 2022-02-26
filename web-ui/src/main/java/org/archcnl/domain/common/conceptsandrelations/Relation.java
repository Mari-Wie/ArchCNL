package org.archcnl.domain.common.conceptsandrelations;

import java.util.Objects;
import java.util.Set;
import org.archcnl.domain.common.FormattedAdocDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;

public abstract class Relation
        implements HierarchyObject, FormattedAdocDomainObject, FormattedViewDomainObject {

    // The object types this relation relates to
    protected Set<ActualObjectType> relatableObjectTypes;
    private String name;
    private String description;

    /**
     * Constructs a relation that can relate to the given object types
     *
     * @param name The name of the relation used in the UI and in written files
     * @param description A description for this Relation
     * @param relatableObjectTypes The object types this relation can relate to
     */
    protected Relation(
            String name, String description, Set<ActualObjectType> relatableObjectTypes) {
        this.name = name;
        this.description = description;
        this.relatableObjectTypes = relatableObjectTypes;
    }

    public boolean canRelateToObjectType(ObjectType objectType) {
        if (objectType instanceof Variable) {
            // at the moment a Relation can always relate to a Variable
            return true;
        } else {
            ActualObjectType actualObjectType = (ActualObjectType) objectType;
            return relatableObjectTypes.stream()
                    .anyMatch(actualObjectType::matchesRelatableObjectType);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Relation) {
            final Relation that = (Relation) obj;
            return Objects.equals(this.getName(), that.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void changeName(String newName, RelationManager relationManager)
            throws RelationAlreadyExistsException {
        if (relationManager.getRelationByName(newName).isEmpty()) {
            this.name = newName;
        } else {
            throw new RelationAlreadyExistsException(newName);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
