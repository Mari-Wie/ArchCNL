package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.visitors.helper.VisitorHelpers;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.AnnotationAttribute;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Enumeration;
import org.archcnl.owlify.famix.codemodel.Field;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Type;

/** Visits all declarations of new types (class, interface, or annotation) in a unit. */
public class JavaTypeVisitor extends VoidVisitorAdapter<Void> {

    private List<DefinedType> definedTypes;
    private String path;

    public JavaTypeVisitor(String path) {
        definedTypes = new ArrayList<>();
        this.path = path;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        List<Type> supertypes = new ArrayList<>();
        setSubclassProperties(supertypes, n.getExtendedTypes());
        setSubclassProperties(supertypes, n.getImplementedTypes());

        definedTypes.add(
                new ClassOrInterface(
                		path,
                        n.resolve().getQualifiedName(),
                        n.getNameAsString(),
                        processNestedTypes(n.getMembers()),
                        processAllMethods(n.getMethods(), n.getConstructors()),
                        processFields(n.getFields()),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        VisitorHelpers.processAnnotations(n.getAnnotations(), path),
                        n.isInterface(),
                        supertypes));
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {

        //        famixTypeIndividual = ontology.createEnumWithName(famixTypeName);
        //        ontology.setHasNamePropertyForNamedEntity(famixTypeName, famixTypeIndividual);

        definedTypes.add(
                new Enumeration(
                		path,
                        n.resolve().getQualifiedName(),
                        n.getNameAsString(),
                        processNestedTypes(n.getMembers()),
                        processAllMethods(n.getMethods(), n.getConstructors()),
                        processFields(n.getFields()),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        VisitorHelpers.processAnnotations(n.getAnnotations(), path)));
    }

    @Override
    public void visit(AnnotationDeclaration n, Void arg) {
        definedTypes.add(
                new Annotation(
                		path,
                        n.resolve().getQualifiedName(),
                        n.getNameAsString(),
                        VisitorHelpers.processAnnotations(n.getAnnotations(), path),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        processAnnotationAttributes(n.getMembers())));
    }

    private void setSubclassProperties(
            List<Type> supertypes, NodeList<ClassOrInterfaceType> extendedTypes) {
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();

        for (ClassOrInterfaceType classOrInterfaceType : extendedTypes) {
            classOrInterfaceType.accept(visitor, null);
            supertypes.add(visitor.getType());
        }
    }

    private List<AnnotationAttribute> processAnnotationAttributes(
            NodeList<BodyDeclaration<?>> members) {
        return members.stream()
                .filter(bodyDeclaration -> bodyDeclaration instanceof AnnotationMemberDeclaration)
                .map(
                        declaration -> {
                            AnnotationMemberDeclaration annotationMember =
                                    (AnnotationMemberDeclaration) declaration;
                            DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
                            annotationMember.getType().accept(visitor, null);
                            return new AnnotationAttribute(
                                    annotationMember.getNameAsString(), visitor.getType());
                        })
                .collect(Collectors.toList());
    }

    private List<Method> processAllMethods(
            List<MethodDeclaration> methods, List<ConstructorDeclaration> constructors) {
        MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor(path);
        ConstructorDeclarationVisitor constructorVisitor = new ConstructorDeclarationVisitor(path);

        methods.forEach(declaration -> declaration.accept(methodVisitor, null));
        constructors.forEach(declaration -> declaration.accept(constructorVisitor, null));

        List<Method> allMethods = methodVisitor.getMethods();
        allMethods.addAll(constructorVisitor.getConstructors());

        return allMethods;
    }

    private List<Field> processFields(List<FieldDeclaration> fields) {
        JavaFieldVisitor fieldVisitor = new JavaFieldVisitor(path);
        fields.forEach(declaration -> declaration.accept(fieldVisitor, null));
        return fieldVisitor.getFields();
    }

    private List<DefinedType> processNestedTypes(NodeList<BodyDeclaration<?>> declarations) {
        JavaTypeVisitor typeVisitor = new JavaTypeVisitor(path);
        declarations.accept(typeVisitor, null);
        return typeVisitor.getDefinedTypes();
    }

    /**
     * @return all types which have been declared at the top-level; nested types are contained in
     *     the respective elements of the list
     */
    public List<DefinedType> getDefinedTypes() {
        return definedTypes;
    }
}
