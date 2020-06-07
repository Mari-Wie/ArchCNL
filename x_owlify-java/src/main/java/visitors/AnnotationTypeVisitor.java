package visitors;

import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class AnnotationTypeVisitor extends VoidVisitorAdapter<Void> {
	
	private OntModel ontoModel;
	private String namespace;
	
	public AnnotationTypeVisitor(OntModel ontoModel, String ontologyNamespace) {
		
		this.ontoModel = ontoModel;
		this.namespace = ontologyNamespace;
	
	}
	
	@Override
	public void visit(AnnotationDeclaration n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("Annotation: " + n.getName());
		super.visit(n, arg);
	}
	
	@Override
	public void visit(AnnotationMemberDeclaration n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("annotation: " + n.getName());
		super.visit(n, arg);
	}

}
