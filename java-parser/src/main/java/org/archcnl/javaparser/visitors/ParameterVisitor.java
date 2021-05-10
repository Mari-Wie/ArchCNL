package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.VisitorHelpers;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Parses a parameter declaration. */
public class ParameterVisitor extends VoidVisitorAdapter<Void> {

    private org.archcnl.owlify.famix.codemodel.Parameter parameter;

    public ParameterVisitor() {}

    @Override
    public void visit(Parameter n, Void arg) {

        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        n.accept(visitor, null);

        List<AnnotationInstance> annotations =
                VisitorHelpers.processAnnotations(n.getAnnotations());
        List<org.archcnl.owlify.famix.codemodel.Modifier> modifiers =
                VisitorHelpers.processModifiers(n.getModifiers());
        parameter =
                new org.archcnl.owlify.famix.codemodel.Parameter(
                        n.getNameAsString(), visitor.getType(), modifiers, annotations);
    }

    /** @return the parsed parameter */
    public org.archcnl.owlify.famix.codemodel.Parameter getParameter() {
        return parameter;
    }
}
