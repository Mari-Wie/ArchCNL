package visitors;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ids.GlobalJavaClassId;
import parser.IndividualCache;

public class ParameterListVisitor extends VoidVisitorAdapter<Void> {

	private Individual method;
	private OntModel ontoModel;
	private String namespace;
	private static int id;
	private Individual parentClass;

	public ParameterListVisitor(OntModel ontoModel, String ontologyNamespace, Individual javaMethodIndividual, Individual parentClass) {

		this.ontoModel = ontoModel;
		this.namespace = ontologyNamespace;
		this.method = javaMethodIndividual;
		this.parentClass = parentClass;

	}

	@Override
	public void visit(Parameter n, Void arg) {

		OntClass parameterClass = ontoModel.getOntClass(namespace + "Parameter");
		ObjectProperty hasParameter = ontoModel.getObjectProperty(namespace + "hasParameter");
		Individual parameterIndividual = ontoModel.createIndividual(namespace + "Parameter" + id, parameterClass);
		id++;
		Individual parameterType = IndividualCache.getInstance().getTypeIndividual(n.getType().asString());
		if (parameterType == null) {
//			OntClass type = ontoModel.getOntClass(this.namespace + "JavaClass");
//			parameterType = ontoModel.createIndividual(this.namespace + "JavaClass" + GlobalJavaClassId.get(), type);
			// TODO
			// hasDeclaredType
			DeclaredTypeVisitor declaredTypeVisitor = new DeclaredTypeVisitor(ontoModel, namespace);
			n.getType().accept(declaredTypeVisitor, null);
			parameterType = declaredTypeVisitor.getDeclaredType();
		}
		if(parameterType!=null) { //TODO: einige Parametertypen noch nicht abgedeckt 
			parameterIndividual.addProperty(ontoModel.getProperty(namespace+"hasDeclaredType"), parameterType);	
			parentClass.addProperty(ontoModel.getObjectProperty(namespace+"dependsOn"), parameterType);
			parameterType.addProperty(ontoModel.getObjectProperty(namespace+"isDependencyOf"), parentClass);
		}
		parameterIndividual.addLiteral(ontoModel.getDatatypeProperty(namespace+"hasName"),n.getName().asString());
		method.addProperty(hasParameter, parameterIndividual);
	}

}
