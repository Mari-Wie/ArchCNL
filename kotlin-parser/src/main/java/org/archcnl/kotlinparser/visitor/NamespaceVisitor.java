package org.archcnl.kotlinparser.visitor;

import org.archcnl.kotlinparser.grammar.KotlinParser;
import org.archcnl.owlify.famix.codemodel.Namespace;

public class NamespaceVisitor extends NamedBaseVisitor {

    private Namespace namespace;

    public NamespaceVisitor(String[] rulesNames) {
        super(rulesNames);
        namespace = Namespace.TOP;
    }

    @Override
    public Void visitPackageHeader(KotlinParser.PackageHeaderContext ctx) {
        var identifier = ctx.identifier();
        // if there is no package, do not try to read it
        if (identifier != null) {
            createPackageBasedOnQualifiedName(identifier.getText());
        }
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
