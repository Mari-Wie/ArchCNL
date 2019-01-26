package visitors;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TypeVisitor extends VoidVisitorAdapter<Void> {
	
	private Individual currentType;
	private OntModel ontoModel;
	private String namespace;
	
	
	public TypeVisitor(Individual type, OntModel ontoModel, String namespace) {
		this.currentType = type;
		this.ontoModel = ontoModel;
		this.namespace = namespace;
	}

	@Override
	public void visit(SimpleName n, Void arg) {
		
		DatatypeProperty hasName = ontoModel.getDatatypeProperty(namespace + "hasName");
		currentType.addLiteral(hasName, n.asString());
		
	}

}
