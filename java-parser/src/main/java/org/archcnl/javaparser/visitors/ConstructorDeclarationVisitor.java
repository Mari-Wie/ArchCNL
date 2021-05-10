package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.MethodParser;
import org.archcnl.owlify.famix.codemodel.Method;

/** Parses all constructor declarations in the analyzed unit.. */
public class ConstructorDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private List<Method> visitedConstructors;

    public ConstructorDeclarationVisitor() {
        this.visitedConstructors = new ArrayList<>();
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        visitedConstructors.add(new MethodParser(n).getMethod());
    }

    /** @return all identified constructors modeled as Methods */
    public List<Method> getConstructors() {
        return visitedConstructors;
    }
}
