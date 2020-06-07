package visitors;

import java.util.EnumSet;
import java.util.ListIterator;
import java.util.Optional;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import parser.IndividualCache;

public class MethodVisitor extends VoidVisitorAdapter<Void> {

	private OntModel ontoModel;
	private String ontologyNamespace;

	private OntClass methodClass;
	private Individual javaMethodIndividual;
	private DatatypeProperty ontoHasNameProperty;
	private ObjectProperty ontoDeclaresMethod;
	private Individual parentClass;
	private static int id;
	private DatatypeProperty sourceCode;

	public MethodVisitor(String ontologyNamespace, OntModel ontoModel, Individual parentClass) {
		this.parentClass = parentClass;
		this.ontoModel = ontoModel;
		this.ontologyNamespace = ontologyNamespace;
		ontoHasNameProperty = ontoModel.getDatatypeProperty(ontologyNamespace + "hasName");
		sourceCode = ontoModel.getDatatypeProperty(ontologyNamespace + "hasSourceCode");
		methodClass = ontoModel.getOntClass(ontologyNamespace + "Method");
	}

	public void visit(MethodDeclaration m, Void arg) {

		javaMethodIndividual = ontoModel.createIndividual(this.ontologyNamespace + "Method" + id, methodClass);
		id++;

		// name
		javaMethodIndividual.addLiteral(ontoHasNameProperty, m.getName().toString());

		// source code
		javaMethodIndividual.addLiteral(sourceCode, m.toString());

		// declares Method
		ontoDeclaresMethod = ontoModel.getObjectProperty(this.ontologyNamespace + "declaresMethod");
		parentClass.addProperty(ontoDeclaresMethod, javaMethodIndividual);
		
		//is declared by 
		javaMethodIndividual.addProperty(ontoModel.getObjectProperty(this.ontologyNamespace + "isDeclaredBy"), parentClass);

		// has Return Type
		DeclaredTypeVisitor returnTypeVisitor = new DeclaredTypeVisitor(ontoModel, ontologyNamespace);
		m.getType().accept(returnTypeVisitor, null);
		Individual returnType = returnTypeVisitor.getDeclaredType();
		ObjectProperty hasDeclaredType = ontoModel.getObjectProperty(this.ontologyNamespace + "hasDeclaredType");
		ObjectProperty dependsOnProp = ontoModel.getObjectProperty(this.ontologyNamespace + "dependsOn");
		if (returnType != null) {
			javaMethodIndividual.addProperty(hasDeclaredType, returnType);
			parentClass.addProperty(dependsOnProp, returnType);
			returnType.addProperty(ontoModel.getObjectProperty(ontologyNamespace + "isDependencyOf"), parentClass);

		} else {
			System.out.println("m.getType.asString" + (m.getType().asString()));
		}

		// annotations
		for (AnnotationExpr annotationExpr : m.getAnnotations()) {
			javaMethodIndividual.addLiteral(ontoModel.getProperty(ontologyNamespace + "isAnnotatedWith"),
					annotationExpr.toString());
		}

		// has modifiers
		EnumSet<Modifier> modifiers = m.getModifiers();
		DatatypeProperty modifierProperty = ontoModel.getDatatypeProperty(this.ontologyNamespace + "hasModifier");
		for (Modifier mod : modifiers) {
			javaMethodIndividual.addLiteral(modifierProperty, mod.asString());
		}

		// method declares Parameter
		NodeList<Parameter> parameters = m.getParameters();
		parameters.accept(new ParameterListVisitor(ontoModel, ontologyNamespace, javaMethodIndividual, parentClass),
				null);

		// m.getThrownExceptions();
		// m.getTypeParameters();

		Optional<BlockStmt> body = m.getBody();
		if (body.isPresent()) {
			BlockStmt blockStmt = body.get();
			// System.out.println("Visiting block statements of method: " + m.getName());
			blockStmt.accept(new BlockStatementVisitor(ontoModel, ontologyNamespace, javaMethodIndividual, parentClass),
					null);
		}

		for (ReferenceType referenceType : m.getThrownExceptions()) {
			// TODO
			// parent class throws
			// method throws
			// exception is thrown by class
			// exception is thrown by method
			Individual typeIndividual = IndividualCache.getInstance().getTypeIndividual(referenceType.asString());

			if (typeIndividual == null) {
				referenceType.getElementType().accept(new DeclaredTypeVisitor(ontoModel, ontologyNamespace), null);
				typeIndividual = IndividualCache.getInstance().getTypeIndividual(referenceType.asString());
			}
			typeIndividual.addProperty(ontoModel.getObjectProperty(ontologyNamespace + "isThrownBy"),
					javaMethodIndividual);
			typeIndividual.addProperty(ontoModel.getObjectProperty(ontologyNamespace + "isThrownBy"), parentClass);
		}

	}

}
