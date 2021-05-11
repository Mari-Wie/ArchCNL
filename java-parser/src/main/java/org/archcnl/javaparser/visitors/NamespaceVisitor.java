package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.archcnl.owlify.famix.codemodel.Namespace;

/** Parses the namespace of the analyzed unit. */
public class NamespaceVisitor extends VoidVisitorAdapter<Void> {

    private Namespace namespace;

    public NamespaceVisitor() {
        namespace = Namespace.TOP;
    }

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        createPackageBasedOnQualifiedName(n.getName().asString());
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

    /** @return the parsed namespace */
    public Namespace getNamespace() {
        return namespace;
    }
}
