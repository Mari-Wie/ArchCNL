package org.archcnl.webui.datatypes.mappings;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.webui.datatypes.RulesConceptsAndRelations;

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
        return Collections.<ObjectType>unmodifiableList(
                RulesConceptsAndRelations.getInstance().getConceptManager().getConcepts());
    }

    public String getRealName() {
        return realName;
    }
}
