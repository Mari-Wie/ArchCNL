package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.javaparser.visitors.helper.VisitorHelpers;
import org.archcnl.owlify.famix.codemodel.Field;

/** Visits all field declarations in a unit. */
public class JavaFieldVisitor extends VoidVisitorAdapter<Void> {

    private List<Field> fields;

    public JavaFieldVisitor() {
        this.fields = new ArrayList<>();
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            variableDeclarator.accept(visitor, null);
            fields.add(
                    new Field(
                            variableDeclarator.getNameAsString(),
                            visitor.getType(),
                            VisitorHelpers.processAnnotations(n.getAnnotations()),
                            VisitorHelpers.processModifiers(n.getModifiers())));
        }
    }

    /** @return all declared fields */
    public List<Field> getFields() {
        return fields;
    }
}
