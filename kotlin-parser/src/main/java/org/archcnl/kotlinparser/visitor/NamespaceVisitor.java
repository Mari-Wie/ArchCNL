package org.archcnl.kotlinparser.visitor;

import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinParser.PackageHeaderContext;
import org.archcnl.owlify.famix.kotlin.grammar.KotlinParserBaseVisitor;

public class NamespaceVisitor extends KotlinParserBaseVisitor<Void> {

    private Namespace namespace;

    public NamespaceVisitor() {
        namespace = Namespace.TOP;
    }

    @Override
    public Void visitPackageHeader(PackageHeaderContext ctx) {
        var identifier = ctx.children.get(1);

        var test = identifier.getText();
        createPackageBasedOnQualifiedName(test);
        return super.visitPackageHeader(ctx);
    }

    private void createPackageBasedOnQualifiedName(String packageName) {
        String[] split = packageName.split("\\.");
        String name = "";

        namespace = new Namespace(split[0], namespace);

        String parent = split[0];

        for (int n = 1; n < split.length; n++) {
            name = parent + "." + split[n];
            namespace = new Namespace(name, namespace);
            parent = name;
        }
    }

    public Namespace getNamespace() {
        return namespace;
    }
}
