package visitors;

import org.apache.jena.ontology.Individual;

//import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;

import ontology.FamixOntology;

public class DeclaredJavaTypeVisitor extends VoidVisitorAdapter<Void> {

	private Individual declaredType;

	private FamixOntology ontology;

	public DeclaredJavaTypeVisitor(FamixOntology ontology) {
		this.ontology = ontology;
	}

	@Override
	public void visit(ClassOrInterfaceType n, Void arg) {

//		String name = n.getName().asString();
		String name = null;
		try {
			name = n.resolve().asReferenceType().getQualifiedName();
			createTypeIndividual(n, name);

		} catch (UnsolvedSymbolException e) {
//			System.out.println("Unable to resolve type: " + e.getName() + " using simple name instead.");
			name = n.getName().asString();
			createTypeIndividual(n, name);
		} catch (UnsupportedOperationException e) {
//			System.out
//					.println("UnsupportedOperatuibException for type: " + n.getName() + " using simple name instead.");
			name = n.getName().asString();
			createTypeIndividual(n, name);
		}

	}

	private void createTypeIndividual(ClassOrInterfaceType n, String name) {
		String ontologyClassOfFamixTypeIndividual = ontology.getOntologyClassOfFamixTypeIndividual(name);

		if (isPrimitiveType(name)) {
			declaredType = ontology.getPrimitiveTypeIndividual(name);
		} else if (ontologyClassOfFamixTypeIndividual == null) {
			// type not defined in source code, e.g. external library or java lib
			Individual tmp = ontology.getFamixClassWithName(name);
			ontology.setHasNamePropertyForNamedEntity(name, tmp);
//			ontology.setHasFullQualifiedNameForType(tmp, name);
			ontology.setIsExternalTypeProperty(true, tmp);
			declaredType = tmp;
		} else if (ontologyClassOfFamixTypeIndividual.equals("FamixClass")) {

			declaredType = ontology.getFamixClassWithName(name);
		} else if (ontologyClassOfFamixTypeIndividual.equals("Enum")) {
			declaredType = ontology.getEnumTypeIndividualWithName(name);
		} else if (ontologyClassOfFamixTypeIndividual.equals("AnnotationType")) {
			declaredType = ontology.getAnnotationTypeIndividualWithName(name);
		}
	}

	private boolean isPrimitiveType(String name) {
		return name.equals("double") || name.equals("char") || name.equals("byte") || name.equals("String")
				|| name.equals("int") || name.equals("boolean") || name.equals("Integer");
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
