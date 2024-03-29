package org.archcnl.javaparser.visitors.helper;

import com.github.javaparser.Position;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.javaparser.visitors.MarkerAnnotationExpressionVisitor;
import org.archcnl.javaparser.visitors.NormalAnnotationExpressionVisitor;
import org.archcnl.javaparser.visitors.SingleMemberAnnotationExpressionVisitor;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;

/** Defines some static helper methods used by multiple visitors. */
public class VisitorHelpers {

    private VisitorHelpers() {}

    /**
     * Parses the given list of annotation expressions and creates a list of the corresponding
     * AnnotationInstances.
     */
    public static List<AnnotationInstance> processAnnotations(
            NodeList<AnnotationExpr> annotationList, Path path) {
        List<AnnotationInstance> annotations = new ArrayList<>();

        for (AnnotationExpr annotationExpr : annotationList) {
            MarkerAnnotationExpressionVisitor v1 = new MarkerAnnotationExpressionVisitor(path);
            SingleMemberAnnotationExpressionVisitor v2 =
                    new SingleMemberAnnotationExpressionVisitor(path);
            NormalAnnotationExpressionVisitor v3 = new NormalAnnotationExpressionVisitor(path);

            annotationExpr.accept(v1, null);
            annotationExpr.accept(v2, null);
            annotationExpr.accept(v3, null);

            if (v1.getAnnotationInstance() != null) {
                annotations.add(v1.getAnnotationInstance());
            }
            if (v2.getAnnotationInstance() != null) {
                annotations.add(v2.getAnnotationInstance());
            }
            if (v3.getAnnotationInstance() != null) {
                annotations.add(v3.getAnnotationInstance());
            }
        }
        return annotations;
    }

    /** Parses the given list of modifiers and returns the corresponding list of Modifiers. */
    public static List<org.archcnl.owlify.famix.codemodel.Modifier> processModifiers(
            NodeList<Modifier> modifiers) {
        return modifiers.stream()
                .map(Modifier::toString)
                .map(
                        mod ->
                                new org.archcnl.owlify.famix.codemodel.Modifier(
                                        mod.replace(" ", ""))) // remove trailing whitespace
                .collect(Collectors.toList());
    }

    public static Optional<Integer> convertOptionalFromPositionToInteger(
            Optional<Position> positionOptional) {
        if (positionOptional.isPresent()) {
            return Optional.of(positionOptional.get().line);
        } else {
            return Optional.empty();
        }
    }
}
