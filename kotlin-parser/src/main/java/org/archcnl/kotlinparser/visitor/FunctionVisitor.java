package org.archcnl.kotlinparser.visitor;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.kotlinparser.grammar.KotlinParser;
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
        var annotationVisitor = new AnnotationVisitor(getRulesNames());
        annotationVisitor.visit(ctx);
        var annotations = annotationVisitor.getAnnotationInstances();

        var functionName = ctx.simpleIdentifier().getText();
        var functionSignature = createMethodSignature(functionName, ctx.functionValueParameters());

        var function =
                new Method(
                        functionName,
                        functionSignature,
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

    // "calculateArea(String, String, String)"
    private String createMethodSignature(
            String functionName,
            KotlinParser.FunctionValueParametersContext functionValueParameters) {
        var parameterTypes = new ArrayList<String>();
        for (var functionValueParameter : functionValueParameters.functionValueParameter()) {
            parameterTypes.add(functionValueParameter.parameter().type_().getText());
        }

        var parameterTypesString = "";
        if (!parameterTypes.isEmpty()) {
            var parameterTypesStringBuilder = new StringBuilder();

            for (String parameterType : parameterTypes) {
                parameterTypesStringBuilder.append(parameterType);
                parameterTypesStringBuilder.append(", ");
            }

            var length = parameterTypesStringBuilder.length();
            parameterTypesStringBuilder.delete(length - 2, length);

            parameterTypesString = parameterTypesStringBuilder.toString();
        }

        return functionName + "(" + parameterTypesString + ")";
    }
}
