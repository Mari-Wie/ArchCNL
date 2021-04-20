package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import org.apache.jena.ontology.Individual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class DeclaredJavaTypeVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = LogManager.getLogger(DeclaredJavaTypeVisitor.class);

    private Individual declaredType;
    private FamixOntology ontology;
    private Type type;

    public DeclaredJavaTypeVisitor(FamixOntology ontology) {
        this.ontology = ontology;
    }

    public DeclaredJavaTypeVisitor() {}

    @Override
    public void visit(ClassOrInterfaceType n, Void arg) {

        String name;
        try {
            name = n.resolve().asReferenceType().getQualifiedName();

        } catch (UnsolvedSymbolException | UnsupportedOperationException e) {
            name = n.getName().asString();
            LOG.warn("Unable to resolve type, using simple name instead: " + name);
        }
        type = new Type(name, false);
        //        declaredType = ontology.getReferenceTypeIndividual(name);
    }

    @Override
    public void visit(PrimitiveType n, Void arg) {
        //        declaredType = ontology.getPrimitiveTypeIndividual(n.asString());
        type = new Type(n.asString(), true);
    }

    @Override
    public void visit(VoidType n, Void arg) {
        //        declaredType = ontology.getPrimitiveTypeIndividual(n.asString());
        type = new Type(n.asString(), true);
    }

    public Individual getDeclaredType() {
        return declaredType;
    }

    public Type getType() {
        return type;
    }
}
