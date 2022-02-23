package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.MethodParser;
import org.archcnl.owlify.famix.codemodel.Method;

/** Visits all methods (excluding constructors) declared in the analyzed unit. */
public class MethodDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private List<Method> visitedMethods;
	private String path;

    public MethodDeclarationVisitor(String path) {
        visitedMethods = new ArrayList<>();
        this.path = path;
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        visitedMethods.add(new MethodParser(n, path).getMethod());
    }

    /** @return all encountered methods (excluding constructors) */
    public List<Method> getMethods() {
        return visitedMethods;
    }
}
