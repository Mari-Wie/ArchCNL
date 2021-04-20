package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class SingleMemberAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual annotatedEntity;
    private AnnotationInstance annotationInstance;

    public SingleMemberAnnotationExpressionVisitor(
            FamixOntology famixOntology, Individual annotatedEntity) {
        this.ontology = famixOntology;
        this.annotatedEntity = annotatedEntity;
    }

    public SingleMemberAnnotationExpressionVisitor() {}

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg) {
        n.getMemberValue();

        //        Individual annotationIndividual =
        //                ontology.getAnnotationTypeIndividualWithName(n.getNameAsString());
        //        ontology.setHasAnnotationInstanceForEntity(annotationIndividual, annotatedEntity);
        //
        annotationInstance = new AnnotationInstance(n.getNameAsString(), new ArrayList<>());

        // TODO Annotation Attributes
    }

    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
