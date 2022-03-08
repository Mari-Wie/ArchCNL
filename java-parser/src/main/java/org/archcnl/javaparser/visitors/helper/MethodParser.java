package org.archcnl.javaparser.visitors.helper;

import com.github.javaparser.Position;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.javaparser.visitors.DeclaredJavaTypeVisitor;
import org.archcnl.javaparser.visitors.LocalVariableVisitor;
import org.archcnl.javaparser.visitors.ParameterVisitor;
import org.archcnl.javaparser.visitors.ThrowStatementVisitor;
import org.archcnl.javaparser.visitors.TryCatchVisitor;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Type;

/**
 * This class parses method and constructor declarations. The parsing is highly similar for both of
 * them. Thus, the code is provided in this class, which is used by the corresponding visitors.
 */
public class MethodParser {
    private Method method;

    private String name;
    private String signature;
    private String path;
    private Optional<Position> beginning;
    private List<Type> caughtExceptions;
    private List<Type> thrownExceptions;
    private List<LocalVariable> localVariables;
    private List<AnnotationInstance> annotations;
    private List<org.archcnl.owlify.famix.codemodel.Parameter> parameters;
    private List<Type> declaredExceptions;
    private Type returnType;
    private List<org.archcnl.owlify.famix.codemodel.Modifier> modifiers;


    /** Parses the given method declaration. */
    public MethodParser(MethodDeclaration n, String path) {
        this.path = path;
        beginning = n.getBegin();
        name = n.getName().asString();
        signature = n.getSignature().asString();
        returnType = processReturnType(n);
        annotations = VisitorHelpers.processAnnotations(n.getAnnotations(), path);
        parameters = processParameters(n.getParameters());
        declaredExceptions = processDeclaredExceptions(n.getThrownExceptions());
        modifiers = VisitorHelpers.processModifiers(n.getModifiers());
        caughtExceptions = new ArrayList<>();
        thrownExceptions = new ArrayList<>();
        localVariables = new ArrayList<>();

        Optional<BlockStmt> body = n.getBody();

        if (body.isPresent()) {
            processBody(body.get().getStatements(), path);
        }

        method = createMethodModel(false);
    }

    /** Parses the given constructor declaration. */
    public MethodParser(ConstructorDeclaration n, String path) {
        this.path = path;
        beginning = n.getBegin();
        name = n.getName().asString();
        signature = n.getSignature().asString();
        returnType = Type.UNUSED_VALUE;
        annotations = VisitorHelpers.processAnnotations(n.getAnnotations(), path);
        parameters = processParameters(n.getParameters());
        declaredExceptions = processDeclaredExceptions(n.getThrownExceptions());
        modifiers = VisitorHelpers.processModifiers(n.getModifiers());
        caughtExceptions = new ArrayList<>();
        thrownExceptions = new ArrayList<>();
        localVariables = new ArrayList<>();

        // constructors body is mandatory in Java
        processBody(n.getBody().getStatements(), path);

        method = createMethodModel(true);
    }

    /** Returns a Method object which models the given constructor/method declaration. */
    public Method getMethod() {
        return method;
    }

    private Method createMethodModel(boolean isConstructor) {
        return new Method(
                name,
                signature,
                modifiers,
                parameters,
                declaredExceptions,
                annotations,
                returnType,
                isConstructor,
                caughtExceptions,
                thrownExceptions,
                localVariables,
                path,
                beginning);
    }

    private Type processReturnType(MethodDeclaration n) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        n.getType().accept(visitor, null);
        return visitor.getType();
    }

    private void processBody(List<Statement> bodyStatements, String path) {
        for (Statement statement : bodyStatements) {
            TryCatchVisitor v1 = new TryCatchVisitor();
            ThrowStatementVisitor v2 = new ThrowStatementVisitor();
            LocalVariableVisitor v3 = new LocalVariableVisitor(path);

            statement.accept(v1, null);
            statement.accept(v2, null);
            statement.accept(v3, null);

            caughtExceptions.addAll(v1.getCaughtExceptions());
            thrownExceptions.addAll(v2.getThrownExceptionType());
            localVariables.addAll(v3.getLocalVariables());
        }
    }

    private List<org.archcnl.owlify.famix.codemodel.Parameter> processParameters(
            List<Parameter> p) {
        List<org.archcnl.owlify.famix.codemodel.Parameter> parameters = new ArrayList<>();

        for (Parameter parameter : p) {
            ParameterVisitor v = new ParameterVisitor(path);
            parameter.accept(v, null);
            parameters.add(v.getParameter());
        }
        return parameters;
    }

    private List<Type> processDeclaredExceptions(NodeList<ReferenceType> thrownExceptions) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();

        return thrownExceptions.stream()
                .map(
                        referenceType -> {
                            referenceType.accept(visitor, null);
                            return visitor.getType();
                        })
                .collect(Collectors.toList());
    }
}
