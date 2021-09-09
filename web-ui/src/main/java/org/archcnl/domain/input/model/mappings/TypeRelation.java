package org.archcnl.domain.input.model.mappings;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class TypeRelation extends Relation {

    private static final String RELATION_TYPE = "rdf";

    private String realName;

    public TypeRelation(String name, String realName) {
        super(name, new LinkedList<>());
        this.realName = realName;
    }

    @Override
    public String toStringRepresentation() {
        return RELATION_TYPE + ":" + getRealName();
    }

    @Override
    public boolean canRelateToObjectType(ObjectType objectType) {
        return true;
    }

    @Override
    public List<ObjectType> getRelatableObjectTypes() {
        return RulesConceptsAndRelations.getInstance().getConceptManager().getConcepts().stream()
                .map(ObjectType.class::cast)
                .collect(Collectors.toList());
    }

    public String getRealName() {
        return realName;
    }
}
