package org.archcnl.owlify.famix.visitors;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MarkerAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual annotatedEntity;

	public MarkerAnnotationExpressionVisitor(FamixOntology famixOntology, Individual annotatedEntity) {
		this.ontology = famixOntology;
		this.annotatedEntity = annotatedEntity;
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Void arg) {
		
		Individual annotationInstanceIndividual = ontology.getAnnotationInstanceIndividual();
		ontology.setHasAnnotationInstanceForEntity(annotationInstanceIndividual, annotatedEntity);
		
		Individual annotationType = ontology.getAnnotationTypeIndividualWithName(n.getName().asString());
		ontology.setHasAnnotationTypeProperty(annotationInstanceIndividual, annotationType);

	}

}
