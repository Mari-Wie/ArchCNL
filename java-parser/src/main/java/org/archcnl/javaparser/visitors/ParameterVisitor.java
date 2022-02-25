package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.VisitorHelpers;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Parses a parameter declaration. */
public class ParameterVisitor extends VoidVisitorAdapter<Void> {

    private org.archcnl.owlify.famix.codemodel.Parameter parameter;
	private String path;

    public ParameterVisitor(String path) {
    	this.path = path;
    }

    @Override
    public void visit(Parameter n, Void arg) {

        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        n.accept(visitor, null);

        List<AnnotationInstance> annotations =
                VisitorHelpers.processAnnotations(n.getAnnotations(), path);
        List<org.archcnl.owlify.famix.codemodel.Modifier> modifiers =
                VisitorHelpers.processModifiers(n.getModifiers());
        String location = path;
        if(n.getBegin().isPresent()) {
        	location += ", Line: " + String.valueOf(n.getBegin().get().line);
        }
        parameter =
                new org.archcnl.owlify.famix.codemodel.Parameter(
                        n.getNameAsString(), visitor.getType(), modifiers, annotations, location);
    }

    /** @return the parsed parameter */
    public org.archcnl.owlify.famix.codemodel.Parameter getParameter() {
        return parameter;
    }
}
