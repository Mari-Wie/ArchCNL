package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;

/** Visits a normal annotation expression. */
public class NormalAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

    private AnnotationInstance annotationInstance;

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        List<AnnotationMemberValuePair> values =
                n.getPairs().stream()
                        .map(pair -> createAnnotationPairFromPair(pair, n))
                        .collect(Collectors.toList());

        annotationInstance = new AnnotationInstance(n.getName().asString(), values);
    }

    /** @return the parsed annotation instance */
    public AnnotationInstance getAnnotationInstance() {
        return annotationInstance;
    }

    private AnnotationMemberValuePair createAnnotationPairFromPair(
            MemberValuePair annotationMemberValuePair, NormalAnnotationExpr n) {
        var name = annotationMemberValuePair.getNameAsString();
        var valueExpression = annotationMemberValuePair.getValue();
        var value = valueExpression.toString();

        if (!(valueExpression instanceof StringLiteralExpr)) {
            var possibleValue = findFieldValueInParentClass(valueExpression, n);
            if (possibleValue != null) {
                value = possibleValue;
            }
        }

        return new AnnotationMemberValuePair(name, value);
    }

    private @Nullable String findFieldValueInParentClass(
            Expression valueExpression, Node currentNode) {
        var parentClass = findClassOrInterfaceDeclaration(currentNode);

        if (parentClass != null) {
            var field = parentClass.getFieldByName(valueExpression.toString());

            if (field.isPresent()) {
                var fieldWithValue = field.get();
                if (fieldWithValue.getVariables().size() > 0) {
                    var fieldVariableInitializer = fieldWithValue.getVariable(0).getInitializer();
                    if (fieldVariableInitializer.isPresent()) {
                        return fieldVariableInitializer.get().toString();
                    }
                }
            }
        }

        return null;
    }

    private @Nullable ClassOrInterfaceDeclaration findClassOrInterfaceDeclaration(
            Node startingPoint) {
        var parent = startingPoint;
        var parentIsClass = parent instanceof ClassOrInterfaceDeclaration;

        while (!parentIsClass) {
            var parentOfTheParent = parent.getParentNode();
            if (parentOfTheParent.isPresent()) {
                parent = parentOfTheParent.get();
                parentIsClass = parent instanceof ClassOrInterfaceDeclaration;
            } else {
                break;
            }
        }

        if (parentIsClass) {
            return (ClassOrInterfaceDeclaration) parent;
        } else {
            return null;
        }
    }
}
