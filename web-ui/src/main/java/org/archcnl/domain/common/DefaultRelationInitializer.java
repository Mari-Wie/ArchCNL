package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.ConformanceRelation;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.MainOntologyRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;

public class DefaultRelationInitializer {

    private ConceptManager conceptManager;

    public DefaultRelationInitializer(ConceptManager conceptManager) {
        this.conceptManager = conceptManager;
    }

    public List<Relation> getDefaultRelations() throws ConceptDoesNotExistException {
        List<Relation> defaultRelations = new ArrayList<>();
        defaultRelations.add(getTypeRelation());
        defaultRelations.addAll(getBuiltinRelations());
        defaultRelations.addAll(getMainOntologyRelations());
        defaultRelations.addAll(getStringRelations());
        defaultRelations.addAll(getBoolRelations());
        defaultRelations.addAll(getObjectRelations());
        defaultRelations.addAll(getConformanceRelations());
        return defaultRelations;
    }

    private Relation getTypeRelation() {
        return TypeRelation.getTyperelation();
    }

    private List<Relation> getBuiltinRelations() {
        return Arrays.asList(JenaBuiltinRelation.getRegexRelation());
    }

    private List<Relation> getMainOntologyRelations() throws ConceptDoesNotExistException {
        List<Relation> relations = new ArrayList<>();
        Set<ActualObjectType> file = Collections.singleton(getConcept("SoftwareArtifactFile"));
        relations.add(
                new MainOntologyRelation(
                        "hasPath",
                        "The path of the file.",
                        file,
                        Collections.singleton(new StringValue(""))));
        relations.add(
                new MainOntologyRelation(
                        "containsArtifact",
                        "The top level source code entities contained in this file.",
                        file,
                        getFamixTypes()));
        return relations;
    }

    private List<Relation> getStringRelations() throws ConceptDoesNotExistException {
        List<Relation> relations = new ArrayList<>();
        final Set<ActualObjectType> stringValue = Collections.singleton(new StringValue(""));
        relations.add(
                new FamixRelation(
                        "hasModifier",
                        "This relation is used to state that the subject has a modifier with the name stated in the object. Examples for modifiers are access modifiers (e.g. public) and mutability modifiers (e.g. final).",
                        getHasModifierSubjects(),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "hasModifier",
                        "This relation is used to state that the subject has a modifier with the name stated in the object. Examples for modifiers are access modifiers (e.g. public) and mutability modifiers (e.g. final).",
                        getHasModifierSubjects(),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "hasName",
                        "This relation is used to state that the subject has the name which is stated in the object.",
                        getNamesEntities(),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "isLocatedAt",
                        "This relation is used to state that the subject is located at the locations stated in the object.",
                        getIsLocatedAtSubjects(),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "hasSignature",
                        "This relation is used to state that a method has the signature which is stated in the object.",
                        Collections.singleton(getConcept("Method")),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "hasFullQualifiedName",
                        "This relation is used to state that an entity has the name which is stated in the object.",
                        getFamixTypes(),
                        stringValue));
        relations.add(
                new FamixRelation(
                        "hasValue",
                        "This relation is used to state that an AnnotationInstanceAttribute (which is an attribute-value pair) has the value which is stated in the object.",
                        Collections.singleton(getConcept("AnnotationInstanceAttribute")),
                        stringValue));
        return relations;
    }

    private List<Relation> getBoolRelations() throws ConceptDoesNotExistException {
        List<Relation> relations = new ArrayList<>();
        final Set<ActualObjectType> boolObject = Collections.singleton(new BooleanValue(false));
        relations.add(
                new FamixRelation(
                        "isConstructor",
                        "This relation is used to state that the subject is or isn't a constructor (based on a value in the object). A constructor is special method that is called when an object is instantiated.",
                        Collections.singleton(getConcept("Method")),
                        boolObject));
        relations.add(
                new FamixRelation(
                        "isExternal",
                        "This relation is used to state that the subject is or isn't an external type (based on a value in the object).",
                        getFamixTypes(),
                        boolObject));
        relations.add(
                new FamixRelation(
                        "isInterface",
                        "This relation is used to state that the subject is or isn't an interface (based on a value in the object). An interface is an abstract type that is used to specify a behavior that classes must implement.",
                        Collections.singleton(getConcept("FamixClass")),
                        boolObject));
        return relations;
    }

