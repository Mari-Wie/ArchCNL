package org.archcnl.owlify.famix.ontology;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.famix.visitors.DeclaredJavaTypeVisitor;
import org.archcnl.owlify.famix.visitors.LocalVariableVisitor;
import org.archcnl.owlify.famix.visitors.ParameterVisitor;
import org.archcnl.owlify.famix.visitors.ThrowStatementVisitor;
import org.archcnl.owlify.famix.visitors.TryCatchVisitor;

public class FamixOntology {

    private FamixOntClassesAndProperties classesAndProperties;
    private OntModel model;

    /*
     * A map between the fully qualified name of a class and the corresponding
     * individual of the ontology.
     */
    private Map<String, Individual> famixTypeIndividualCache;
    private Map<String, Individual> namespaceIndividualCache;
    private Map<Individual, Map<String, Individual>> annotationTypeToAttribute;

    private long typeID;
    private long inheritanceID;
    private long annotationInstanceID;
    private long attributeID;
    private long methodID;
    private long parameterID;
    private long exceptionId;
    private long localVariableId;
    private long namespaceId;
    private long annotationTypeAttributeId;
    private long annotationInstanceAttributeId;

    public FamixOntology(InputStream famixOntologyInputStream) {
        famixTypeIndividualCache = new HashMap<>();
        namespaceIndividualCache = new HashMap<>();
        classesAndProperties = new FamixOntClassesAndProperties();
        annotationTypeToAttribute = new HashMap<>();
        loadFamixModel(famixOntologyInputStream);
    }

    public String getOntologyNamespace() {
        return classesAndProperties.getOntologyNamespace();
    }

    private void loadFamixModel(InputStream famixOntologyInputStream) {
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        model.read(famixOntologyInputStream, null);
    }

    public Individual createNamespaceIndividual(String namespaceName) {

        if (!namespaceIndividualCache.containsKey(namespaceName)) {
            Individual individual =
                    classesAndProperties.createNamespaceIndividual(model, namespaceId++);

            setHasNamePropertyForNamedEntity(namespaceName, individual);
            namespaceIndividualCache.put(namespaceName, individual);
        }
        return getNamespaceIndividual(namespaceName);
    }

    public Individual getNamespaceIndividual(String namespaceName) {
        return namespaceIndividualCache.get(namespaceName);
    }

    public Individual createFamixClassWithName(String fullyQualifiedName) {
        return createTypeWithName(fullyQualifiedName, false);
    }

    public Individual createEnumWithName(String fullyQualifiedName) {
        return createTypeWithName(fullyQualifiedName, true);
    }

    private Individual createTypeWithName(String fullyQualifiedName, boolean isEnum) {
        if (!famixTypeIndividualCache.containsKey(fullyQualifiedName)) {
            Individual individual = null;

            if (isEnum) {
                individual =
                        classesAndProperties.getEnumTypeIndividual(
                                model, fullyQualifiedName, typeID);
            } else {
                individual =
                        classesAndProperties.getFamixClassIndividual(
                                model, fullyQualifiedName, typeID);
            }
            setHasFullQualifiedNameForType(individual, fullyQualifiedName);
            famixTypeIndividualCache.put(fullyQualifiedName, individual);
            typeID++;
        }
        return famixTypeIndividualCache.get(fullyQualifiedName);
    }

    public Individual getAnnotationTypeIndividualWithName(String annotationTypeName) {
        if (!famixTypeIndividualCache.containsKey(annotationTypeName)) {
            Individual individual =
                    classesAndProperties.getAnnotationTypeIndividual(
                            model, annotationTypeName, typeID);
            setHasNamePropertyForNamedEntity(annotationTypeName, individual);
            famixTypeIndividualCache.put(annotationTypeName, individual);
            typeID++;
        }

        return famixTypeIndividualCache.get(annotationTypeName);
    }

    public void addAnnotationTypeAttribute(
            String name, Individual type, Individual annotationType) {
        Individual annotationTypeAttr = createExternalAnnotationTypeAttribute(name);
        setDeclaredTypeForBehavioralOrStructuralEntity(annotationTypeAttr, type);
        setHasAnnotationTypeAttributeForAnnotationType(annotationType, name, annotationTypeAttr);
    }

