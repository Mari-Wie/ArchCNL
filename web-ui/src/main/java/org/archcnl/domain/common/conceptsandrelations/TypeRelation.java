package org.archcnl.domain.common.conceptsandrelations;

import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;

public class TypeRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "rdf";
    private static final TypeRelation typeRelation =
            new TypeRelation(
                    "is-of-type",
                    "type",
                    "This relation is used to state that the subject is an instance of the class stated in the object.");

    private String realName;

    private TypeRelation(String name, String realName, String description) {
        super(name, description, new LinkedHashSet<>(), new LinkedHashSet<>());
        this.realName = realName;
    }

    @Override
    public boolean canRelateToObjectType(ObjectType objectType) {
        return objectType instanceof Concept;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public Set<ActualObjectType> getRelatableSubjectTypes() {
        throw new UnsupportedOperationException("Can only be determined on Triplet level.");
    }

    @Override
    public Set<ActualObjectType> getRelatableObjectTypes() {
        throw new UnsupportedOperationException(
                "The type relation can relate to all possible concepts.");
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getRealName();
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }

    public static TypeRelation getTyperelation() {
        return typeRelation;
    }
}
