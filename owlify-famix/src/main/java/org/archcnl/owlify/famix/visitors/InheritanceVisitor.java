package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;

// TODO: delete this class when refactoring is completed

public class InheritanceVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual subClass;
    private List<Type> supertypes;

    public InheritanceVisitor(FamixOntology ontology, Individual subClass) {
        this.ontology = ontology;
        this.subClass = subClass;
        this.supertypes = new ArrayList<>();
    }

    public InheritanceVisitor() {
        this.supertypes = new ArrayList<>();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        setSubclassProperties(n.getExtendedTypes());
        setSubclassProperties(n.getImplementedTypes());
    }

    private void setSubclassProperties(NodeList<ClassOrInterfaceType> extendedTypes) {
        //        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        for (ClassOrInterfaceType classOrInterfaceType : extendedTypes) {
            classOrInterfaceType.accept(visitor, null);
            //
            // ontology.setInheritanceBetweenSubClassAndSuperClass(visitor.getDeclaredType(),
            // subClass);

            supertypes.add(visitor.getType());
        }
    }

    public List<Type> getSupertypes() {
        return supertypes;
    }
}
