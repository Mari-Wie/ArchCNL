package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class MarkerAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual annotatedEntity;
    private AnnotationInstance annotationInstance;

    public MarkerAnnotationExpressionVisitor(
            FamixOntology famixOntology, Individual annotatedEntity) {
        this.ontology = famixOntology;
        this.annotatedEntity = annotatedEntity;
    }

    public MarkerAnnotationExpressionVisitor() {}

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        annotationInstance = new AnnotationInstance(n.getName().asString(), new ArrayList<>());
        //        ontology.getAnnotationInstanceIndividual(n.getName().asString(), annotatedEntity,
        // new NodeList<MemberValuePair>());
    }

    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
