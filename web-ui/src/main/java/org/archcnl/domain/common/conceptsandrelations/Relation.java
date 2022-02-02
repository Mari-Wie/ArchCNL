package org.archcnl.domain.common.conceptsandrelations;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.archcnl.domain.common.FormattedAdocDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.io.AdocIoUtils;
import org.archcnl.domain.input.exceptions.NoMatchFoundException;
import org.archcnl.domain.input.exceptions.NoRelationException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public abstract class Relation
        implements ConceptAndRelation, FormattedAdocDomainObject, FormattedViewDomainObject {

    // The object types this relation relates to
    protected List<ActualObjectType> relatableObjectTypes;
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
            String name, String description, List<ActualObjectType> relatableObjectTypes) {
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

    public List<ActualObjectType> getRelatableObjectTypes() {
        return relatableObjectTypes;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Relation parsePredicate(String potentialPredicate) throws NoRelationException {
        try {
            if (potentialPredicate.contains(":")) {
                String predicateName =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=\\w+:).+"), potentialPredicate);
                return RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .getRelationByName(predicateName)
                        .orElse(
                                RulesConceptsAndRelations.getInstance()
                                        .getRelationManager()
                                        .getRelationByRealName(predicateName)
                                        .orElse(
                                                new CustomRelation(
                                                        predicateName, "", new LinkedList<>())));
            } else {
                // has to be a SpecialRelation
                return RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .getRelationByRealName(potentialPredicate)
                        .orElseThrow(() -> new NoRelationException(potentialPredicate));
            }
        } catch (NoMatchFoundException e) {
            throw new NoRelationException(potentialPredicate);
        }
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

    public void changeName(String newName) throws RelationAlreadyExistsException {
        if (!RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .doesRelationExist(new CustomRelation(newName, "", new LinkedList<>()))) {
            this.name = newName;
            RulesConceptsAndRelations.getInstance()
                    .getRelationManager()
                    .relationHasBeenUpdated(this);
        } else {
            throw new RelationAlreadyExistsException(newName);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        RulesConceptsAndRelations.getInstance().getRelationManager().relationHasBeenUpdated(this);
    }
}
