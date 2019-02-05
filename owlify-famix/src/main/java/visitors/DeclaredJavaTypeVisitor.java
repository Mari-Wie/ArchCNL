package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class DeclaredJavaTypeVisitor extends VoidVisitorAdapter<Void> {

	private Individual declaredType;

	private FamixOntology ontology;

	public DeclaredJavaTypeVisitor(FamixOntology ontology) {
		this.ontology = ontology;
	}

	@Override
	public void visit(ClassOrInterfaceType n, Void arg) {

		String name = n.getName().asString();
		String ontologyClassOfFamixTypeIndividual = ontology
				.getOntologyClassOfFamixTypeIndividual(name);
		
		if(name.equals("String")) {
			declaredType = ontology.getPrimitiveTypeIndividual(name);
		}
		else if(ontologyClassOfFamixTypeIndividual == null) {
			//type not defined in source code, e.g. external library or java lib
			Individual tmp = ontology.getFamixClassWithName(n.getName().asString());
			ontology.setHasNamePropertyForNamedEntity(n.getName().asString(), tmp);
			ontology.setIsExternalTypeProperty(true, tmp);			
			declaredType = tmp;
		}
		else if (ontologyClassOfFamixTypeIndividual.equals("FamixClass")) {

			declaredType = ontology.getFamixClassWithName(n.getName().asString());
		} else if (ontologyClassOfFamixTypeIndividual.equals("Enum")) {
			declaredType = ontology.getEnumTypeIndividualWithName(n.getName().asString());
		} else if (ontologyClassOfFamixTypeIndividual.equals("AnnotationType")) {
			declaredType = ontology.getAnnotationTypeIndividualWithName(n.getName().asString());
		} 

	}

	@Override
	public void visit(PrimitiveType n, Void arg) {
		declaredType = ontology.getPrimitiveTypeIndividual(n.asString());
	}
	
	@Override
	public void visit(VoidType n, Void arg) {
		declaredType = ontology.getPrimitiveTypeIndividual(n.asString());
	}

	public Individual getDeclaredType() {
		return declaredType;
	}

}
