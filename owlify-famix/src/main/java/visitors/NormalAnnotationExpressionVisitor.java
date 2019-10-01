package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
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

		Individual annotationInstanceIndividual = ontology.getAnnotationInstanceIndividual();
		ontology.setHasAnnotationInstanceForEntity(annotationInstanceIndividual, annotatedEntity);

		Individual annotationType = ontology.getAnnotationTypeIndividualWithName(n.getName().asString());
		ontology.setHasAnnotationTypeProperty(annotationInstanceIndividual, annotationType);
		Individual annotationTypeAttributeIndividual = null;
		for (MemberValuePair memberValuePair : n.getPairs()) {
			Expression value = memberValuePair.getValue();
			Individual annotationTypeAttribute = ontology.getAnnotationTypeAttributeOfAnnotationTypeByName(
					memberValuePair.getName().asString(), annotationType);
			if(annotationTypeAttribute == null) {
				annotationTypeAttribute = ontology.createAnnotationTypeAttributeIndividual();
				 ontology.setHasNamePropertyForNamedEntity(memberValuePair.getName().asString(),annotationTypeAttribute);				
			}
			Individual annotationInstanceAttribute = ontology.getAnnotationInstanceAttributeIndividual();
			ontology.setHasAnnotationTypeAttributeForAnnotationInstanceAttribute(annotationTypeAttribute,
					annotationInstanceAttribute);
			ontology.setHasAnnotationInstanceAttributeForAnnotationInstance(annotationInstanceAttribute,
					annotationInstanceIndividual);
			ontology.setHasValueForAnnotationInstanceAttribute(annotationInstanceAttribute, value.toString());
		}

	}

}