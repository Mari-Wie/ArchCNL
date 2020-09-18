package visitors;

import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class TypeParameterVisitor extends VoidVisitorAdapter<Void> {
	
	private FamixOntology ontology;

	public TypeParameterVisitor(FamixOntology ontology) {
		this.ontology = ontology;
	}
	
	@Override
	public void visit(TypeParameter n, Void arg) {
		System.out.println(n.getName());
	}

}
