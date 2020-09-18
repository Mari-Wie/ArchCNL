package visitors;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ids.GlobalEnumId;
import ids.GlobalJavaClassId;
import parser.IndividualCache;

public class ClassVisitor extends VoidVisitorAdapter<Void> {

	private OntClass ontoJavaClass;
	private OntClass ontoEnum;
	private Individual javaClassIndividual;
	private DatatypeProperty ontoHasNameProperty;
	private DatatypeProperty ontoHasSourceCodeProperty;
	private OntModel ontoModel;
	private String ontologyNamespace;
	private String sourceCode;

	public ClassVisitor(String codeOntologyNamespace, OntModel ontoModel, String sourceCode) {
		this.ontoModel = ontoModel;
		this.ontologyNamespace = codeOntologyNamespace;
		this.sourceCode = sourceCode;
		ontoHasNameProperty = ontoModel.getDatatypeProperty(codeOntologyNamespace + "hasName");
		ontoHasSourceCodeProperty = ontoModel.getDatatypeProperty(codeOntologyNamespace + "hasSourceCode");

	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {

		ontoJavaClass = this.ontoModel.getOntClass(this.ontologyNamespace + "JavaClass");

		javaClassIndividual = IndividualCache.getInstance().getTypeIndividual(n.getName().toString());
		if (javaClassIndividual == null) {
			javaClassIndividual = this.ontoModel
					.createIndividual(this.ontologyNamespace + n.getName().toString() + GlobalJavaClassId.get(), ontoJavaClass);
			javaClassIndividual.addProperty(ontoHasSourceCodeProperty, this.sourceCode);
			IndividualCache.getInstance().updateTypes(n.getName().toString(), javaClassIndividual);
		}

		// name
		javaClassIndividual.addLiteral(ontoHasNameProperty, n.getName().toString());

//		System.out.println(javaClassIndividual);
		if (n.isInterface()) {
			// TODO add literal isInterface
		}

		for (Modifier modifier : n.getModifiers()) {
			javaClassIndividual.addLiteral(ontoModel.getProperty(ontologyNamespace + "hasModifier"),
					modifier.asString());
		}
		
		AnnotationTypeVisitor annotationVisitor = new AnnotationTypeVisitor(ontoModel, ontologyNamespace);
		for (AnnotationExpr annotationExpr : n.getAnnotations()) {
			javaClassIndividual.addLiteral(ontoModel.getProperty(ontologyNamespace + "isAnnotatedWith"),
					annotationExpr.toString());
			annotationExpr.accept(annotationVisitor, null);
		}
		
		
		Individual declaredType;
		ObjectProperty extendsProp = ontoModel.getObjectProperty(ontologyNamespace+"extends");
		ObjectProperty implementsProperty = ontoModel.getObjectProperty(ontologyNamespace+"implements");
		NodeList<ClassOrInterfaceType> extendedTypes = n.getExtendedTypes();
		DeclaredTypeVisitor visitor = new DeclaredTypeVisitor(ontoModel, ontologyNamespace);
		for (ClassOrInterfaceType classOrInterfaceType : extendedTypes) {
			classOrInterfaceType.accept(visitor,
					null);
			//add extend relation
			declaredType = visitor.getDeclaredType();
			javaClassIndividual.addProperty(extendsProp, declaredType);
		}
		NodeList<ClassOrInterfaceType> implementedTypes = n.getImplementedTypes();
		for (ClassOrInterfaceType classOrInterfaceType : implementedTypes) {
			classOrInterfaceType.accept(visitor, null);
			declaredType = visitor.getDeclaredType();
			//add implements relation
			javaClassIndividual.addProperty(implementsProperty, declaredType);
		}
		
	}

	@Override
	public void visit(EnumDeclaration n, Void arg) {

		ontoEnum = this.ontoModel.getOntClass(ontologyNamespace + "EnumType");
		Individual ontoEnumIndividual = IndividualCache.getInstance().getTypeIndividual(n.getName().asString());
		if (ontoEnumIndividual == null) {
			ontoEnumIndividual = this.ontoModel.createIndividual(ontologyNamespace + n.getName().toString() + "Enum" + GlobalEnumId.get(),
					ontoEnum);
			IndividualCache.getInstance().updateTypes(n.getName().toString(), ontoEnumIndividual);
		}

		ontoEnumIndividual.addLiteral(ontoHasNameProperty, n.getName().asString());
		for (Modifier modifier : n.getModifiers()) {
			ontoEnum.addLiteral(ontoModel.getProperty(ontologyNamespace + "hasModifier"), modifier.asString());
		}

		// TODO
		// for (EnumConstantDeclaration enumConstantDeclaration : n.getEntries()) {
		// enumConstantDeclaration.getName();
		// }
	}

	public Individual getJavaClassIndividual() {
		return javaClassIndividual;
	}

}
