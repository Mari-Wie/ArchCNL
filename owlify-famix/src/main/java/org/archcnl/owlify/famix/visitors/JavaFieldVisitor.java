package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Field;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.visitors.helper.VisitorHelpers;

public class JavaFieldVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual parent;
    private List<Field> fields;

    public JavaFieldVisitor(FamixOntology ontology, Individual parent) {
        this.ontology = ontology;
        this.parent = parent;
        this.fields = new ArrayList<>();
    }

    public JavaFieldVisitor() {
        this.fields = new ArrayList<>();
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        //        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            variableDeclarator.accept(visitor, null);
            //            Individual field =
            // ontology.createAttributeIndividual(variableDeclarator.getName().asString(),
            // visitor.getDeclaredType(), parent,
            // n.getModifiers().stream().map(Modifier::toString).collect(Collectors.toList()));
            //            AnnotationProcessor.visitAnnotations(ontology, n.getAnnotations(), field);

            fields.add(
                    new Field(
                            variableDeclarator.getNameAsString(),
                            visitor.getType(),
                            VisitorHelpers.processAnnotations(n.getAnnotations()),
                            VisitorHelpers.processModifiers(n.getModifiers())));
        }
    }

    public List<Field> getFields() {
        return fields;
    }
}
