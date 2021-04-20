package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.visitors.helper.MethodParser;

public class MethodDeclarationVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;

    private List<Method> visitedMethods;

    public MethodDeclarationVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
        visitedMethods = new ArrayList<>();
    }

    public MethodDeclarationVisitor() {
        visitedMethods = new ArrayList<>();
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {

        Optional<BlockStmt> body = n.getBody();

        //        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        n.getType().accept(visitor, null);

        //        Individual m = ontology.addMethod(parent, n.getName().asString(),
        // n.getSignature().asString(),
        // n.getModifiers().stream().map(Modifier::toString).collect(Collectors.toList()),
        // n.getParameters(), n.getThrownExceptions(), body.isPresent() ? body.get().getStatements()
        // : new NodeList<Statement>(), false, visitor.getDeclaredType());
        //        AnnotationProcessor.visitAnnotations(ontology, n.getAnnotations(), m);

        // TODO
        // n.getTypeParameters();

        visitedMethods.add(new MethodParser(n).getMethod());
    }

    public List<Method> getMethods() {
        return visitedMethods;
    }
}
