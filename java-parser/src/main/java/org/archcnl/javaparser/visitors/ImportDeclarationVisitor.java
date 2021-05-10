package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.Type;

/** Parses the import statements in the given unit. */
public class ImportDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private List<Type> imports;

    public ImportDeclarationVisitor() {
        this.imports = new ArrayList<>();
    }

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        String importedType = n.getNameAsString();

        if (n.isStatic()) {
            // static import: package.subpackage.Class.MethodOrField;
            // => removing the substring from the last "." yields the class of the imported
            //    field or method
            importedType = importedType.substring(0, importedType.lastIndexOf("."));
        }

        String simpleName = importedType.substring(importedType.lastIndexOf(".") + 1);
        imports.add(
                new Type(importedType, simpleName, false)); // primitive types cannot be imported
        super.visit(n, arg); // continue visiting
    }

    /** @return the imported types */
    public List<Type> getImports() {
        return imports;
    }
}
