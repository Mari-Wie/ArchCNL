package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;

/** Visits a normal annotation expression. */
public class NormalAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;

    public NormalAnnotationExpressionVisitor() {}

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        List<AnnotationMemberValuePair> values =
                n.getPairs().stream()
                        .map(
                                pair ->
                                        new AnnotationMemberValuePair(
                                                pair.getNameAsString(), pair.getValue().toString()))
                        .collect(Collectors.toList());

        annotationInstance = new AnnotationInstance(n.getName().asString(), values);
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