    private Individual createExternalAnnotationTypeAttribute(String name) {
        Individual annotationTypeAttr =
                classesAndProperties.getAnnotationTypeAttributeIndividual(
                        model, annotationTypeAttributeId++);
        setHasNamePropertyForNamedEntity(name, annotationTypeAttr);
        return annotationTypeAttr;
    }

    public void setHasNamePropertyForNamedEntity(String name, Individual namedEntity) {
        DatatypeProperty property = classesAndProperties.getHasNameProperty(model);
        namedEntity.addLiteral(property, name);
    }

    public void setIsInterfaceForFamixClass(boolean isInterface, Individual famixClass) {
        DatatypeProperty property = classesAndProperties.getIsInterfaceProperty(model);
        famixClass.addLiteral(property, isInterface);
    }

    public void setHasModifierForNamedEntity(String modifier, Individual namedEntity) {
        DatatypeProperty property = classesAndProperties.getHasModifierProperty(model);
        namedEntity.addLiteral(property, modifier);
    }

    public void setHasAnnotationInstanceForEntity(
            Individual annotationIndividual, Individual famixTypeIndividual) {
        ObjectProperty property = classesAndProperties.getHasAnnotationInstanceProperty(model);
        famixTypeIndividual.addProperty(property, annotationIndividual);
    }

    public void setInheritanceBetweenSubClassAndSuperClass(
            Individual superClassIndividual, Individual subClassIndividual) {
        Individual inheritance =
                classesAndProperties.getInheritanceIndividual(model, inheritanceID);
        inheritanceID++;
        ObjectProperty subclassProperty = classesAndProperties.getSubClassProperty(model);
        ObjectProperty superclassProperty = classesAndProperties.getSuperClassProperty(model);
        inheritance.addProperty(subclassProperty, subClassIndividual);
        inheritance.addProperty(superclassProperty, superClassIndividual);
    }

    public Individual getAnnotationInstanceIndividual(
            String name, Individual annotatedEntity, NodeList<MemberValuePair> memberValuePairs) {
        Individual annotation =
                classesAndProperties.getAnnotationInstanceIndividual(model, annotationInstanceID++);

        setHasAnnotationInstanceForEntity(annotation, annotatedEntity);

        Individual annotationType = getAnnotationTypeIndividualWithName(name);
        setHasAnnotationTypeProperty(annotation, annotationType);

        for (MemberValuePair memberValuePair : memberValuePairs) {
            Expression value = memberValuePair.getValue();
            Individual annotationTypeAttribute =
                    getAnnotationTypeAttributeOfAnnotationTypeByName(
                            memberValuePair.getName().asString(), annotationType);

            if (annotationTypeAttribute == null) {
                annotationTypeAttribute =
                        createExternalAnnotationTypeAttribute(memberValuePair.getName().asString());
            }

            Individual annotationInstanceAttribute = getAnnotationInstanceAttributeIndividual();
            setHasAnnotationTypeAttributeForAnnotationInstanceAttribute(
                    annotationTypeAttribute, annotationInstanceAttribute);
            setHasAnnotationInstanceAttributeForAnnotationInstance(
                    annotationInstanceAttribute, annotation);
            setHasValueForAnnotationInstanceAttribute(
                    annotationInstanceAttribute, value.toString());
        }

        return annotation;
    }

    private void setHasAnnotationTypeProperty(
            Individual annotationInstanceIndividual, Individual annotationType) {
        ObjectProperty hasAnnotationTypeProperty =
                classesAndProperties.getHasAnnotationTypeProperty(model);
        annotationInstanceIndividual.addProperty(hasAnnotationTypeProperty, annotationType);
    }

    public OntModel toJenaModel() {
        OntModel copyOfModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        copyOfModel.add(model);
        return copyOfModel;
    }

