package org.archcnl.javaparser.visitors;

import com.github.javaparser.Position;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.Optional;

import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Visits a marker annotation expression. */
public class MarkerAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;
    private String path;
	private Optional<Position> beginning;

    public MarkerAnnotationExpressionVisitor(String path, Optional<Position> beginning) {
        this.path = path;
        this.beginning = beginning;
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        annotationInstance =
                new AnnotationInstance(n.getName().asString(), new ArrayList<>(), path, beginning);
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
