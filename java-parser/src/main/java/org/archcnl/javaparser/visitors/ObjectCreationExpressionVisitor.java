package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.archcnl.owlify.famix.codemodel.Type;

/** Parses an object creation. */
public class ObjectCreationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private Type typeOfCreatedObject;

    public ObjectCreationExpressionVisitor() {}

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        n.accept(visitor, null);
    }

    /** @return the declared type of the created object */
    public Type getTypeOfCreatedObject() {
        return typeOfCreatedObject;
    }
}
