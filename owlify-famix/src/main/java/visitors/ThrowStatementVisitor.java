package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class ThrowStatementVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual parent;

	public ThrowStatementVisitor(FamixOntology ontology, Individual parent) {
		this.ontology = ontology;
		this.parent = parent;
	}

	@Override
	public void visit(ThrowStmt n, Void arg) {
		ObjectCreationExpressionVisitor typeOfCreatedObjectVisitor = new ObjectCreationExpressionVisitor(ontology);
		n.getExpression().accept(typeOfCreatedObjectVisitor, null);
		Individual typeOfThrownException = typeOfCreatedObjectVisitor.getTypeOfCreatedObject();
		
		Individual thrownExceptionIndividual = ontology.getThrownExceptionIndividual();
		if(typeOfThrownException != null) { //TODO: throw <variable> instead of throw new ... breaks the transformation
			ontology.setExceptionHasDefiningClass(thrownExceptionIndividual, typeOfThrownException);			
		}
		ontology.setThrowsExceptionProperty(thrownExceptionIndividual,parent);
	}
	
	
}
