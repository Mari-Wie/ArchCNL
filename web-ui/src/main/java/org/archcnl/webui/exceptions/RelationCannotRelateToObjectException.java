package org.archcnl.webui.exceptions;

import org.archcnl.webui.mappings.Concept;
import org.archcnl.webui.mappings.Relation;

public class RelationCannotRelateToObjectException extends Exception {

    private static final long serialVersionUID = -4083457902526821679L;

    public RelationCannotRelateToObjectException(Relation relation, Concept concept) {
        super(
                "The relation \""
                        + relation.getName()
                        + "\" cannot relate to object \""
                        + concept.getName()
                        + "\".");
    }
}
