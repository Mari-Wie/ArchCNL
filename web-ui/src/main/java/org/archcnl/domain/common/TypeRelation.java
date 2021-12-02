package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class TypeRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "rdf";

    private String realName;

    public TypeRelation(String name, String realName, String description) {
        super(name, description, new LinkedList<>());
        this.realName = realName;
    }

    @Override
    public boolean canRelateToObjectType(ObjectType objectType) {
        return objectType instanceof Concept;
    }

    @Override
    public List<ActualObjectType> getRelatableObjectTypes() {
        Stream<Concept> allConcepts =
                Stream.concat(
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getInputConcepts()
                                .stream(),
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getOutputConcepts()
                                .stream());
        return allConcepts.map(ActualObjectType.class::cast).collect(Collectors.toList());
    }

    public String getRealName() {
        return realName;
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
        return RELATION_TYPE + ":" + getRealName();
    }
}
