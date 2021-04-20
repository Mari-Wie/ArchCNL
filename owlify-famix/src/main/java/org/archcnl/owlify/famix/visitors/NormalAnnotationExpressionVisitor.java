package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class NormalAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual annotatedEntity;
    private AnnotationInstance annotationInstance;

    public NormalAnnotationExpressionVisitor(
            FamixOntology famixOntology, Individual annotatedEntity) {

        this.ontology = famixOntology;
        this.annotatedEntity = annotatedEntity;
    }

    public NormalAnnotationExpressionVisitor() {}

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        //        ontology.getAnnotationInstanceIndividual(n.getName().asString(), annotatedEntity,
        // n.getPairs());

        List<AnnotationMemberValuePair> values =
                n.getPairs().stream()
                        .map(
                                pair ->
                                        new AnnotationMemberValuePair(
                                                pair.getNameAsString(), pair.getValue().toString()))
                        .collect(Collectors.toList());

        annotationInstance = new AnnotationInstance(n.getName().asString(), values);
    }

    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }
}
