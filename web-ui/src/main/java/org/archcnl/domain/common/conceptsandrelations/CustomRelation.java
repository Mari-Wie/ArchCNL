package org.archcnl.domain.common.conceptsandrelations;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class CustomRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "architecture";

    private Optional<RelationMapping> mapping;

    public CustomRelation(
            String name,
            String description,
            Set<ActualObjectType> relatableSubjectTypes,
            Set<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableSubjectTypes, relatableObjectTypes);
        this.mapping = Optional.empty();
    }

    public void setMapping(RelationMapping mapping, ConceptManager conceptManager)
            throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getPredicate())) {
            this.mapping = Optional.of(mapping);
            Set<ActualObjectType> subjectRelatableTypes = new LinkedHashSet<>();
            Set<ActualObjectType> objectRelatableTypes = new LinkedHashSet<>();

            Variable subject = mapping.getThenTriplet().getSubject();
            ObjectType object = mapping.getThenTriplet().getObject();

            if (object instanceof ActualObjectType) {
                objectRelatableTypes.add((ActualObjectType) object);
            }

            VariableManager variableManager = new VariableManager();
            mapping.getWhenTriplets()
                    .forEach(
                            andTriplets -> {
                                variableManager.parseVariableTypes(andTriplets, conceptManager);
                                subjectRelatableTypes.addAll(subject.getDynamicTypes());
                                if (object instanceof Variable) {
                                    objectRelatableTypes.addAll(
                                            ((Variable) object).getDynamicTypes());
                                }
                            });
            this.relatableSubjectTypes = subjectRelatableTypes;
            this.relatableObjectTypes = objectRelatableTypes;
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getPredicate().getName());
        }
    }

    public Optional<RelationMapping> getMapping() {
        return mapping;
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
