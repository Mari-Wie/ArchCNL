package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.jena.ontology.Individual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class ThrowStatementVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(ThrowStatementVisitor.class);

    private FamixOntology ontology;
    private Individual parent;
    private Type thrownException;

    public ThrowStatementVisitor() {}

    public ThrowStatementVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
    }

    @Override
    public void visit(ThrowStmt n, Void arg) {
        ObjectCreationExpressionVisitor typeOfCreatedObjectVisitor =
                new ObjectCreationExpressionVisitor(ontology);
        n.getExpression().accept(typeOfCreatedObjectVisitor, null);
        Individual typeOfThrownException = typeOfCreatedObjectVisitor.getTypeOfCreatedObject2();

        thrownException = typeOfCreatedObjectVisitor.getTypeOfCreatedObject();

        if (typeOfThrownException == null) {
            LOG.debug(
                    "Throw statement does not match \"throw new X()\", using fall back solution: "
                            + n.toString());
            Expression throwExpression = n.getExpression();
            String typeName =
                    throwExpression.calculateResolvedType().asReferenceType().getQualifiedName();
            //            typeOfThrownException = ontology.getReferenceTypeIndividual(typeName);
        }

        if (thrownException == null) {
            LOG.debug(
                    "Throw statement does not match \"throw new X()\", using fall back solution: "
                            + n.toString());

            Expression throwExpression = n.getExpression();
            String typeName =
                    throwExpression.calculateResolvedType().asReferenceType().getQualifiedName();

            thrownException = new Type(typeName, false); // primitives types cannot be thrown
        }

        //        ontology.getThrownExceptionIndividual(typeOfThrownException, parent);
    }

    public Type getThrownExceptionType() {
        return thrownException;
    }
}
