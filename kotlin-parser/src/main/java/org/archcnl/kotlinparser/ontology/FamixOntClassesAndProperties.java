package org.archcnl.kotlinparser.ontology;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class FamixOntClassesAndProperties {
    private final String famixOntologyNamespace = "http://arch-ont.org/ontologies/famix.owl#";

    public String getOntologyNamespace() {
        return famixOntologyNamespace;
    }

    public Individual createNamespaceIndividual(OntModel model, long namespaceId) {
        OntClass namespaceClass = model.getOntClass(famixOntologyNamespace + "Namespace");
        return model.createIndividual(
                this.famixOntologyNamespace + "Namespace" + namespaceId, namespaceClass);
    }

    public Individual getFamixClassIndividual(OntModel model, String name, long classID) {
        OntClass famixClass = model.getOntClass(famixOntologyNamespace + "FamixClass");
        return model.createIndividual(this.famixOntologyNamespace + name + classID, famixClass);
    }

    public Individual getAnnotationTypeIndividual(
            OntModel model, String annotationTypeName, long annotationTypeID) {
        OntClass annotationClass = model.getOntClass(famixOntologyNamespace + "AnnotationType");
        return model.createIndividual(
                this.famixOntologyNamespace + annotationTypeName + annotationTypeID,
                annotationClass);
    }

    public Individual getEnumTypeIndividual(OntModel model, String enumTypeName, long enumTypeID) {
        OntClass enumClass = model.getOntClass(famixOntologyNamespace + "Enum");
        return model.createIndividual(
                this.famixOntologyNamespace + enumTypeName + enumTypeID, enumClass);
    }

    public DatatypeProperty getHasNameProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "hasName");
    }

    public DatatypeProperty getIsInterfaceProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "isInterface");
    }

    public DatatypeProperty getHasModifierProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "hasModifier");
    }

    public ObjectProperty getHasAnnotationInstanceProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasAnnotationInstance");
    }

    public Individual getInheritanceIndividual(OntModel model, long inheritanceID) {
        OntClass inheritanceClass = model.getOntClass(famixOntologyNamespace + "Inheritance");
        return model.createIndividual(
                famixOntologyNamespace + "Inheritance" + inheritanceID, inheritanceClass);
    }

    public ObjectProperty getSubClassProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasSubClass");
    }

    public ObjectProperty getSuperClassProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasSuperClass");
    }

    public Individual getAnnotationInstanceIndividual(OntModel model, long annotationInstanceID) {
        OntClass annotationInstanceClass =
                model.getOntClass(famixOntologyNamespace + "AnnotationInstance");
        return model.createIndividual(
                famixOntologyNamespace + "AnnotationInstance" + annotationInstanceID,
                annotationInstanceClass);
    }

    public ObjectProperty getHasAnnotationTypeProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasAnnotationType");
    }

    public Individual getAttributeIndividual(OntModel model, long attributeID) {
        OntClass attribute = model.getOntClass(famixOntologyNamespace + "Attribute");
        return model.createIndividual(
                famixOntologyNamespace + "Attribute" + attributeID, attribute);
    }

    public ObjectProperty getDefinesAttributeObjectProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "definesAttribute");
    }

    public ObjectProperty getHasDeclaredTypeProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasDeclaredType");
    }

    public Individual getPrimitiveTypeIndividual(OntModel model, String primitiveTypeName) {
        return model.getIndividual(famixOntologyNamespace + primitiveTypeName);
    }

    public Individual getMethodIndividual(OntModel model, long methodId) {
        OntClass method = model.getOntClass(famixOntologyNamespace + "Method");
        return model.createIndividual(famixOntologyNamespace + "Method" + methodId, method);
    }

    public DatatypeProperty getHasSignatureProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "hasSignature");
    }

    public ObjectProperty getDefinesMethodProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "definesMethod");
    }

    public Individual getParameterIndividual(OntModel model, long parameterId) {
        OntClass parameter = model.getOntClass(famixOntologyNamespace + "Parameter");
        return model.createIndividual(
                famixOntologyNamespace + "Parameter" + parameterId, parameter);
    }

    public ObjectProperty getDefinesParameterProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "definesParameter");
    }

    public DatatypeProperty getIsExternalProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "isExternal");
    }

    public ObjectProperty getHasDeclaredExceptionProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasDeclaredException");
    }

    public OntClass getDeclaredExceptionClass(OntModel model) {
        return model.getOntClass(famixOntologyNamespace + "DeclaredException");
    }

    public Individual getDeclaredExceptionIndividual(OntModel model, long declaredId) {
        OntClass declared = model.getOntClass(famixOntologyNamespace + "DeclaredException");
        return model.createIndividual(
                famixOntologyNamespace + "DeclaredException" + declaredId, declared);
    }

    public Individual getThrownExceptionIndividual(OntModel model, long thrownId) {
        OntClass thrown = model.getOntClass(famixOntologyNamespace + "ThrownException");
        return model.createIndividual(
                famixOntologyNamespace + "ThrownException" + thrownId, thrown);
    }

    public Individual getCaughtExceptionIndividual(OntModel model, long caughtId) {
        OntClass caught = model.getOntClass(famixOntologyNamespace + "CaughtException");
        return model.createIndividual(
                famixOntologyNamespace + "CaughtException" + caughtId, caught);
    }

    public ObjectProperty getHasCaughtExceptionProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasCaughtException");
    }

    public ObjectProperty getThrowsExceptionProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "throwsException");
    }

    public ObjectProperty getHasDefiningClassProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasDefiningClass");
    }

    public Individual getLocalVariableIndividual(OntModel model, long localVariableId) {
        OntClass localVariableClass = model.getOntClass(famixOntologyNamespace + "LocalVariable");
        return model.createIndividual(
                famixOntologyNamespace + "LocalVariable" + localVariableId, localVariableClass);
    }

    public DatatypeProperty getIsConstructorProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "isConstructor");
    }

    public ObjectProperty getNamespaceContainsProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "namespaceContains");
    }

    public Individual getAnnotationTypeAttributeIndividual(OntModel model, long id) {
        OntClass typeAttribute =
                model.getOntClass(famixOntologyNamespace + "AnnotationTypeAttribute");

        return model.createIndividual(
                famixOntologyNamespace + "AnnotationTypeAttribute" + id, typeAttribute);
    }

    public ObjectProperty getHasAnnotationTypeAttributeProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasAnnotationTypeAttribute");
    }

    public Individual getAnnotationInstanceAttributeIndividual(OntModel model, long id) {
        OntClass instanceAttribute =
                model.getOntClass(famixOntologyNamespace + "AnnotationInstanceAttribute");
        return model.createIndividual(
                famixOntologyNamespace + "AnnotationInstanceAttribute" + id, instanceAttribute);
    }

    public ObjectProperty getHasAnnotationInstanceAttribute(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasAnnotationInstanceAttribute");
    }

    public DatatypeProperty getHasValueProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "hasValue");
    }

    public DatatypeProperty getHasFullQualifiedNameProperty(OntModel model) {
        return model.getDatatypeProperty(famixOntologyNamespace + "hasFullQualifiedName");
    }

    public ObjectProperty getDefinesVariableProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "definesVariable");
    }

    public Individual getInvocationIndividual(OntModel model, long invocationId) {
        OntClass invocation = model.getOntClass(famixOntologyNamespace + "Invocation");
        return model.createIndividual(
                famixOntologyNamespace + "Invocation" + invocationId, invocation);
    }

    public ObjectProperty getHasReceiverProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasReceiver");
    }

    public ObjectProperty getHasSenderProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "hasSender");
    }

    public Individual getImportAssociationIndividual(OntModel model, long importId) {
        OntClass importAssociation = model.getOntClass(famixOntologyNamespace + "Import");
        return model.createIndividual(
                famixOntologyNamespace + "Import" + importId, importAssociation);
    }

    public ObjectProperty getImportsProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "imports");
    }

    public ObjectProperty getInvokesProperty(OntModel model) {
        return model.getObjectProperty(famixOntologyNamespace + "invokes");
    }
}
