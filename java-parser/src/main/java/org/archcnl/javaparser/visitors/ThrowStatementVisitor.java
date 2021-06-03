package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.codemodel.Type;

/** Parses all throw statements in the analyzed unit. */
public class ThrowStatementVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(ThrowStatementVisitor.class);

    private final List<Type> thrownExceptions;

    public ThrowStatementVisitor() {
        thrownExceptions = new ArrayList<>();
    }

    @Override
    public void visit(ThrowStmt n, Void arg) {
        ObjectCreationExpressionVisitor typeOfCreatedObjectVisitor =
                new ObjectCreationExpressionVisitor();
        n.getExpression().accept(typeOfCreatedObjectVisitor, null);

        Type thrownException = typeOfCreatedObjectVisitor.getTypeOfCreatedObject();

        if (thrownException == null) {
            LOG.debug(
                    "Throw statement does not match \"throw new X()\", using fall back solution: "
                            + n);

            Expression throwExpression = n.getExpression();

            try {
                String typeName =
                        throwExpression
                                .calculateResolvedType()
                                .asReferenceType()
                                .getQualifiedName();
                String simpleName = typeName.substring(typeName.lastIndexOf(".") + 1);
                thrownException =
                        new Type(typeName, simpleName, false); // primitives types cannot be thrown
            } catch (UnsupportedOperationException e) {
                LOG.error("Can not calculate type of " + throwExpression, e);
            }
        }

        if (thrownException != null) {
            thrownExceptions.add(thrownException);
        }
    }

    /** @return the declared types of all thrown exceptions */
    public List<Type> getThrownExceptionType() {
        return thrownExceptions;
    }
}