    private List<Relation> getObjectRelations() throws ConceptDoesNotExistException {
        List<Relation> relations = new ArrayList<>();
        // FamixClass relations
        Set<ActualObjectType> famixClass = Collections.singleton(getConcept("FamixClass"));
        Set<ActualObjectType> method = Collections.singleton(getConcept("Method"));
        Set<ActualObjectType> inheritance = Collections.singleton(getConcept("Inheritance"));
        relations.add(
                new FamixRelation(
                        "hasDeclaredException",
                        "This relation is used to state that the subject (for example a method) has a specified in the object exception type.",
                        method,
                        famixClass));
        relations.add(
                new FamixRelation(
                        "hasCaughtException",
                        "This relation is used to state that the subject (for example a method) catches a specified in the object exception type.",
                        method,
                        famixClass));
        relations.add(
                new FamixRelation(
                        "throwsException",
                        "This relation is used to state that the subject (for example a method) throws a specified in the object exception type.",
                        method,
                        famixClass));
        relations.add(
                new FamixRelation(
                        "hasSubClass",
                        "This relation is used to state that the subject has a sub class of the specified in the object type.",
                        inheritance,
                        famixClass));
        relations.add(
                new FamixRelation(
                        "hasSuperClass",
                        "This relation is used to state that the subject extends a super class of the specified in the object type.",
                        inheritance,
                        famixClass));

        // Parameter relations
        Set<ActualObjectType> parameter = Collections.singleton(getConcept("Parameter"));
        relations.add(
                new FamixRelation(
                        "definesParameter",
                        "This relation is used to state that the subject (for example a method) has specified in the object parameters.",
                        method,
                        parameter));

        // LocalVariable relations
        Set<ActualObjectType> localVariable = Collections.singleton(getConcept("LocalVariable"));
        relations.add(
                new FamixRelation(
                        "definesVariable",
                        "This relation is used to state that the subject (for example a method) defines a specified in the object variable.",
                        method,
                        localVariable));

        // AnnotationInstance relations
        Set<ActualObjectType> annotationInstance =
                Collections.singleton(getConcept("AnnotationInstance"));
        relations.add(
                new FamixRelation(
                        "hasAnnotationInstance",
                        "This relation is used to state that the subject has a specified in the object annotation.",
                        getHasAnnotationInstanceSubjects(),
                        annotationInstance));

        // AnnotationType relations
        relations.add(
                new FamixRelation(
                        "hasAnnotationType",
                        "This relation is used to state that the subject has a specified in the object annotations type.",
                        annotationInstance,
                        Collections.singleton(getConcept("AnnotationType"))));

        // AnnotationTypeAttribute relations
        relations.add(
                new FamixRelation(
                        "hasAnnotationTypeAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object attributes of an annotation type.",
                        getHasAnnotationTypeAttributeSubjects(),
                        Collections.singleton(getConcept("AnnotationTypeAttribute"))));

        // AnnotationInstanceAttribute relations
        relations.add(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object an attribute-value pair.",
                        annotationInstance,
                        Collections.singleton(getConcept("AnnotationInstanceAttribute"))));

        // Attribute relations
        Set<ActualObjectType> attribute = Collections.singleton(getConcept("Attribute"));
        relations.add(
                new FamixRelation(
                        "definesAttribute",
                        "This relation is used to state that the subject (for example a class) defines specified in the object attribute.",
                        getFamixClassAndEnum(),
                        attribute));

        // Method relations
        relations.add(
                new FamixRelation(
                        "definesMethod",
                        "This relation is used to state that the subject (for example a class) defines specified in the object method.",
                        getFamixClassAndEnum(),
                        method));

        // Type relations
        relations.add(
                new FamixRelation(
                        "imports",
                        "This relation is used to state that the subject (for example a class) imports (has dependency to) specified in the object type, class etc.",
                        getFamixTypes(),
                        getFamixTypes()));

        // Class and Enum relations
        relations.add(
                new FamixRelation(
                        "definesNestedType",
                        "This relation is used to state that the subject (for example a class) defines a nested specified in the object type.",
                        getFamixClassAndEnum(),
                        getFamixTypes()));

        // Type + NameSpace relations
        relations.add(
                new FamixRelation(
                        "namespaceContains",
                        "This relation is used to state that the subject (namespace) contains a specified in the object type, class etc.",
                        Collections.singleton(getConcept("Namespace")),
                        getNamespaceContainsObjects()));

