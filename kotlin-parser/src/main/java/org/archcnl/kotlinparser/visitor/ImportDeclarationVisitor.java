package org.archcnl.kotlinparser.visitor;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.kotlinparser.grammar.KotlinParser;
import org.archcnl.kotlinparser.grammar.KotlinParserBaseVisitor;
import org.archcnl.owlify.famix.codemodel.Type;

public class ImportDeclarationVisitor extends KotlinParserBaseVisitor<Void> {
    private final List<Type> imports;

    public ImportDeclarationVisitor() {
        this.imports = new ArrayList<>();
    }

    @Override
    public Void visitImportHeader(KotlinParser.ImportHeaderContext ctx) {
        // if there are no imports, do not try to read them
        if (ctx.children != null && ctx.children.size() >= 2) {
            var identifier = ctx.children.get(1);

            var importedType = identifier.getText();
            var simpleName = importedType.substring(importedType.lastIndexOf(".") + 1);
            imports.add(new Type(importedType, simpleName, false));
        }

        return super.visitImportHeader(ctx);
    }

    public List<Type> getImports() {
        return imports;
    }
}
