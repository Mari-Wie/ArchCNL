package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.LocalVariable;

/** Visits all local variable declarations in the given unit. */
public class LocalVariableVisitor extends VoidVisitorAdapter<Void> {

    private List<LocalVariable> localVariables;

    public LocalVariableVisitor() {
        localVariables = new ArrayList<>();
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
            variableDeclarator.accept(visitor, null);

            localVariables.add(
                    new LocalVariable(visitor.getType(), variableDeclarator.getNameAsString()));
        }
    }

    /** @return all encountered local variables */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }
}
