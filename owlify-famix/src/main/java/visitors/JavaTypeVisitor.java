package visitors;

import java.io.InputStream;

//import java.util.EnumSet;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class JavaTypeVisitor extends VoidVisitorAdapter<Void> {

	private Individual famixTypeIndividual;
	private String famixTypeName;
	private FamixOntology ontology;

	public JavaTypeVisitor(InputStream famixOntologyInputStream) {
		ontology = new FamixOntology(famixOntologyInputStream);
	}

	public JavaTypeVisitor(FamixOntology ontology) {
		 this.ontology = ontology;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {
//		famixTypeIndividual = ontology.getFamixClassWithName(n.getName().asString());
		famixTypeIndividual = ontology.getFamixClassWithName(n.resolve().getQualifiedName());

		// data type properties

		// set name
		//famixTypeName = n.getName().asString();
		famixTypeName = n.resolve().getQualifiedName();
		ontology.setHasNamePropertyForNamedEntity(famixTypeName, famixTypeIndividual);

		// set if interface
		boolean isInterface = n.isInterface();
		ontology.setIsInterfaceForFamixClass(isInterface, famixTypeIndividual);

		// set source code -> compilationUnit
//		System.out.println(n);

		// set modifiers
		NodeList<Modifier> modifiers = n.getModifiers();
		for (Modifier modifier : modifiers) {
			ontology.setHasModifierForNamedEntity(modifier.toString(), famixTypeIndividual);
		}
		
		super.visit(n, null);

		// parameterized type
		// n.getTypeParameters()


	}

	@Override
	public void visit(EnumDeclaration n, Void arg) {
		System.out.println("Enum: " + n.resolve().getQualifiedName());
		famixTypeIndividual = ontology.getEnumTypeIndividualWithName(n.resolve().getQualifiedName());

		// set name
		famixTypeName = n.resolve().getQualifiedName();
		ontology.setHasNamePropertyForNamedEntity(famixTypeName, famixTypeIndividual);

		// Enum members
		// n.getMembers()
		
		super.visit(n, null);
	}

	@Override
	public void visit(AnnotationDeclaration n, Void arg) {
		System.out.println("Annotation: " + n.resolve().getQualifiedName());
		famixTypeIndividual = ontology.getAnnotationTypeIndividualWithName(n.resolve().getQualifiedName());

		// set name
		famixTypeName = n.resolve().getQualifiedName();
		ontology.setHasNamePropertyForNamedEntity(famixTypeName, famixTypeIndividual);
		
		DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
		for (BodyDeclaration<?> bodyDeclaration : n.getMembers()) {
			if(bodyDeclaration instanceof AnnotationMemberDeclaration) {
				AnnotationMemberDeclaration annotationMember = (AnnotationMemberDeclaration) bodyDeclaration;
				//System.out.println(annotationMember.getType() + " " + annotationMember.getName());
				Individual annotationTypeAttributeIndividual = ontology.createAnnotationTypeAttributeIndividual();
				ontology.setHasNamePropertyForNamedEntity(annotationMember.getName().asString(), annotationTypeAttributeIndividual);
				annotationMember.getType().accept(visitor, null);
				Individual declaredType = visitor.getDeclaredType();
				ontology.setDeclaredTypeForBehavioralOrStructuralEntity(annotationTypeAttributeIndividual, declaredType);
				ontology.setHasAnnotationTypeAttributeForAnnotationType(famixTypeIndividual,annotationMember.getName().asString(),annotationTypeAttributeIndividual);
			}
		}
		
		super.visit(n, null);
		
	}
	

	public Individual getFamixTypeIndividual() {
		return famixTypeIndividual;
	}
	
	public String getNameOfFamixType() {
		return famixTypeName;
	}

}
