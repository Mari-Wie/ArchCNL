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
    private String path;

    public JavaFieldVisitor(String path) {
        this.fields = new ArrayList<>();
        this.path = path;
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();

        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            variableDeclarator.accept(visitor, null);
            String location = path;
            if (variableDeclarator.getBegin().isPresent()) {
                location += ", Line: " + String.valueOf(variableDeclarator.getBegin().get().line);
            }
            fields.add(
                    new Field(
                            location,
                            variableDeclarator.getNameAsString(),
                            visitor.getType(),
                            VisitorHelpers.processAnnotations(n.getAnnotations(), path),
                            VisitorHelpers.processModifiers(n.getModifiers())));
        }
    }

    /** @return all declared fields */
    public List<Field> getFields() {
        return fields;
    }
}
