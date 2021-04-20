package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class ImportDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual importingUnit;
    private List<Type> imports;

    public ImportDeclarationVisitor(FamixOntology ontology, Individual currentUnitIndividual) {
        this.ontology = ontology;
        this.importingUnit = currentUnitIndividual;
        this.imports = new ArrayList<>();
    }

    public ImportDeclarationVisitor() {
        this.imports = new ArrayList<>();
    }

    @Override
    public void visit(ImportDeclaration n, Void arg) {

        //        Individual importedType =
        // ontology.getReferenceTypeIndividual(n.getName().toString());

        String importedType = n.getNameAsString();

        if (n.isStatic()) {
            // static import: package.subpackage.Class.MethodOrField;
            // => removing the substring from the last "." yields the class of the imported
            //    field or method
            importedType = importedType.substring(0, importedType.lastIndexOf("."));
        }

        imports.add(new Type(importedType, false)); // primitive types cannot be imported

        //        ontology.setImports(importingUnit, importedType);
        super.visit(n, arg);
    }

    public List<Type> getImports() {
        return imports;
    }
}
