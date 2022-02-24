package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Visits a marker annotation expression. */
public class MarkerAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;
    private String path;

    public MarkerAnnotationExpressionVisitor(String path) {
    	this.path = path;
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        annotationInstance = new AnnotationInstance(n.getName().asString(), new ArrayList<>(), path);
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
