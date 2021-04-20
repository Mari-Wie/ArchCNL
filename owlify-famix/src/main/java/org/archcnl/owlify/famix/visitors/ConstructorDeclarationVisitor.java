package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.visitors.helper.MethodParser;

public class ConstructorDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;
    private List<Method> visitedConstructors;

    public ConstructorDeclarationVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
        this.visitedConstructors = new ArrayList<>();
    }

    public ConstructorDeclarationVisitor() {
        this.visitedConstructors = new ArrayList<>();
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {

        BlockStmt body = n.getBody();
        //        Individual method = ontology.addMethod(parent, n.getName().asString(),
        // n.getSignature().asString(),
        // n.getModifiers().stream().map(Modifier::toString).collect(Collectors.toList()),
        // n.getParameters(), n.getThrownExceptions(), body.getStatements(), true, null);
        //        AnnotationProcessor.visitAnnotations(ontology, n.getAnnotations(), method);

        visitedConstructors.add(new MethodParser(n).getMethod());
    }

    public List<Method> getConstructors() {
        return visitedConstructors;
    }
}
