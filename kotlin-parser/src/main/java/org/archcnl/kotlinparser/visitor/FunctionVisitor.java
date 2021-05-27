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
