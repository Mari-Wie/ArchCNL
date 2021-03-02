package org.archcnl.owlify.famix.visitors;


import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorDeclarationVisitor extends VoidVisitorAdapter<Void> {
	
	private FamixOntology ontology;
	private Individual parent;

	public ConstructorDeclarationVisitor(FamixOntology ontology, Individual parent) {
		this.ontology = ontology;
		this.parent = parent;
	}
	
	@Override
	public void visit(ConstructorDeclaration n, Void arg) {
		
		Individual methodIndividual = ontology.getMethodIndividual();
		ontology.setHasNamePropertyForNamedEntity(n.getName().asString(), methodIndividual);
		
		ontology.setIsConstructorProperty(true,methodIndividual);


		ontology.setSignatureOfBehavioralEntity(n.getSignature().asString(), methodIndividual);

		for (Modifier modifier : n.getModifiers()) {
			ontology.setHasModifierForNamedEntity(modifier.toString(), methodIndividual);
		}

		for (AnnotationExpr annotationExpr : n.getAnnotations()) {
			annotationExpr.accept(new MarkerAnnotationExpressionVisitor(ontology, methodIndividual), null);
			annotationExpr.accept(new SingleMemberAnnotationExpressionVisitor(ontology, methodIndividual), null);
			annotationExpr.accept(new NormalAnnotationExpressionVisitor(ontology, methodIndividual), null);
		}

		ontology.setDefinesMethodPropertyForFamixType(parent, methodIndividual);

		for (Parameter parameter : n.getParameters()) {
			parameter.accept(new ParameterVisitor(ontology, methodIndividual), null);
		}
		DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);

		for (ReferenceType referenceType : n.getThrownExceptions()) {
			referenceType.accept(visitor, null);
			Individual thrownExceptionClass = visitor.getDeclaredType();

			Individual declaredExceptionIndividual = ontology.getDeclaredExceptionIndividual();
			ontology.setExceptionHasDefiningClass(declaredExceptionIndividual, thrownExceptionClass);
			ontology.setHasDeclaredExceptionProperty(declaredExceptionIndividual, methodIndividual);
		}

		BlockStmt body = n.getBody();
		for (Statement statement : body.getStatements()) {
			statement.accept(new TryCatchVisitor(ontology, methodIndividual), null);
			statement.accept(new ThrowStatementVisitor(ontology, methodIndividual), null);
			statement.accept(new LocalVariableVisitor(ontology, methodIndividual), null);
		}

	}

}
