package org.archcnl.owlify.famix.visitors;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaFieldVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual parent;

	public JavaFieldVisitor(FamixOntology ontology, Individual parent) {
		this.ontology = ontology;
		this.parent = parent;
	}

	@Override
	public void visit(FieldDeclaration n, Void arg) {
		DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);

		for (VariableDeclarator variableDeclarator : n.getVariables()) {
			variableDeclarator.accept(visitor, null);
			Individual fieldIndividual = ontology.getAttributeIndividual();
			ontology.setHasNamePropertyForNamedEntity(variableDeclarator.getName().asString(), fieldIndividual);
			ontology.setDefinesAttributePropertyForType(parent, fieldIndividual);
			ontology.setDeclaredTypeForBehavioralOrStructuralEntity(fieldIndividual, visitor.getDeclaredType());

			for (Modifier modifier : n.getModifiers()) {
				ontology.setHasModifierForNamedEntity(modifier.toString(), fieldIndividual);
			}

			for (AnnotationExpr annotationExpr : n.getAnnotations()) {
				annotationExpr.accept(new MarkerAnnotationExpressionVisitor(ontology, fieldIndividual), null);
				annotationExpr.accept(new SingleMemberAnnotationExpressionVisitor(ontology, fieldIndividual), null);
				annotationExpr.accept(new NormalAnnotationExpressionVisitor(ontology, fieldIndividual), null);
			}

		}

	}

}
