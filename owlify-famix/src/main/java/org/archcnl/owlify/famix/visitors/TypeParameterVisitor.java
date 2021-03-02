package org.archcnl.owlify.famix.visitors;

import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TypeParameterVisitor extends VoidVisitorAdapter<Void> {
	
	private FamixOntology ontology;

	public TypeParameterVisitor(FamixOntology ontology) {
		this.ontology = ontology;
	}
	
	@Override
	public void visit(TypeParameter n, Void arg) {
		// TODO: implement
		System.out.println(n.getName());
	}

}
