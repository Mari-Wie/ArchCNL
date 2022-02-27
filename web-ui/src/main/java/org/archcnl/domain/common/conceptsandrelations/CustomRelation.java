package org.archcnl.domain.common.conceptsandrelations;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class CustomRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "architecture";

    private Optional<RelationMapping> mapping;

    public CustomRelation(
<<<<<<< HEAD
            String name,
            String description,
            Set<ActualObjectType> relatableSubjectTypes,
            Set<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableSubjectTypes, relatableObjectTypes);
=======
            String name, String description, List<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
>>>>>>> master
        this.mapping = Optional.empty();
    }

    public void setMapping(RelationMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getPredicate())) {
            this.mapping = Optional.of(mapping);
            ObjectType thenTripletObject = mapping.getThenTriplet().getObject();
            if (thenTripletObject instanceof ActualObjectType) {
                setRelatableObjectType(thenTripletObject);
            }
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getPredicate().getName());
        }
    }

    public Optional<RelationMapping> getMapping() {
        return mapping;
    }

    public void setRelatableObjectType(ObjectType objectType) {
        if (objectType instanceof Variable) {
            relatableObjectTypes = new LinkedHashSet<>();
        } else {
            relatableObjectTypes =
                    new LinkedHashSet<>(Arrays.asList((ActualObjectType) objectType));
        }
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

    @Override
    public boolean isEditable() {
        return true;
    }
}
