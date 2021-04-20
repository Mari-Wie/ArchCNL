package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class AccessVisitor extends VoidVisitorAdapter<Void> {
    // TODO: class is never used
    private FamixOntology ontology;
    private Individual accessingUnit;

    public AccessVisitor(FamixOntology ontology, Individual currentUnitIndividual) {
        this.ontology = ontology;
        this.accessingUnit = currentUnitIndividual;
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        if (n.getCondition().isMethodCallExpr()) {
            visit(n.getCondition().asMethodCallExpr(), null);
        }

        super.visit(n, arg);
    }

    //	@Override
    //	public void visit(FieldAccessExpr n, Void arg) {
    //		// FAMIX access
    ////		System.out.println("visit field access");
    ////		System.out.println("Name: " + n.getName());
    ////		System.out.println(n.getParentNode().get());
    ////		System.out.println(n.getScope());
    //		super.visit(n, arg);
    //	}

    @Override
    public void visit(AssignExpr n, Void arg) {
        //		System.out.println("AssignExpr");
        //		System.out.println(n.getValue());
        //		System.out.println(n.getTarget());
        if (n.getValue().isMethodCallExpr()) {
            visit(n.getValue().asMethodCallExpr(), arg);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        //		System.out.println("ObjectCreationExpr");
        //		System.out.println(n.getType());
        //		System.out.println(n.getParentNode());
        //		Individual type = ontology.getFamixClassWithName(n.getType().getName().toString());
        try {
            ResolvedReferenceType resolvedType = n.getType().resolve();
            //            Individual type =
            // ontology.getFamixClassWithName(resolvedType.getQualifiedName());
            Individual type = ontology.getReferenceTypeIndividual(resolvedType.getQualifiedName());

            ontology.setInvokes(accessingUnit, type);
        } catch (UnsolvedSymbolException e) {
            //			System.out.println("Unable to resolve type: " + e.getName());
        } catch (UnsupportedOperationException e) {
            //			System.out.println("UnsupportedOperatuibException for type: " + n.getType());
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        // Famix Invocation
        // scope: receiver

        //		Individual invocation = ontology.getInvocationIndividual();
        try {

            ResolvedMethodDeclaration resolvedMethodDeclaration = n.resolve();

            // Type where method is declared
            //            Individual declaringType =
            //                    ontology.getFamixClassWithName(
            //
            // resolvedMethodDeclaration.declaringType().getQualifiedName());

            Individual declaringType =
                    ontology.getReferenceTypeIndividual(
                            resolvedMethodDeclaration.declaringType().getQualifiedName());
            // TODO: changed to be consistent with the case above
            // TODO: does this break anything?
            // TODO: write an issue when checking in

            //            Individual receiver = ontology.getLocalVariableIndividual(declaringType);
            //            ontology.setInvokes(accessingUnit, receiver);
            ontology.setInvokes(accessingUnit, declaringType);

        } catch (Exception e) {
            // TODO: handle exception
        }

        super.visit(n, arg);
    }
}
