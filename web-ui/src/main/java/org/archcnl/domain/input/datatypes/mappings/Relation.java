package org.archcnl.domain.input.datatypes.mappings;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.NoRelationException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.io.AdocIoUtils;

public abstract class Relation {

    private String name;

    // The object types this relation relates to
    private List<ObjectType> relatableObjectTypes;

    /**
     * Constructs a relation that can relate to the given object types
     *
     * @param name The name of the relation used in the UI and in written files
     * @param relatableObjectTypes The object types this relation can relate to
     */
    protected Relation(String name, List<ObjectType> relatableObjectTypes) {
        this.name = name;
        this.relatableObjectTypes = relatableObjectTypes;
        // A relation can always relate to a variable
        try {
            addRelatableObjectType(new Variable("placeholder"));
        } catch (InvalidVariableNameException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public abstract String toStringRepresentation();

    public boolean canRelateToObjectType(ObjectType objectType) {
        return relatableObjectTypes.contains(objectType);
    }

    public List<ObjectType> getRelatableObjectTypes() {
        return relatableObjectTypes;
    }

    protected void addRelatableObjectType(ObjectType objectType) {
        relatableObjectTypes.add(objectType);
    }

    public String getName() {
        return name;
    }

    public static Relation parsePredicate(String potentialPredicate) throws NoRelationException {
        try {
            if (potentialPredicate.contains(":")) {
                String predicateName =
                        AdocIoUtils.getFirstMatch(
                                Pattern.compile("(?<=\\w+:).+"), potentialPredicate);
                try {
                    try {
                        return RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName(predicateName);
                    } catch (RelationDoesNotExistException e2) {
                        return RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByRealName(predicateName);
                    }
                } catch (RelationDoesNotExistException e1) {
                    return new CustomRelation(predicateName);
                }
            } else {
                // has to be a SpecialRelation
                return RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .getRelationByRealName(potentialPredicate);
            }
        } catch (Exception e) {
            throw new NoRelationException(potentialPredicate);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Relation)) {
            return false;
        }
        Relation otherRelation = (Relation) o;
        return name.equals(otherRelation.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
