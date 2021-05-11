package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TypeParameterVisitor extends VoidVisitorAdapter<Void> {

    public TypeParameterVisitor() {}

    @Override
    public void visit(TypeParameter n, Void arg) {
        // TODO: implement
        System.out.println(n.getName());
    }
}
