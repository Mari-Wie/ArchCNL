package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.visitors.helper.VisitorHelpers;

public class ParameterVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;
    private org.archcnl.owlify.famix.codemodel.Parameter parameter;

    public ParameterVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
    }

    public ParameterVisitor() {}

    @Override
    public void visit(Parameter n, Void arg) {

        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
        n.accept(visitor, null);

        //        Individual parameterIndividual =
        // ontology.getParameterIndividual(n.getName().asString(), visitor.getDeclaredType(),
        // parent, n.getModifiers().stream().map(Modifier::toString).collect(Collectors.toList()));
        //        ontology.setHasNamePropertyForNamedEntity(n.getName().asString(),
        // parameterIndividual);
        //        AnnotationProcessor.visitAnnotations(ontology, n.getAnnotations(),
        // parameterIndividual);

        List<AnnotationInstance> annotations =
                VisitorHelpers.processAnnotations(n.getAnnotations());
        List<org.archcnl.owlify.famix.codemodel.Modifier> modifiers =
                VisitorHelpers.processModifiers(n.getModifiers());
        parameter =
                new org.archcnl.owlify.famix.codemodel.Parameter(
                        n.getNameAsString(), visitor.getType(), modifiers, annotations);
    }

    public org.archcnl.owlify.famix.codemodel.Parameter getParameter() {
        return parameter;
    }
}
