package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class LocalVariableVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;
    private List<LocalVariable> localVariables;

    public LocalVariableVisitor() {
        localVariables = new ArrayList<>();
    }

    public LocalVariableVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
        localVariables = new ArrayList<>();
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
            variableDeclarator.accept(visitor, null);

            //            ontology.addLocalVariable(variableDeclarator.getName().asString(),
            // visitor.getDeclaredType(), parent);
            localVariables.add(
                    new LocalVariable(visitor.getType(), variableDeclarator.getNameAsString()));
        }
    }

    public List<LocalVariable> getLocalVariables() {
        return localVariables;
    }
}
