package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
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
    public void visit(final ThrowStmt n, final Void arg) {
        final ObjectCreationExpressionVisitor typeOfCreatedObjectVisitor =
                new ObjectCreationExpressionVisitor();
        n.getExpression().accept(typeOfCreatedObjectVisitor, null);

        Type thrownException = typeOfCreatedObjectVisitor.getTypeOfCreatedObject();

        if (thrownException == null) {
            ThrowStatementVisitor.LOG.debug(
                    "Throw statement does not match \"throw new X()\", using fall back solution: "
                            + n);

            final Expression throwExpression = n.getExpression();

            try {
                final String typeName =
                        throwExpression
                                .calculateResolvedType()
                                .asReferenceType()
                                .getQualifiedName();
                final String simpleName = typeName.substring(typeName.lastIndexOf(".") + 1);
                thrownException =
                        new Type(typeName, simpleName, false); // primitives types cannot be
                // thrown
            } catch (final UnsupportedOperationException e) {
                ThrowStatementVisitor.LOG.error("Can not calculate type of " + throwExpression, e);
            } catch (final UnsolvedSymbolException e) {
                ThrowStatementVisitor.LOG.error("Can not solve symbol of " + throwExpression, e);
            } catch (final RuntimeException e) {
                ThrowStatementVisitor.LOG.error(
                        "An exception was used that javaparser can not reach: " + throwExpression,
                        e);
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
