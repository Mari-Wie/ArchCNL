package org.archcnl.kotlinparser.visitor;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinParser.ImportHeaderContext;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinParserBaseVisitor;

public class ImportDeclarationVisitor extends KotlinParserBaseVisitor<Void> {
    private List<Type> imports;

    public ImportDeclarationVisitor() {
        this.imports = new ArrayList<>();
    }

    @Override
    public Void visitImportHeader(ImportHeaderContext ctx) {
        var identifier = ctx.children.get(1);

        var importedType = identifier.getText();
        var simpleName = importedType.substring(importedType.lastIndexOf(".") + 1);
        imports.add(new Type(importedType, simpleName, false));

        return super.visitImportHeader(ctx);
    }

    public List<Type> getImports() {
        return imports;
    }
}
