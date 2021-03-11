package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class LocalVariableVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;

    public LocalVariableVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            Individual localVariableIndividual = ontology.getLocalVariableIndividual();
            ontology.setHasNamePropertyForNamedEntity(
                    variableDeclarator.getName().asString(), localVariableIndividual);

            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
            variableDeclarator.accept(visitor, null);
            Individual declaredTypeOfVariable = visitor.getDeclaredType();
            ontology.setDeclaredTypeForBehavioralOrStructuralEntity(
                    localVariableIndividual, declaredTypeOfVariable);

            // parent of local variable
            ontology.setDefinesLocalVariableProperty(parent, localVariableIndividual);
        }
    }
}
