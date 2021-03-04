package org.archcnl.owlify.famix.visitors;

import org.apache.jena.ontology.Individual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.ontology.FamixOntology;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;

public class DeclaredJavaTypeVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(DeclaredJavaTypeVisitor.class);
    
	private Individual declaredType;

	private FamixOntology ontology;

	public DeclaredJavaTypeVisitor(FamixOntology ontology) {
		this.ontology = ontology;
	}

	@Override
	public void visit(ClassOrInterfaceType n, Void arg) {

	    String name;
		try {
		    name = n.resolve().asReferenceType().getQualifiedName();

		} catch (UnsolvedSymbolException | UnsupportedOperationException e) {
			name = n.getName().asString();
			LOG.debug("Unable to resolve type, using simple name instead: %s", name);
		} 
        declaredType = ontology.getTypeIndividualFor(name);
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
