package visitors;

import java.util.EnumSet;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class ClassVisitor extends VoidVisitorAdapter<Void> {

	private Individual famixTypeIndividual;
	private FamixOntology ontology;

	public ClassVisitor(String famixOntology) {
		ontology = new FamixOntology(famixOntology);
	}

	public ClassVisitor(FamixOntology ontology) {
		 this.ontology = ontology;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {
		famixTypeIndividual = ontology.getFamixClassWithName(n.getName().asString());

		// data type properties

		// set name
		String typeName = n.getName().asString();
		ontology.setHasNamePropertyForNamedEntity(typeName, famixTypeIndividual);

		// set if interface
		boolean isInterface = n.isInterface();
		ontology.setIsInterfaceForFamixClass(isInterface, famixTypeIndividual);

		// set source code -> compilationUnit
//		System.out.println(n);

		// set modifiers
		EnumSet<Modifier> modifiers = n.getModifiers();
		for (Modifier modifier : modifiers) {
			ontology.setHasModifierForNamedEntity(modifier.toString(), famixTypeIndividual);
		}
		
		

		// parameterized type
		// n.getTypeParameters()


		n.getConstructors();
	}

	@Override
	public void visit(EnumDeclaration n, Void arg) {
		famixTypeIndividual = ontology.getEnumTypeIndividualWithName(n.getName().asString());

		// set name
		String typeName = n.getName().asString();
		ontology.setHasNamePropertyForNamedEntity(typeName, famixTypeIndividual);

		// Enum members
		// n.getMembers()
	}

	@Override
	public void visit(AnnotationDeclaration n, Void arg) {
		famixTypeIndividual = ontology.getAnnotationTypeIndividualWithName(n.getName().asString());

		// set name
		String typeName = n.getName().asString();
		ontology.setHasNamePropertyForNamedEntity(typeName, famixTypeIndividual);
		
	}
	

	public Individual getFamixTypeIndividual() {
		return famixTypeIndividual;
	}

}
