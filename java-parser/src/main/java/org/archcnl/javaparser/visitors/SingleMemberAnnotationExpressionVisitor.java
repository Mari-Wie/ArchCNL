package org.archcnl.javaparser.visitors;

import com.github.javaparser.Position;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.Optional;

import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Parses a single member annotation expression. */
public class SingleMemberAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;
    private String path;
	private Optional<Position> beginning;

    public SingleMemberAnnotationExpressionVisitor(String path, Optional<Position> beginning) {
        this.path = path;
        this.beginning = beginning;
    }

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg) {
        annotationInstance = new AnnotationInstance(n.getNameAsString(), new ArrayList<>(), path, beginning);

        // TODO Annotation Attributes
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
