package org.archcnl.webui.datatypes.mappings;

import java.util.LinkedList;
import org.archcnl.webui.exceptions.InvalidVariableNameException;
import org.archcnl.webui.exceptions.UnrelatedMappingException;
import org.archcnl.webui.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;

public class CustomRelation extends Relation {

    private static final String RELATION_TYPE = "architecture";

    private RelationMapping mapping;

    public CustomRelation(String name)
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    InvalidVariableNameException {
        super(name, new LinkedList<>());
        // TODO: Allow for thenVariables to be null
        mapping =
                new RelationMapping(
                        new Variable("placeholder1"),
                        new Variable("placeholder2"),
                        new LinkedList<>(),
                        this);
    }

    public void setMapping(RelationMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getPredicate())) {
            this.mapping = mapping;
            addRelatableObjectType(mapping.getThenTriplet().getObject());
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getPredicate().getName());
        }
    }

    public RelationMapping getMapping() {
        return mapping;
    }

    @Override
    public String toStringRepresentation() {
        return RELATION_TYPE + ":" + getName();
    }
}
