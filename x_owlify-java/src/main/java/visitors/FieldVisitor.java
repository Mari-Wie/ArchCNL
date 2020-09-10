package visitors;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldVisitor extends VoidVisitorAdapter<Void> {

	private OntModel ontoModel;
	private String namespace;
	private Individual parentClass;
	private OntClass fieldClass;
	private ObjectProperty definesField;
	private static int id;

	public FieldVisitor(String ontologyNamespace, OntModel ontoModel, Individual javaClassIndividual) {
		this.ontoModel = ontoModel;
		this.namespace = ontologyNamespace;
		this.parentClass = javaClassIndividual;

		fieldClass = ontoModel.getOntClass(namespace + "Field");
		definesField = ontoModel.getObjectProperty(namespace + "definesField");
	}

	@Override
	public void visit(FieldDeclaration field, Void arg) {

		Individual fieldIndividual;
		id++;

		DeclaredTypeVisitor visitor = new DeclaredTypeVisitor(ontoModel, namespace);
		ObjectProperty dependsOnProperty = ontoModel.getObjectProperty(namespace+"dependsOn");
		ObjectProperty isDependencyOfProperty = ontoModel.getObjectProperty(namespace+"isDependencyOf");
		DatatypeProperty sourceCode = ontoModel.getDatatypeProperty(namespace+"hasSourceCode");
		
		for (VariableDeclarator var : field.getVariables()) {
			fieldIndividual = ontoModel.createIndividual(namespace + "Field" + id, fieldClass);
			var.getType().accept(visitor, null);

			Individual declaredType = visitor.getDeclaredType();

			if (declaredType != null) {// TODO implement remaining types 
				fieldIndividual.addProperty(ontoModel.getProperty(namespace + "hasDeclaredType"),
						visitor.getDeclaredType());
				//TODO add depends on property and its inverse is Dependency Of
//				OntologyInformation.getDependsOnProperty();
				parentClass.addProperty(dependsOnProperty, declaredType);
				declaredType.addProperty(isDependencyOfProperty, parentClass);
			}

			fieldIndividual.addLiteral(ontoModel.getProperty(namespace + "hasName"), var.getName().asString());
			fieldIndividual.addLiteral(sourceCode, field.toString());

			for (Modifier mod : field.getModifiers()) {
				fieldIndividual.addLiteral(ontoModel.getProperty(namespace + "hasModifier"), mod.asString());
			}
			
			for (AnnotationExpr annotationExpr : field.getAnnotations()) {
				
			}

			parentClass.addProperty(definesField, fieldIndividual);
		}
	}

}
