package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class InheritanceVisitor extends VoidVisitorAdapter<Void>{
	
	private FamixOntology ontology;
	
	private Individual subClass;
	
	public InheritanceVisitor(FamixOntology ontology, Individual subClass) {
		this.ontology = ontology;
		this.subClass = subClass;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {

		DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
		NodeList<ClassOrInterfaceType> extendedTypes = n.getExtendedTypes();
		for (ClassOrInterfaceType classOrInterfaceType : extendedTypes) {
			classOrInterfaceType.accept(visitor, null);
			Individual declaredType = visitor.getDeclaredType();
			ontology.setInheritanceBetweenSubClassAndSuperClass(declaredType, subClass);
		}

		NodeList<ClassOrInterfaceType> implementedTypes = n.getImplementedTypes();
		for (ClassOrInterfaceType classOrInterfaceType : implementedTypes) {
			classOrInterfaceType.accept(visitor, null);
			Individual declaredType = visitor.getDeclaredType();
			ontology.setInheritanceBetweenSubClassAndSuperClass(declaredType, subClass);
		}

	
	}

}