        // Type + Primitive relations
        relations.add(
                new FamixRelation(
                        "hasDeclaredType",
                        "This relation is used to state that the subject (for example attribute) has a specified in the object type.",
                        getHasDeclaredTypeSubjects(),
                        getHasDeclaredTypeObjects()));
        return relations;
    }

    private List<Relation> getConformanceRelations() throws ConceptDoesNotExistException {
        List<Relation> relations = new ArrayList<>();
        // Relations "hasProofText" and "hasViolationText" are excluded here as they are currently
        // unused
        // TODO: "hasRuleID" and "hasCheckingDate" are also excluded as their ObjectType is unclear
        final Set<ActualObjectType> architectureRule =
                new HashSet<>(Arrays.asList(getConcept("ArchitectureRule")));
        relations.add(
                new ConformanceRelation(
                        "hasRuleRepresentation",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object representation (in most cases string representation).",
                        architectureRule,
                        new HashSet<>(Arrays.asList(new StringValue("")))));
        relations.add(
                new ConformanceRelation(
                        "hasRuleType",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object rule type.",
                        architectureRule,
                        new HashSet<>(Arrays.asList(new StringValue("")))));
        final Set<ActualObjectType> proof = new HashSet<>(Arrays.asList(getConcept("Proof")));
        relations.add(
                new ConformanceRelation(
                        "hasNotInferredStatement",
                        "This relation is used to state that the subject hasn't / doesn't correspond to the specified in the object statements.",
                        proof,
                        new HashSet<>(Arrays.asList(getConcept("NotInferredStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "This relation is used to state that the subject has / corresponds to the specified in the object statements.",
                        proof,
                        new HashSet<>(Arrays.asList(getConcept("AssertedStatement")))));
        final Set<ActualObjectType> statements = new HashSet<>();
        statements.add(getConcept("NotInferredStatement"));
        statements.add(getConcept("AssertedStatement"));
        relations.add(
                new ConformanceRelation(
                        "hasSubject",
                        "This relation is used to state that the subject (for example statement) has a specified subject.",
                        statements,
                        new HashSet<>()));
        relations.add(
                new ConformanceRelation(
                        "hasPredicate",
                        "This relation is used to state that the subject (for example statement) has a specified predicate.",
                        statements,
                        new HashSet<>()));
        relations.add(
                new ConformanceRelation(
                        "hasObject",
                        "This relation is used to state that the subject (for example statement) has a specified object.",
                        statements,
                        new HashSet<>()));
        final Set<ActualObjectType> architecturViolation =
                new HashSet<>(Arrays.asList(getConcept("ArchitectureViolation")));
        relations.add(
                new ConformanceRelation(
                        "proofs",
                        "This relation is used to state that the subject (proof) verifies a specified in the object violation.",
                        proof,
                        architecturViolation));
        final Set<ActualObjectType> conformanceCheck =
                new HashSet<>(Arrays.asList(getConcept("ConformanceCheck")));
        relations.add(
                new ConformanceRelation(
                        "hasDetected",
                        "This relation is used to state that the subject (conformance check) has detected a specified in the object violation.",
                        conformanceCheck,
                        architecturViolation));
        relations.add(
                new ConformanceRelation(
                        "hasViolation",
                        "This relation is used to state that the subject has a specified in the object violation.",
                        architectureRule,
                        architecturViolation));
        relations.add(
                new ConformanceRelation(
                        "violates",
                        "This relation is used to state that the subject violates a specified in the object rule.",
                        architecturViolation,
                        architectureRule));
        relations.add(
                new ConformanceRelation(
                        "validates",
                        "This relation is used to state that the subject validates a specified in the object rule or conformance check.",
                        conformanceCheck,
                        architectureRule));
        return relations;
    }

    private Set<ActualObjectType> getFamixTypes() throws ConceptDoesNotExistException {
        return new HashSet<>(
                Arrays.asList(
                        getConcept("FamixClass"),
                        getConcept("Enum"),
                        getConcept("AnnotationType")));
    }

    private Set<ActualObjectType> getFamixClassAndEnum() throws ConceptDoesNotExistException {
        return new HashSet<>(Arrays.asList(getConcept("FamixClass"), getConcept("Enum")));
    }

    private Set<ActualObjectType> getNamespaceContainsObjects()
            throws ConceptDoesNotExistException {
        return new HashSet<>(
                Arrays.asList(
                        getConcept("FamixClass"),
                        getConcept("Enum"),
                        getConcept("AnnotationType"),
                        getConcept("Namespace")));
    }

    private Set<ActualObjectType> getNamesEntities() throws ConceptDoesNotExistException {
        Set<ActualObjectType> namedEntity = new HashSet<>();
        namedEntity.add(getConcept("Namespace"));
        namedEntity.add(getConcept("AnnotationType"));
        namedEntity.add(getConcept("AnnotationTypeAttribute"));
        namedEntity.add(getConcept("Enum"));
        namedEntity.add(getConcept("FamixClass"));
        namedEntity.add(getConcept("Method"));
        namedEntity.add(getConcept("Parameter"));
        namedEntity.add(getConcept("LocalVariable"));
        namedEntity.add(getConcept("Attribute"));
        namedEntity.add(getConcept("PrimitiveType"));
        return namedEntity;
    }

    private Set<ActualObjectType> getHasModifierSubjects() throws ConceptDoesNotExistException {
        Set<ActualObjectType> hasModifierSubjects = new HashSet<>();
        hasModifierSubjects.add(getConcept("AnnotationType"));
        hasModifierSubjects.add(getConcept("Enum"));
        hasModifierSubjects.add(getConcept("FamixClass"));
        hasModifierSubjects.add(getConcept("Method"));
        hasModifierSubjects.add(getConcept("Parameter"));
        hasModifierSubjects.add(getConcept("LocalVariable"));
        hasModifierSubjects.add(getConcept("Attribute"));
        return hasModifierSubjects;
    }

    private Set<ActualObjectType> getIsLocatedAtSubjects() throws ConceptDoesNotExistException {
        Set<ActualObjectType> isLocatedAtSubjects = new HashSet<>();
        isLocatedAtSubjects.add(getConcept("AnnotationType"));
        isLocatedAtSubjects.add(getConcept("AnnotationInstance"));
        isLocatedAtSubjects.add(getConcept("Enum"));
        isLocatedAtSubjects.add(getConcept("FamixClass"));
        isLocatedAtSubjects.add(getConcept("Method"));
        isLocatedAtSubjects.add(getConcept("Parameter"));
        isLocatedAtSubjects.add(getConcept("LocalVariable"));
        isLocatedAtSubjects.add(getConcept("Attribute"));
        return isLocatedAtSubjects;
    }

    private Set<ActualObjectType> getHasAnnotationInstanceSubjects()
            throws ConceptDoesNotExistException {
        Set<ActualObjectType> hasAnnotationInstanceSubjects = new HashSet<>();
        hasAnnotationInstanceSubjects.add(getConcept("AnnotationType"));
        hasAnnotationInstanceSubjects.add(getConcept("Enum"));
        hasAnnotationInstanceSubjects.add(getConcept("FamixClass"));
        hasAnnotationInstanceSubjects.add(getConcept("Method"));
        hasAnnotationInstanceSubjects.add(getConcept("Parameter"));
        hasAnnotationInstanceSubjects.add(getConcept("Attribute"));
        return hasAnnotationInstanceSubjects;
    }

    private Set<ActualObjectType> getHasAnnotationTypeAttributeSubjects()
            throws ConceptDoesNotExistException {
        return new HashSet<>(
                Arrays.asList(
                        getConcept("AnnotationInstanceAttribute"), getConcept("AnnotationType")));
    }

    private Set<ActualObjectType> getHasDeclaredTypeSubjects() throws ConceptDoesNotExistException {
        return new HashSet<>(
                Arrays.asList(
                        getConcept("Attribute"),
                        getConcept("Method"),
                        getConcept("Parameter"),
                        getConcept("LocalVariable"),
                        getConcept("AnnotationTypeAttribute")));
    }

    private Set<ActualObjectType> getHasDeclaredTypeObjects() throws ConceptDoesNotExistException {
        return new HashSet<>(
                Arrays.asList(
                        getConcept("FamixClass"), getConcept("Enum"), getConcept("PrimitiveType")));
    }

    private Concept getConcept(final String conceptName) throws ConceptDoesNotExistException {
        return conceptManager
                .getConceptByName(conceptName)
                .orElseThrow(() -> new ConceptDoesNotExistException(conceptName));
    }
}
