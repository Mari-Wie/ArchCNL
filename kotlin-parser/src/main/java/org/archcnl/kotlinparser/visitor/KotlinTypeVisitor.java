package org.archcnl.kotlinparser.visitor;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.kotlinparser.grammar.KotlinParser;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;

public class KotlinTypeVisitor extends NamedBaseVisitor {
    private final List<DefinedType> definedTypes;

    public KotlinTypeVisitor(String[] rulesNames) {
        super(rulesNames);
        this.definedTypes = new ArrayList<>();
    }

    @Override
    public Void visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
        var identifier = ctx.simpleIdentifier();
        // if there is no class name, do not try to read it
        if (identifier != null) {
            var className = identifier.getText();
            var fullyQualifiedName = getFullyQualifiedPartBeforeName(ctx).concat(className);
            var isInterface = ctx.INTERFACE() != null;

            var definedType =
                    new ClassOrInterface(
                            fullyQualifiedName,
                            className,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            isInterface,
                            new ArrayList<>());
            definedTypes.add(definedType);
        }
        return super.visitClassDeclaration(ctx);
    }

    public List<DefinedType> getDefinedTypes() {
        return definedTypes;
    }

    private String getFullyQualifiedPartBeforeName(KotlinParser.ClassDeclarationContext ctx) {
        var parent = ctx.getParent();
        var namespaceOfFile = "";
        var outerClassesIdentifiers = new ArrayList<>();

        while (parent != null) {
            if (parent instanceof KotlinParser.KotlinFileContext) {
                var kotlinFileContext = (KotlinParser.KotlinFileContext) parent;
                namespaceOfFile = kotlinFileContext.packageHeader().identifier().getText();
                break;
            }
            if (parent instanceof KotlinParser.ClassDeclarationContext) {
                var classDeclarationContext = (KotlinParser.ClassDeclarationContext) parent;
                outerClassesIdentifiers.add(classDeclarationContext.simpleIdentifier().getText());
            }
            parent = parent.getParent();
        }

        var namespaceBuilder = new StringBuilder();
        if (!namespaceOfFile.isEmpty()) {
            namespaceBuilder.append(namespaceOfFile).append(".");
        }

        // the most inner parent class is the first in the list,
        // since it is discovered first by traversing the parents.
        // To append the outer class first, traverse the list backwards
        for (int i = outerClassesIdentifiers.size() - 1; i >= 0; i--) {
            var outerClassIdentifier = outerClassesIdentifiers.get(i);
            namespaceBuilder.append(outerClassIdentifier).append(".");
        }

        return namespaceBuilder.toString();
    }
}
