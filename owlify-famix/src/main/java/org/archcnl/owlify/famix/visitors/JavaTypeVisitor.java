package org.archcnl.owlify.famix.visitors;

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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.AnnotationAttribute;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Enumeration;
import org.archcnl.owlify.famix.codemodel.Field;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Type;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.visitors.helper.VisitorHelpers;

public class JavaTypeVisitor extends VoidVisitorAdapter<Void> {

    private Individual famixTypeIndividual;
    private String famixTypeName;
    private FamixOntology ontology;

    private List<DefinedType> definedTypes;

    public JavaTypeVisitor(InputStream famixOntologyInputStream) {
        ontology = new FamixOntology(famixOntologyInputStream);
    }

    public JavaTypeVisitor(FamixOntology ontology) {
        this.ontology = ontology;
    }

    public JavaTypeVisitor() {
        definedTypes = new ArrayList<>();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        List<Type> supertypes = new ArrayList<>();
        setSubclassProperties(supertypes, n.getExtendedTypes());
        setSubclassProperties(supertypes, n.getImplementedTypes());

        definedTypes.add(
                new ClassOrInterface(
                        n.resolve().getQualifiedName(),
                        processNestedTypes(n.getMembers()),
                        processAllMethods(n.getMethods(), n.getConstructors()),
                        processFields(n.getFields()),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        VisitorHelpers.processAnnotations(n.getAnnotations()),
                        n.isInterface(),
                        supertypes));

        //        super.visit(n, null);
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {

        //        famixTypeIndividual = ontology.createEnumWithName(famixTypeName);
        //        ontology.setHasNamePropertyForNamedEntity(famixTypeName, famixTypeIndividual);

        definedTypes.add(
                new Enumeration(
                        n.resolve().getQualifiedName(),
                        processNestedTypes(n.getMembers()),
                        processAllMethods(n.getMethods(), n.getConstructors()),
                        processFields(n.getFields()),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        VisitorHelpers.processAnnotations(n.getAnnotations())));

        //        super.visit(n, null);
    }

    @Override
    public void visit(AnnotationDeclaration n, Void arg) {
        //        famixTypeIndividual = ontology.getAnnotationTypeIndividualWithName(famixTypeName);

        //        n.getMembers().forEach(m -> processAnnotationMemberDeclaration(m));

        definedTypes.add(
                new Annotation(
                        n.resolve().getQualifiedName(),
                        VisitorHelpers.processAnnotations(n.getAnnotations()),
                        VisitorHelpers.processModifiers(n.getModifiers()),
                        processAnnotationAttributes(n.getMembers())));

        //        super.visit(n, null);
    }

    private void setSubclassProperties(
            List<Type> supertypes, NodeList<ClassOrInterfaceType> extendedTypes) {
        //      DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(ontology);
        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor();
        for (ClassOrInterfaceType classOrInterfaceType : extendedTypes) {
            classOrInterfaceType.accept(visitor, null);
            //
            // ontology.setInheritanceBetweenSubClassAndSuperClass(visitor.getDeclaredType(),
            // subClass);

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
        MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
        ConstructorDeclarationVisitor constructorVisitor = new ConstructorDeclarationVisitor();

        methods.forEach(declaration -> declaration.accept(methodVisitor, null));
        constructors.forEach(declaration -> declaration.accept(constructorVisitor, null));

        List<Method> allMethods = methodVisitor.getMethods();
        allMethods.addAll(constructorVisitor.getConstructors());

        return allMethods;
    }

    private List<Field> processFields(List<FieldDeclaration> fields) {
        JavaFieldVisitor fieldVisitor = new JavaFieldVisitor();
        fields.forEach(declaration -> declaration.accept(fieldVisitor, null));
        return fieldVisitor.getFields();
    }

    private List<DefinedType> processNestedTypes(NodeList<BodyDeclaration<?>> declarations) {
        JavaTypeVisitor typeVisitor = new JavaTypeVisitor();
        declarations.accept(typeVisitor, null);
        return typeVisitor.getDefinedTypes();
    }

    public Individual getFamixTypeIndividual() {
        return famixTypeIndividual;
    }

    public String getNameOfFamixType() {
        return famixTypeName;
    }

    public List<DefinedType> getDefinedTypes() {
        return definedTypes;
    }
}
