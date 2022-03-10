package org.archcnl.kotlinparser.visitor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.archcnl.kotlinparser.grammar.KotlinParser;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;

public class AnnotationVisitor extends NamedBaseVisitor {
    private final List<AnnotationInstance> annotationInstances;

    public AnnotationVisitor(String[] rulesNames) {
        super(rulesNames);
        annotationInstances = new ArrayList<>();
    }

    @Override
    public Void visitSingleAnnotation(KotlinParser.SingleAnnotationContext ctx) {
        var constructorInvocation = ctx.unescapedAnnotation().constructorInvocation();
        if (constructorInvocation != null) {
            var userType = constructorInvocation.userType().getText();

            var annotationValues = new ArrayList<AnnotationMemberValuePair>();
            var valueArguments = constructorInvocation.valueArguments().valueArgument();
            valueArguments.forEach(
                    arg -> {
                        String name;
                        if (arg.simpleIdentifier() != null) {
                            name = arg.simpleIdentifier().getText();
                        } else {
                            name = "";
                        }
                        var value = arg.expression().getText();

                        var annotationValue = new AnnotationMemberValuePair(name, value);
                        annotationValues.add(annotationValue);
                    });

            var annotationInstance =
                    new AnnotationInstance(
                            userType,
                            annotationValues,
                            Path.of("noPathInformationAvailableForKotlinFiles"), // TODO implement
                            // path information
                            // for Kotlin
                            Optional.empty()); // TODO implement line information for Kotlin
            annotationInstances.add(annotationInstance);
        }

        return super.visitSingleAnnotation(ctx);
    }

    public List<AnnotationInstance> getAnnotationInstances() {
        return annotationInstances;
    }
}
