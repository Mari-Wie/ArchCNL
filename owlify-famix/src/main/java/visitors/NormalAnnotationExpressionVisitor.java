package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class NormalAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {
	
	private FamixOntology ontology;
	private Individual annotatedEntity;
	
	public NormalAnnotationExpressionVisitor(FamixOntology famixOntology, Individual annotatedEntity) {
		
		this.ontology = famixOntology;
		this.annotatedEntity = annotatedEntity;
	
	}

	@Override
	public void visit(NormalAnnotationExpr n, Void arg) {
		System.out.println("normal");
		
		Individual annotationInstanceIndividual = ontology.getAnnotationInstanceIndividual();
		ontology.setHasAnnotationInstanceForEntity(annotationInstanceIndividual, annotatedEntity);
		
		Individual annotationType = ontology.getAnnotationTypeIndividualWithName(n.getName().asString());
		ontology.setHasAnnotationTypeProperty(annotationInstanceIndividual, annotationType);

		//TODO Annotation Attributes
//		n.getPairs();
	
	}
	
	

}
