package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class TryCatchVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;

    private List<Type> caughtExceptions;

    public TryCatchVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
        this.caughtExceptions = new ArrayList<>();
    }

    public TryCatchVisitor() {
        this.caughtExceptions = new ArrayList<>();
    }

    @Override
    public void visit(TryStmt n, Void arg) {
        for (CatchClause catchClause : n.getCatchClauses()) {
            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
            catchClause.getParameter().getType().accept(visitor, null);
            Individual declaredTypeOfCatchClause = visitor.getDeclaredType();

            //            ontology.getCaughtExceptionIndividual(declaredTypeOfCatchClause, parent);

            caughtExceptions.add(visitor.getType());
        }
    }

    public List<Type> getCaughtExceptions() {
        return caughtExceptions;
    }
}
