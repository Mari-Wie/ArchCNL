package visitors;

import java.util.Optional;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class MethodDeclarationVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual parent;

	public MethodDeclarationVisitor(FamixOntology ontology, Individual parent) {
		this.ontology = ontology;
		this.parent = parent;
	}

	@Override
	public void visit(MethodDeclaration n, Void arg) {

		Individual methodIndividual = ontology.getMethodIndividual();
		ontology.setHasNamePropertyForNamedEntity(n.getName().asString(), methodIndividual);

		DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
		n.accept(visitor, null);
		ontology.setDeclaredTypeForBehavioralOrStructuralEntity(methodIndividual, visitor.getDeclaredType());

		ontology.setSignatureOfBehavioralEntity(n.getSignature().asString(), methodIndividual);

		for (Modifier modifier : n.getModifiers()) {
			ontology.setHasModifierForNamedEntity(modifier.asString(), methodIndividual);
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

		for (ReferenceType referenceType : n.getThrownExceptions()) {
			referenceType.accept(visitor, null);
			Individual thrownExceptionClass = visitor.getDeclaredType();

			// TODO or alternative: use object property definedByExceptionClass
			Individual declaredExceptionIndividual = ontology.getDeclaredExceptionIndividual();
			// TODO set defining class: thrownExpcetionClass
			ontology.setExceptionHasDefiningClass(declaredExceptionIndividual, thrownExceptionClass);
			ontology.setHasDeclaredExceptionProperty(declaredExceptionIndividual, methodIndividual);
		}

		Optional<BlockStmt> body = n.getBody();
		if(body.isPresent()) {
			
			for (Statement statement : body.get().getStatements()) {
				statement.accept(new TryCatchVisitor(ontology, methodIndividual), null);
				statement.accept(new ThrowStatementVisitor(ontology, methodIndividual), null);
				statement.accept(new LocalVariableVisitor(ontology, methodIndividual), null);
			}
		}

		// TODO
		// n.getTypeParameters();
	}
	
	

}
