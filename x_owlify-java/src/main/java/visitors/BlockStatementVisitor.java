package visitors;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import parser.IndividualCache;

public class BlockStatementVisitor extends VoidVisitorAdapter<Void> {
	
	private OntModel ontoModel;
	private String ontoNamespace;
	private Individual methodIndividual;
	private NodeList<VariableDeclarator> variables;
	private Individual parentClass;
	private static int id;

	public BlockStatementVisitor(OntModel ontoModel, String ontoNamespace, Individual methodIndividual, Individual parentClass) {
		this.ontoModel = ontoModel;
		this.ontoNamespace = ontoNamespace;
		this.methodIndividual = methodIndividual;
		this.parentClass = parentClass;
	}
	
	@Override
	public void visit(VariableDeclarationExpr n, Void arg) {
		
		OntClass localVariableClass = ontoModel.getOntClass(ontoNamespace + "LocalVariable");
		DatatypeProperty hasNameProperty = ontoModel.getDatatypeProperty(ontoNamespace + "hasName"); 
		ObjectProperty hasDeclaredTypeProperty = ontoModel.getObjectProperty(ontoNamespace + "hasDeclaredType");
		ObjectProperty hasLocalVariableProperty = ontoModel.getObjectProperty(ontoNamespace + "definesLocalVariable");
		DatatypeProperty hasSourceCodeProperty = ontoModel.getDatatypeProperty(ontoNamespace + "hasSourceCode");
		Individual localVariableIndividual;
		id++;
		
		variables = n.getVariables();
		for (VariableDeclarator variableDeclarator : variables) {
			localVariableIndividual = ontoModel.createIndividual(ontoNamespace + "LocalVariable" + id, localVariableClass);
			localVariableIndividual.addLiteral(hasNameProperty, variableDeclarator.getName().toString());
			localVariableIndividual.addLiteral(hasSourceCodeProperty, variableDeclarator.toString());
			Individual declaredTypeOfLocalVariableIndividual = IndividualCache.getInstance().getTypeIndividual(variableDeclarator.getType().toString());
			if(declaredTypeOfLocalVariableIndividual==null) {
				variableDeclarator.getType().accept(new DeclaredTypeVisitor(ontoModel, ontoNamespace), null);
			}
			declaredTypeOfLocalVariableIndividual = IndividualCache.getInstance().getTypeIndividual(variableDeclarator.getType().toString());
			if(declaredTypeOfLocalVariableIndividual != null) {
				localVariableIndividual.addProperty(hasDeclaredTypeProperty, declaredTypeOfLocalVariableIndividual);
				parentClass.addProperty(ontoModel.getObjectProperty(ontoNamespace+"dependsOn"), declaredTypeOfLocalVariableIndividual);
				declaredTypeOfLocalVariableIndividual.addProperty(ontoModel.getObjectProperty(ontoNamespace+"isDependencyOf"), parentClass);
			}
			//TODO modifier
			
			methodIndividual.addProperty(hasLocalVariableProperty, localVariableIndividual);
		}
	}

}
