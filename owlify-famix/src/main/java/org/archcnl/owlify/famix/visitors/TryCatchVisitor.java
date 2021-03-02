package org.archcnl.owlify.famix.visitors;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TryCatchVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual parent;

	public TryCatchVisitor(FamixOntology ontology, Individual parent) {
		this.ontology = ontology;
		this.parent = parent;
	}

	@Override
	public void visit(TryStmt n, Void arg) {

		for (CatchClause catchClause : n.getCatchClauses()) {
			DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
			catchClause.getParameter().getType().accept(visitor, null);
			Individual declaredTypeOfCatchClause = visitor.getDeclaredType();
			Individual caughtExceptionIndividual = ontology.getCaughtExceptionIndividual();
			ontology.setExceptionHasDefiningClass(caughtExceptionIndividual, declaredTypeOfCatchClause);
			ontology.setHasCaughtExceptionProperty(caughtExceptionIndividual,parent);
		}

	}

}