    public void save(String resultPath) {
        try {
            model.write(new FileOutputStream(new File(resultPath)));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Individual createAttributeIndividual(
            String name, Individual typeIndividual, Individual parent, List<String> modifiers) {

        Individual attribute = classesAndProperties.getAttributeIndividual(model, attributeID++);

        setDefinesAttributePropertyForType(parent, attribute);
        setDeclaredTypeForBehavioralOrStructuralEntity(attribute, typeIndividual);

        setHasNamePropertyForNamedEntity(name, attribute);
        processModifiers(modifiers, attribute);

        return attribute;
    }

    private void setDefinesAttributePropertyForType(Individual parent, Individual fieldIndividual) {
        ObjectProperty definesAttribute =
                classesAndProperties.getDefinesAttributeObjectProperty(model);
        parent.addProperty(definesAttribute, fieldIndividual);
    }

    public void setDeclaredTypeForBehavioralOrStructuralEntity(
            Individual entity, Individual declaredType) {
        ObjectProperty hasDeclaredTypeProperty =
                classesAndProperties.getHasDeclaredTypeProperty(model);
        entity.addProperty(hasDeclaredTypeProperty, declaredType);
    }

    public Individual getPrimitiveTypeIndividual(String primitiveTypeName) {
        return classesAndProperties.getPrimitiveTypeIndividual(model, primitiveTypeName);
    }

    public Individual addMethod(
            Individual parent,
            String name,
            String signature,
            List<String> modifiers,
            NodeList<Parameter> parameters,
            NodeList<ReferenceType> thrownExceptions,
            NodeList<Statement> bodyStatements,
            boolean isConstructor,
            Individual returnType) {

        // TODO: lots of circular dependencies

        Individual method = classesAndProperties.getMethodIndividual(model, methodID++);

        setSignatureOfBehavioralEntity(signature, method);
        setDefinesMethodPropertyForFamixType(parent, method);
        setIsConstructorProperty(isConstructor, method);

        if (returnType != null) {
            setDeclaredTypeForBehavioralOrStructuralEntity(method, returnType);
        }

        setHasNamePropertyForNamedEntity(name, method);
        processModifiers(modifiers, method);

        DeclaredJavaTypeVisitor visitor = new DeclaredJavaTypeVisitor(this);

        for (ReferenceType referenceType : thrownExceptions) {
            referenceType.accept(visitor, null);
            Individual thrownExceptionClass = visitor.getDeclaredType();

            Individual declaredExceptionIndividual = getDeclaredExceptionIndividual();
            setExceptionHasDefiningClass(declaredExceptionIndividual, thrownExceptionClass);
            setHasDeclaredExceptionProperty(declaredExceptionIndividual, method);
        }

        for (Parameter parameter : parameters) {
            parameter.accept(new ParameterVisitor(this, method), null);
        }

        for (Statement statement : bodyStatements) {
            statement.accept(new TryCatchVisitor(this, method), null);
            statement.accept(new ThrowStatementVisitor(this, method), null);
            statement.accept(new LocalVariableVisitor(this, method), null);
        }

        return method;
    }

    private void setSignatureOfBehavioralEntity(String signature, Individual methodIndividual) {
        DatatypeProperty hasSignatureProperty = classesAndProperties.getHasSignatureProperty(model);
        methodIndividual.addLiteral(hasSignatureProperty, signature);
    }

    private void setDefinesMethodPropertyForFamixType(
            Individual parent, Individual methodIndividual) {
        ObjectProperty definesMethodProperty = classesAndProperties.getDefinesMethodProperty(model);
        parent.addProperty(definesMethodProperty, methodIndividual);
    }

    public Individual getParameterIndividual(
            String name, Individual type, Individual parent, List<String> modifiers) {
        Individual parameter = classesAndProperties.getParameterIndividual(model, parameterID++);

        setDeclaredTypeForBehavioralOrStructuralEntity(parameter, type);

        setHasNamePropertyForNamedEntity(name, parameter);
        processModifiers(modifiers, parameter);

        setDefinesParameterPropertyForBehavioralEntity(parent, parameter);

        return parameter;
    }

    private void processModifiers(List<String> modifiers, Individual individual) {
        for (String modifier : modifiers) {
            setHasModifierForNamedEntity(modifier, individual);
        }
    }

    private void setDefinesParameterPropertyForBehavioralEntity(
            Individual parent, Individual parameterIndividual) {
        ObjectProperty definesParameterProperty =
                classesAndProperties.getDefinesParameterProperty(model);
        parent.addProperty(definesParameterProperty, parameterIndividual);
    }

    private void setIsExternalTypeProperty(boolean isExternal, Individual externalType) {
        DatatypeProperty isExternalProperty = classesAndProperties.getIsExternalProperty(model);
        externalType.addLiteral(isExternalProperty, isExternal);
    }

    private void setHasDeclaredExceptionProperty(
            Individual thrownException, Individual methodIndividual) {
        ObjectProperty hasDeclaredExceptionProperty =
                classesAndProperties.getHasDeclaredExceptionProperty(model);
        methodIndividual.addProperty(hasDeclaredExceptionProperty, thrownException);
    }

    private Individual getDeclaredExceptionIndividual() {
        return classesAndProperties.getDeclaredExceptionIndividual(model, exceptionId++);
    }

    public Individual getThrownExceptionIndividual(Individual type, Individual parent) {
        Individual exception =
                classesAndProperties.getThrownExceptionIndividual(model, exceptionId++);
        setExceptionHasDefiningClass(exception, type);
        setThrowsExceptionProperty(exception, parent);
        return exception;
    }

    public Individual getCaughtExceptionIndividual(Individual type, Individual parent) {
        Individual exception =
                classesAndProperties.getCaughtExceptionIndividual(model, exceptionId++);
        setExceptionHasDefiningClass(exception, type);
        setHasCaughtExceptionProperty(exception, parent);
        return exception;
    }

    private void setExceptionHasDefiningClass(
            Individual exceptionIndividual, Individual definingClass) {
        ObjectProperty hasDefiningClassProperty =
                classesAndProperties.getHasDefiningClassProperty(model);
        exceptionIndividual.addProperty(hasDefiningClassProperty, definingClass);
    }

    public void addLocalVariable(String name, Individual type, Individual parent) {
        Individual variable =
                classesAndProperties.getLocalVariableIndividual(model, localVariableId++);

        setDeclaredTypeForBehavioralOrStructuralEntity(variable, type);
        setHasNamePropertyForNamedEntity(name, variable);
        setDefinesLocalVariableProperty(parent, variable);
    }

    private void setHasCaughtExceptionProperty(
            Individual caughtExceptionIndividual, Individual methodIndividual) {
        ObjectProperty hasCaughtExceptionProperty =
                classesAndProperties.getHasCaughtExceptionProperty(model);
        methodIndividual.addProperty(hasCaughtExceptionProperty, caughtExceptionIndividual);
    }

    private void setThrowsExceptionProperty(
            Individual thrownExceptionIndividual, Individual methodIndividual) {
        ObjectProperty throwsExceptionProperty =
                classesAndProperties.getThrowsExceptionProperty(model);
        methodIndividual.addProperty(throwsExceptionProperty, thrownExceptionIndividual);
    }

    private void setIsConstructorProperty(boolean isConstructor, Individual methodIndividual) {
        DatatypeProperty isConstructorProperty =
                classesAndProperties.getIsConstructorProperty(model);
        methodIndividual.addLiteral(isConstructorProperty, isConstructor);
    }

    public void setNamespaceContainsProperty(
            Individual parentNamespace, Individual namespaceIndividual) {
        ObjectProperty namespaceContainsProperty =
                classesAndProperties.getNamespaceContainsProperty(model);
        parentNamespace.addProperty(namespaceContainsProperty, namespaceIndividual);
    }

    public void add(OntModel ontology) {
        model.add(ontology);
    }

    public void setHasAnnotationTypeAttributeForAnnotationType(
            Individual annotationTypeIndividual,
            String nameOfAnnotationAttribute,
            Individual annotationTypeAttributeIndividual) {
        ObjectProperty hasAnnotationTypeAttribute =
                classesAndProperties.getHasAnnotationTypeAttributeProperty(model);
        annotationTypeIndividual.addProperty(
                hasAnnotationTypeAttribute, annotationTypeAttributeIndividual);

        Map<String, Individual> annotationTypeAttributes =
                annotationTypeToAttribute.get(annotationTypeIndividual);

        if (annotationTypeAttributes == null) {
            annotationTypeAttributes = new HashMap<>();
        }
        annotationTypeAttributes.put(nameOfAnnotationAttribute, annotationTypeAttributeIndividual);
        annotationTypeToAttribute.put(annotationTypeIndividual, annotationTypeAttributes);
    }

    private Individual getAnnotationTypeAttributeOfAnnotationTypeByName(
            String name, Individual annotationType) {
        Map<String, Individual> annotationTypeAttributes =
                annotationTypeToAttribute.get(annotationType);

        if (annotationTypeAttributes == null) {
            return null;
        }

        for (String annotationTypeAttributeName : annotationTypeAttributes.keySet()) {
            if (annotationTypeAttributeName.equals(name)) {
                return annotationTypeAttributes.get(name);
            }
        }
        return null;
    }

    private Individual getAnnotationInstanceAttributeIndividual() {
        return classesAndProperties.getAnnotationInstanceAttributeIndividual(
                model, annotationInstanceAttributeId++);
    }

    private void setHasAnnotationTypeAttributeForAnnotationInstanceAttribute(
            Individual annotationTypeAttribute, Individual annotationInstanceAttribute) {
        ObjectProperty property = classesAndProperties.getHasAnnotationTypeAttributeProperty(model);
        annotationInstanceAttribute.addProperty(property, annotationTypeAttribute);
    }

    private void setHasAnnotationInstanceAttributeForAnnotationInstance(
            Individual annotationInstanceAttribute, Individual annotationInstance) {
        ObjectProperty property = classesAndProperties.getHasAnnotationInstanceAttribute(model);
        annotationInstance.addProperty(property, annotationInstanceAttribute);
    }

    private void setHasValueForAnnotationInstanceAttribute(
            Individual annotationInstanceAttribute, String value) {
        DatatypeProperty property = classesAndProperties.getHasValueProperty(model);
        annotationInstanceAttribute.addLiteral(property, value);
    }

    private void setHasFullQualifiedNameForType(
            Individual currentUnitIndividual, String fullQualifiedName) {
        DatatypeProperty property = classesAndProperties.getHasFullQualifiedNameProperty(model);
        currentUnitIndividual.addLiteral(property, fullQualifiedName);
    }

    private void setDefinesLocalVariableProperty(
            Individual parent, Individual localVariableIndividual) {

        ObjectProperty property = classesAndProperties.getDefinesVariableProperty(model);
        parent.addProperty(property, localVariableIndividual);
    }

    public void setImports(Individual accessingUnit, Individual importedType) {
        ObjectProperty property = classesAndProperties.getImportsProperty(model);
        accessingUnit.addProperty(property, importedType);
    }

    public void setInvokes(Individual accessingUnit, Individual receiver) {
        ObjectProperty property = classesAndProperties.getInvokesProperty(model);
        accessingUnit.addProperty(property, receiver);
    }

    /**
     * Returns an individual representing the given reference type (class, interface, enumeration,
     * annotation). Primitive types are not supported, use {@link
     * #getPrimitiveTypeIndividual(String)} instead.
     *
     * @param name Fully qualified and resolved type name.
     * @return An Individual representing the given type.
     */
    public Individual getReferenceTypeIndividual(String name) {
        if (!famixTypeIndividualCache.containsKey(name)) {
            // type not defined in source code, e.g. external library or java lib
            // there has to be a previous run where all types are inserted into the map before this
            // method can be called
            Individual tmp = createFamixClassWithName(name);
            setHasNamePropertyForNamedEntity(name, tmp);
            setIsExternalTypeProperty(true, tmp);
            return tmp;
        }

        // type was visited in the first run, its individual is stored in the cache
        return famixTypeIndividualCache.get(name);
    }
}
