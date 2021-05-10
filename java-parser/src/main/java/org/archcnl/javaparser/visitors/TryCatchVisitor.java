package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.Type;

/** Parses all try-catch statements in the given unit. */
public class TryCatchVisitor extends VoidVisitorAdapter<Void> {

    private List<Type> caughtExceptions;

    public TryCatchVisitor() {
        this.caughtExceptions = new ArrayList<>();
    }

    @Override
    public void visit(TryStmt n, Void arg) {
        for (CatchClause catchClause : n.getCatchClauses()) {
            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
            catchClause.getParameter().getType().accept(visitor, null);

            caughtExceptions.add(visitor.getType());
        }
    }

    /** @return list of the declared types of catched exceptions */
    public List<Type> getCaughtExceptions() {
        return caughtExceptions;
    }
}
