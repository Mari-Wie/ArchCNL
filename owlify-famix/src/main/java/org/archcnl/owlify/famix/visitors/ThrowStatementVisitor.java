package org.archcnl.owlify.famix.visitors;

import org.apache.jena.ontology.Individual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ThrowStatementVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(ThrowStatementVisitor.class);
    
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
		
		if(typeOfThrownException == null) { 
		    LOG.debug("Throw statement does not match \"throw new X()\", using fall back solution: %s", n.toString());
		    Expression throwExpression = n.getExpression();
		    String typeName = throwExpression.calculateResolvedType().asReferenceType().getQualifiedName();
		    typeOfThrownException = ontology.getTypeIndividualFor(typeName);
		}
		ontology.setExceptionHasDefiningClass(thrownExceptionIndividual, typeOfThrownException);  
		ontology.setThrowsExceptionProperty(thrownExceptionIndividual,parent);
	}
	
	
}
