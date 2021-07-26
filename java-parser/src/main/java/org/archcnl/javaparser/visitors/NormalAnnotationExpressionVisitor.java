package org.archcnl.javaparser.visitors;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/** Visits a normal annotation expression. */
public class NormalAnnotationExpressionVisitor extends VoidVisitorAdapter<Void> {

  private AnnotationInstance annotationInstance;

  @Override
  public void visit(final NormalAnnotationExpr n, final Void arg) {
    final List<AnnotationMemberValuePair> values = n.getPairs().stream()
        .map(pair -> createAnnotationPairFromPair(pair, n)).collect(Collectors.toList());

    annotationInstance = new AnnotationInstance(n.getName().asString(), values);
  }

  /** @return the parsed annotation instance */
  public AnnotationInstance getAnnotationInstance() {
    return annotationInstance;
  }

  private AnnotationMemberValuePair createAnnotationPairFromPair(
      final MemberValuePair annotationMemberValuePair, final NormalAnnotationExpr n) {
    final var name = annotationMemberValuePair.getNameAsString();
    final var valueExpression = annotationMemberValuePair.getValue();
    var value = valueExpression.toString();

    if (!(valueExpression instanceof StringLiteralExpr)) {
      final var possibleValue = findFieldValueInParentClass(valueExpression, n);
      if (possibleValue != null) {
        value = possibleValue;
      }
    }

    return new AnnotationMemberValuePair(name, value);
  }

  private @Nullable String findFieldValueInParentClass(final Expression valueExpression,
      final Node currentNode) {
    final var parentClass = findClassOrInterfaceDeclaration(currentNode);

    if (parentClass != null) {
      final var field = parentClass.getFieldByName(valueExpression.toString());

      if (field.isPresent()) {
        final var fieldWithValue = field.get();
        final var fieldVariableInitializer = fieldWithValue.getVariable(0).getInitializer();
        if (fieldVariableInitializer.isPresent()) {
          return fieldVariableInitializer.get().toString();
        }
      }
    }

    return null;
  }

  private @Nullable ClassOrInterfaceDeclaration findClassOrInterfaceDeclaration(
      final Node startingPoint) {
    var parent = startingPoint;
    var parentIsClass = parent instanceof ClassOrInterfaceDeclaration;

    while (!parentIsClass) {
      final var parentOfTheParent = parent.getParentNode();
      if (parentOfTheParent.isPresent()) {
        parent = parentOfTheParent.get();
        parentIsClass = parent instanceof ClassOrInterfaceDeclaration;
      } else {
        break;
      }
    }

    if (parentIsClass) {
      return (ClassOrInterfaceDeclaration) parent;
    }
    return null;
  }
}
