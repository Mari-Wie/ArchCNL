package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.codemodel.Type;

/**
 * Parses a type declaration (e.g. the "double" in "double x = 5.0;", not type declarations as
 * "public class MyClass {...}").
 */
public class DeclaredJavaTypeVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(DeclaredJavaTypeVisitor.class);

    private Type type;

    public DeclaredJavaTypeVisitor() {}

    @Override
    public void visit(ClassOrInterfaceType n, Void arg) {

        String name;
        String simpleName = n.getName().asString();
        try {
            name = n.resolve().asReferenceType().getQualifiedName();

        } catch (UnsolvedSymbolException | UnsupportedOperationException e) {
            name = simpleName;
            LOG.warn("Unable to resolve type, using simple name instead: " + name);
            LOG.debug(e);
        }
        type = new Type(name, simpleName, false);
    }

    @Override
    public void visit(PrimitiveType n, Void arg) {
        type = new Type(n.asString(), n.asString(), true);
    }

    @Override
    public void visit(VoidType n, Void arg) {
        type = new Type(n.asString(), n.asString(), true);
    }

    /** @return the parsed type */
    public Type getType() {
        return type;
    }
}
