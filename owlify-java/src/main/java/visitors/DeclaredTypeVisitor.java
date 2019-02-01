package visitors;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ids.GlobalJavaClassId;
import parser.IndividualCache;

public class DeclaredTypeVisitor extends VoidVisitorAdapter<Void> {

	private OntModel ontoModel;
	private String namespace;
	private Individual declaredType;
	private static int id;

	public DeclaredTypeVisitor(OntModel ontoModel, String ontologyNamespace) {
		this.ontoModel = ontoModel;
		this.namespace = ontologyNamespace;
	}

	@Override
	public void visit(ClassOrInterfaceType n, Void arg) {

		declaredType = IndividualCache.getInstance().getTypeIndividual(n.getName().asString());
		if (declaredType == null) {
			declaredType = resolveOntologyClassOfType(n);
			IndividualCache.getInstance().updateTypes(n.getName().asString(), declaredType);
		}

	}

	private Individual resolveOntologyClassOfType(ClassOrInterfaceType n) {

		Individual i;
		if (n.getTypeArguments().isPresent()) {
			// is a parameterized type
			OntClass parameterizedType = ontoModel.getOntClass(this.namespace + "ParameterizedType");
			i = ontoModel.createIndividual(this.namespace + "ParameterizedType" + id, parameterizedType);
			n.accept(new ParameterizedTypeVisitor(i, ontoModel, namespace), null);
			i.addLiteral(ontoModel.getDatatypeProperty(this.namespace+"hasName"), n.getName().asString());
			id++;
		} else {
			OntClass type = ontoModel.getOntClass(this.namespace + "JavaClass");
			i = ontoModel.createIndividual(this.namespace + n.getName().toString() + GlobalJavaClassId.get(), type);
			i.addLiteral(ontoModel.getDatatypeProperty(this.namespace+"hasName"), n.getName().asString());
		}
		
		return i;
	}
	

	@Override
	public void visit(PrimitiveType n, Void arg) {

		declaredType = IndividualCache.getInstance().getTypeIndividual(n.asString());

		if (declaredType == null) {
			OntClass primitiveType = ontoModel.getOntClass(this.namespace + "PrimitiveType");
			declaredType = ontoModel.createIndividual(this.namespace + "PrimitiveType" + id, primitiveType);
			id++;
			IndividualCache.getInstance().updateTypes(n.asString(), declaredType);
			declaredType.addLiteral(ontoModel.createDatatypeProperty(namespace+"hasName"), n.asString());
		}
	}
	
	@Override
	public void visit(ArrayType n, Void arg) {
		//TODO
//		System.out.println("Array types to be implemented");
	}

	@Override
	public void visit(IntersectionType n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("intersection");

	}

	@Override
	public void visit(UnionType n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("union type");
	}

	@Override
	public void visit(VoidType n, Void arg) {
		declaredType = IndividualCache.getInstance().getTypeIndividual(n.asString());
		if (declaredType == null) {
			OntClass voidType = ontoModel.getOntClass(this.namespace + "VoidType");
			declaredType = ontoModel.createIndividual(this.namespace + "VoidType" + id, voidType);
			id++;
			IndividualCache.getInstance().updateTypes(n.asString(), declaredType);
			declaredType.addLiteral(ontoModel.createDatatypeProperty(namespace+"hasName"), n.asString());
		}

	}

	@Override
	public void visit(WildcardType n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("wild card type");
	}

	@Override
	public void visit(UnknownType n, Void arg) {
		// TODO Auto-generated method stub
		System.out.println("unknown type");
	}

	public Individual getDeclaredType() {
		return declaredType;
	}

}
