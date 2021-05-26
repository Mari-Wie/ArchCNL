package org.archcnl.kotlinparser.visitor;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.kotlinparser.grammar.KotlinParser;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.AnnotationMemberValuePair;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Type;

public class FunctionVisitor extends NamedBaseVisitor {
    private final List<Method> functions;

    public FunctionVisitor(String[] rulesNames) {
        super(rulesNames);
        functions = new ArrayList<>();
    }

    @Override
    public Void visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx) {
        var annotations = new ArrayList<AnnotationInstance>();
        var annotationContexts = ctx.modifiers().annotation();
        annotationContexts.forEach(
                annotation -> {
                    var singleAnnotation = annotation.singleAnnotation();
                    if (singleAnnotation != null) {
                        var constructorInvocation =
                                singleAnnotation.unescapedAnnotation().constructorInvocation();
                        var userType = constructorInvocation.userType().getText();

                        var annotationValues = new ArrayList<AnnotationMemberValuePair>();
                        var valueArguments = constructorInvocation.valueArguments().valueArgument();
                        valueArguments.forEach(
                                arg -> {
                                    var name = arg.simpleIdentifier().getText();
                                    var value = arg.expression().getText();

                                    var annotationValue =
                                            new AnnotationMemberValuePair(name, value);
                                    annotationValues.add(annotationValue);
                                });

                        var annotationInstance = new AnnotationInstance(userType, annotationValues);
                        annotations.add(annotationInstance);
                    }
                });

        var functionName = ctx.simpleIdentifier().getText();

        var function =
                new Method(
                        functionName,
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        annotations,
                        Type.UNUSED_VALUE,
                        false,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        functions.add(function);

        return super.visitFunctionDeclaration(ctx);
    }

    public List<Method> getFunctions() {
        return functions;
    }
}
