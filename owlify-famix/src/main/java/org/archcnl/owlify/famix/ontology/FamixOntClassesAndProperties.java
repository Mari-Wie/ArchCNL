package org.archcnl.owlify.famix.ontology;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class FamixOntClassesAndProperties {
    private static final String NAMESPACE_PREFIX = "http://arch-ont.org/ontologies/famix.owl#";

    public String getOntologyNamespace() {
        return NAMESPACE_PREFIX;
    }

    public Individual createNamespaceIndividual(OntModel model, long namespaceId) {
        OntClass namespaceClass = model.getOntClass(NAMESPACE_PREFIX + "Namespace");
        return model.createIndividual(
                this.NAMESPACE_PREFIX + "Namespace" + namespaceId, namespaceClass);
    }

    public Individual getFamixClassIndividual(OntModel model, String name, long classID) {
        OntClass famixClass = model.getOntClass(NAMESPACE_PREFIX + "FamixClass");
        return model.createIndividual(this.NAMESPACE_PREFIX + name + classID, famixClass);
    }

    public Individual getAnnotationTypeIndividual(
            OntModel model, String annotationTypeName, long annotationTypeID) {
        OntClass annotationClass = model.getOntClass(NAMESPACE_PREFIX + "AnnotationType");
        return model.createIndividual(
                this.NAMESPACE_PREFIX + annotationTypeName + annotationTypeID, annotationClass);
    }

    public Individual getEnumTypeIndividual(OntModel model, String enumTypeName, long enumTypeID) {
        OntClass enumClass = model.getOntClass(NAMESPACE_PREFIX + "Enum");
        return model.createIndividual(this.NAMESPACE_PREFIX + enumTypeName + enumTypeID, enumClass);
    }

    public DatatypeProperty getHasNameProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "hasName");
    }

    public DatatypeProperty getIsInterfaceProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "isInterface");
    }

    public DatatypeProperty getHasModifierProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "hasModifier");
    }

    public ObjectProperty getHasAnnotationInstanceProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasAnnotationInstance");
    }

    public Individual getInheritanceIndividual(OntModel model, long inheritanceID) {
        OntClass inheritanceClass = model.getOntClass(NAMESPACE_PREFIX + "Inheritance");
        return model.createIndividual(
                NAMESPACE_PREFIX + "Inheritance" + inheritanceID, inheritanceClass);
    }

    public ObjectProperty getSubClassProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasSubClass");
    }

    public ObjectProperty getSuperClassProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasSuperClass");
    }

    public Individual getAnnotationInstanceIndividual(OntModel model, long annotationInstanceID) {
        OntClass annotationInstanceClass =
                model.getOntClass(NAMESPACE_PREFIX + "AnnotationInstance");
        return model.createIndividual(
                NAMESPACE_PREFIX + "AnnotationInstance" + annotationInstanceID,
                annotationInstanceClass);
    }

    public ObjectProperty getHasAnnotationTypeProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasAnnotationType");
    }

    public Individual getAttributeIndividual(OntModel model, long attributeID) {
        OntClass attribute = model.getOntClass(NAMESPACE_PREFIX + "Attribute");
        return model.createIndividual(NAMESPACE_PREFIX + "Attribute" + attributeID, attribute);
    }

    public ObjectProperty getDefinesAttributeObjectProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "definesAttribute");
    }

    public ObjectProperty getHasDeclaredTypeProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasDeclaredType");
    }

    public Individual getPrimitiveTypeIndividual(OntModel model, String primitiveTypeName) {
        return model.getIndividual(NAMESPACE_PREFIX + primitiveTypeName);
    }

    public Individual getMethodIndividual(OntModel model, long methodId) {
        OntClass method = model.getOntClass(NAMESPACE_PREFIX + "Method");
        return model.createIndividual(NAMESPACE_PREFIX + "Method" + methodId, method);
    }

    public DatatypeProperty getHasSignatureProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "hasSignature");
    }

    public ObjectProperty getDefinesMethodProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "definesMethod");
    }

    public Individual getParameterIndividual(OntModel model, long parameterId) {
        OntClass parameter = model.getOntClass(NAMESPACE_PREFIX + "Parameter");
        return model.createIndividual(NAMESPACE_PREFIX + "Parameter" + parameterId, parameter);
    }

    public ObjectProperty getDefinesParameterProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "definesParameter");
    }

    public DatatypeProperty getIsExternalProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "isExternal");
    }

    public ObjectProperty getHasDeclaredExceptionProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasDeclaredException");
    }

    public OntClass getDeclaredExceptionClass(OntModel model) {
        return model.getOntClass(NAMESPACE_PREFIX + "DeclaredException");
    }

    public Individual getDeclaredExceptionIndividual(OntModel model, long declaredId) {
        OntClass declared = model.getOntClass(NAMESPACE_PREFIX + "DeclaredException");
        return model.createIndividual(
                NAMESPACE_PREFIX + "DeclaredException" + declaredId, declared);
    }

    public Individual getThrownExceptionIndividual(OntModel model, long thrownId) {
        OntClass thrown = model.getOntClass(NAMESPACE_PREFIX + "ThrownException");
        return model.createIndividual(NAMESPACE_PREFIX + "ThrownException" + thrownId, thrown);
    }

    public Individual getCaughtExceptionIndividual(OntModel model, long caughtId) {
        OntClass caught = model.getOntClass(NAMESPACE_PREFIX + "CaughtException");
        return model.createIndividual(NAMESPACE_PREFIX + "CaughtException" + caughtId, caught);
    }

    public ObjectProperty getHasCaughtExceptionProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasCaughtException");
    }

    public ObjectProperty getThrowsExceptionProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "throwsException");
    }

    public ObjectProperty getHasDefiningClassProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasDefiningClass");
    }

    public Individual getLocalVariableIndividual(OntModel model, long localVariableId) {
        OntClass localVariableClass = model.getOntClass(NAMESPACE_PREFIX + "LocalVariable");
        return model.createIndividual(
                NAMESPACE_PREFIX + "LocalVariable" + localVariableId, localVariableClass);
    }

    public DatatypeProperty getIsConstructorProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "isConstructor");
    }

    public ObjectProperty getNamespaceContainsProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "namespaceContains");
    }

    public Individual getAnnotationTypeAttributeIndividual(OntModel model, long id) {
        OntClass typeAttribute = model.getOntClass(NAMESPACE_PREFIX + "AnnotationTypeAttribute");

        return model.createIndividual(
                NAMESPACE_PREFIX + "AnnotationTypeAttribute" + id, typeAttribute);
    }

    public ObjectProperty getHasAnnotationTypeAttributeProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasAnnotationTypeAttribute");
    }

    public Individual getAnnotationInstanceAttributeIndividual(OntModel model, long id) {
        OntClass instanceAttribute =
                model.getOntClass(NAMESPACE_PREFIX + "AnnotationInstanceAttribute");
        return model.createIndividual(
                NAMESPACE_PREFIX + "AnnotationInstanceAttribute" + id, instanceAttribute);
    }

    public ObjectProperty getHasAnnotationInstanceAttribute(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasAnnotationInstanceAttribute");
    }

    public DatatypeProperty getHasValueProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "hasValue");
    }

    public DatatypeProperty getHasFullQualifiedNameProperty(OntModel model) {
        return model.getDatatypeProperty(NAMESPACE_PREFIX + "hasFullQualifiedName");
    }

    public ObjectProperty getDefinesVariableProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "definesVariable");
    }

    public Individual getInvocationIndividual(OntModel model, long invocationId) {
        OntClass invocation = model.getOntClass(NAMESPACE_PREFIX + "Invocation");
        return model.createIndividual(NAMESPACE_PREFIX + "Invocation" + invocationId, invocation);
    }

    public ObjectProperty getHasReceiverProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasReceiver");
    }

    public ObjectProperty getHasSenderProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "hasSender");
    }

    public Individual getImportAssociationIndividual(OntModel model, long importId) {
        OntClass importAssociation = model.getOntClass(NAMESPACE_PREFIX + "Import");
        return model.createIndividual(NAMESPACE_PREFIX + "Import" + importId, importAssociation);
    }

    public ObjectProperty getImportsProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "imports");
    }

    public ObjectProperty getInvokesProperty(OntModel model) {
        return model.getObjectProperty(NAMESPACE_PREFIX + "invokes");
    }
}
