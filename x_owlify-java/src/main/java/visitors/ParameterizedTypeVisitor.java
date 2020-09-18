package visitors;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ParameterizedTypeVisitor extends VoidVisitorAdapter<Void> {
	
	private Individual currentType;
	private OntModel ontoModel;
	private String namespace;
	
	public ParameterizedTypeVisitor(Individual type, OntModel ontoModel, String namespace) {
		this.currentType = type;
		this.ontoModel = ontoModel;
		this.namespace = namespace;
	}

	
	@Override
	public void visit(NodeList n, Void arg) {
		// TODO Auto-generated method stub
		super.visit(n, arg);
	}

}
