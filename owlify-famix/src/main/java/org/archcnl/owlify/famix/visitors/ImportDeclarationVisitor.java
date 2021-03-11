package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class ImportDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual importingUnit;

    public ImportDeclarationVisitor(FamixOntology ontology, Individual currentUnitIndividual) {
        this.ontology = ontology;
        this.importingUnit = currentUnitIndividual;
    }

    @Override
    public void visit(ImportDeclaration n, Void arg) {

        //		Individual importedType = ontology.getFamixClassWithName(n.getName().getIdentifier());
        Individual importedType = ontology.getFamixClassWithName(n.getName().toString());
        ontology.setHasFullQualifiedNameForType(importedType, n.getName().asString());
        ontology.setImports(importingUnit, importedType);
        super.visit(n, arg);
    }
}
