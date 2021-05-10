package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Parses a single member annotation expression. */
public class SingleMemberAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;

    public SingleMemberAnnotationExpressionVisitor() {}

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg) {
        annotationInstance = new AnnotationInstance(n.getNameAsString(), new ArrayList<>());

        // TODO Annotation Attributes
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
