package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class CustomRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "architecture";

    private RelationMapping mapping;

    public CustomRelation(String name, String description, List<ObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
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

    public Optional<RelationMapping> getMapping() {
        return Optional.ofNullable(mapping);
    }

    public void changeRelatableObjectTypes(ObjectType objectType) {
        List<ObjectType> objectTypes = new LinkedList<>();
        objectTypes.add(objectType);
        // A relation can always relate to a variable
        try {
            objectTypes.add(new Variable("placeholder"));
        } catch (InvalidVariableNameException e) {
            throw new RuntimeException(e.getMessage());
        }
        setRelatableObjectType(objectTypes);
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getName();
    }
}