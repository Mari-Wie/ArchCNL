package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.VisitorHelpers;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.archcnl.owlify.famix.codemodel.Modifier;

/** Visits all local variable declarations in the given unit. */
public class LocalVariableVisitor extends VoidVisitorAdapter<Void> {

    private List<LocalVariable> localVariables;
    private String path;

    public LocalVariableVisitor(String path) {
        localVariables = new ArrayList<>();
        this.path = path;
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
            variableDeclarator.accept(visitor, null);

            List<Modifier> modifiers = VisitorHelpers.processModifiers(n.getModifiers());
            localVariables.add(
                    new LocalVariable(
                            visitor.getType(),
                            variableDeclarator.getNameAsString(),
                            modifiers,
                            path,
                            variableDeclarator.getBegin()));
        }
    }

    /** @return all encountered local variables */
    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }
}
