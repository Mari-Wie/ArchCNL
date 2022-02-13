package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.ConformanceRelation;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class RelationManager extends HierarchyManager<Relation> {

    private List<Relation> relations;

    public RelationManager(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        relations = new LinkedList<>();

        addHierarchyRoot("Default Relations");
        addHierarchyRoot("Custom Relations");
        initializeTypeRelation();
        initializeBoolRelations();
        initializeStringRelations();
        initializeSpecialRelations();
        initializeObjectRelations(conceptManager);
        initializeConformanceRelations(conceptManager);
    }

    public void addRelation(final Relation relation) throws RelationAlreadyExistsException {
        if (doesRelationExist(relation)) {
            throw new RelationAlreadyExistsException(relation.getName());
        }
        relations.add(relation);
    }

    public void add(Relation relation, HierarchyNode<Relation> parent)
            throws RelationAlreadyExistsException {
        addRelation(relation);
        parent.add(relation);
    }

    public void addToParent(Relation relation, HierarchyNode<Relation> parent)
            throws RelationAlreadyExistsException {
        addRelation(relation);
        parent.add(relation);
    }

    public void addToParent(Relation relation, String parentName)
            throws RelationAlreadyExistsException {
        addRelation(relation);
        Optional<HierarchyNode<Relation>> parent =
                hierarchy_roots.stream()
                        .filter(node -> parentName.equals(node.getName()))
                        .findAny();
        if (!parent.isPresent()) {
            // TODO: error handling
        }
        parent.get().add(relation);
    }
    // TODO: fix this lazy coupout by removing it and refactoring the init functions. This function
    // is only here because I was to lazy to fix all the init functions.
    public void addToDefault(Relation relation) {
        try {
            addToParent(relation, "Default Relations");
        } catch (RelationAlreadyExistsException e) {
            e.printStackTrace();
            throw new RuntimeException("Relation already exists");
        }
    }

    public void addOrAppend(final CustomRelation relation) throws UnrelatedMappingException {
        try {
            if (!doesRelationExist(relation)) {
                addRelation(relation);
            } else {
                final Optional<Relation> existingRelationOpt =
                        getRelationByName(relation.getName());
                if (existingRelationOpt.isPresent()
                        && existingRelationOpt.get() instanceof CustomRelation) {
                    final CustomRelation existingCustomRelation =
                            (CustomRelation) existingRelationOpt.get();
                    final Optional<RelationMapping> existingMapping =
                            existingCustomRelation.getMapping();
                    final Optional<RelationMapping> newMapping = relation.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomRelation.setMapping(newMapping.get());
                    }
                }
            }
        } catch (final RelationAlreadyExistsException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of of mapping \""
                            + relation.getName()
                            + "\" failed unexpectedly.");
        }
    }

    public Optional<Relation> getRelationByName(final String name) {
        return relations.stream().filter(relation -> name.equals(relation.getName())).findAny();
    }

    public Optional<Relation> getRelationByRealName(final String realName) {
        for (final Relation relation : relations) {
            if (relation instanceof JenaBuiltinRelation) {
                final JenaBuiltinRelation specialRelation = (JenaBuiltinRelation) relation;
                if (realName.equals(specialRelation.getRealName())) {
                    return Optional.of(specialRelation);
                }
            } else if (relation instanceof TypeRelation) {
                final TypeRelation typeRelation = (TypeRelation) relation;
                if (realName.equals(typeRelation.getRealName())) {
                    return Optional.of(typeRelation);
                }
            }
        }
        return Optional.empty();
    }

    public boolean doesRelationExist(final Relation relation) {
        return relations.stream()
                .anyMatch(
                        existingRelation -> relation.getName().equals(existingRelation.getName()));
    }

    public void removeRelation(Relation relation) {
        relations.remove(relation);
        removeFromHierarchy(new HierarchyNode<>(relation));
    }

    private void initializeSpecialRelations() {
        final List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        addToDefault(
                new JenaBuiltinRelation(
                        "matches",
                        "regex",
                        "This relation is used to state that a literal given as the subject matches against the regular expression pattern stated in the object. The syntax of the regular expression pattern is according to java.util.regex.",
                        stringConcept));
    }

    private void initializeTypeRelation() {
        TypeRelation typeRelation = TypeRelation.getTyperelation();
        typeRelation.setDescription(
                "This relation is used to state that the subject is an instance of the class stated in the object.");
        addToDefault(typeRelation);
    }

    private void initializeStringRelations() {
        final List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        addToDefault(
                new FamixRelation(
                        "hasModifier",
                        "This relation is used to state that the subject has a modifier with the name stated in the object. Examples for modifiers are access modifiers (e.g. public) and mutability modifiers (e.g. final).",
                        stringConcept));
        addToDefault(
                new FamixRelation(
                        "hasName",
                        "This relation is used to state that the subject has the name which is stated in the object.",
                        stringConcept));
        addToDefault(
                new FamixRelation(
                        "hasSignature",
                        "This relation is used to state that a method has the signature which is stated in the object.",
                        stringConcept));
        addToDefault(
                new FamixRelation(
                        "hasValue",
                        "This relation is used to state that an AnnotationInstanceAttribute (which is an attribute-value pair) has the value which is stated in the object.",
                        stringConcept));
        addToDefault(
                new FamixRelation(
                        "hasFullQualifiedName",
                        "This relation is used to state that an entity has the name which is stated in the object.",
                        stringConcept));
    }

    private void initializeBoolRelations() {
        final List<ActualObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        addToDefault(
                new FamixRelation(
                        "isConstructor",
                        "This relation is used to state that the subject is or isn't a constructor (based on a value in the object). A constructor is special method that is called when an object is instantiated.",
                        boolConcept));
        addToDefault(
                new FamixRelation(
                        "isExternal",
                        "This relation is used to state that the subject is or isn't an external type (based on a value in the object).",
                        boolConcept));
        addToDefault(
                new FamixRelation(
                        "isInterface",
                        "This relation is used to state that the subject is or isn't an interface (based on a value in the object). An interface is an abstract type that is used to specify a behavior that classes must implement.",
                        boolConcept));
    }

    private void initializeObjectRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        final List<ActualObjectType> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        addToDefault(
                new FamixRelation(
                        "hasDefiningClass",
                        "This relation is used to state that the subject has or is a specified in the object class or type.",
                        famixClassConcept));
        addToDefault(
                new FamixRelation(
                        "hasDeclaredException",
                        "This relation is used to state that the subject (for example a method) has a specified in the object exception type.",
                        famixClassConcept));
        addToDefault(
                new FamixRelation(
                        "hasCaughtException",
                        "This relation is used to state that the subject (for example a method) catches a specified in the object exception type.",
                        famixClassConcept));
        addToDefault(
                new FamixRelation(
                        "throwsException",
                        "This relation is used to state that the subject (for example a method) throws a specified in the object exception type.",
                        famixClassConcept));
        addToDefault(
                new FamixRelation(
                        "hasSubClass",
                        "This relation is used to state that the subject has a sub class of the specified in the object type.",
                        famixClassConcept));
        addToDefault(
                new FamixRelation(
                        "hasSuperClass",
                        "This relation is used to state that the subject extends a super class of the specified in the object type.",
                        famixClassConcept));

        // Parameter relations
        final List<ActualObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(extractConcept(conceptManager, "Parameter"));
        addToDefault(
                new FamixRelation(
                        "definesParameter",
                        "This relation is used to state that the subject (for example a method) has specified in the object parameters.",
                        parameterConcept));

        // LocalVariable relations
        final List<ActualObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(extractConcept(conceptManager, "LocalVariable"));
        addToDefault(
                new FamixRelation(
                        "definesVariable",
                        "This relation is used to state that the subject (for example a method) defines a specified in the object variable.",
                        localVariableConcept));

        // AnnotationInstance relations
        final List<ActualObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(extractConcept(conceptManager, "AnnotationInstance"));
        addToDefault(
                new FamixRelation(
                        "hasAnnotationInstance",
                        "This relation is used to state that the subject has a specified in the object annotation.",
                        annotationInstanceConcept));

        // AnnotationType relations
        final List<ActualObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(extractConcept(conceptManager, "AnnotationType"));
        addToDefault(
                new FamixRelation(
                        "hasAnnotationType",
                        "This relation is used to state that the subject has a specified in the object annotations type.",
                        annotationTypeConcept));

        // AnnotationTypeAttribute relations
        final List<ActualObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                extractConcept(conceptManager, "AnnotationTypeAttribute"));
        addToDefault(
                new FamixRelation(
                        "hasAnnotationTypeAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object attributes of an annotation type.",
                        annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        final List<ActualObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                extractConcept(conceptManager, "AnnotationInstanceAttribute"));
        addToDefault(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object an attribute-value pair.",
                        annotationInstanceAttributeConcept));

        // Attribute relations
        final List<ActualObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(extractConcept(conceptManager, "Attribute"));
        addToDefault(
                new FamixRelation(
                        "definesAttribute",
                        "This relation is used to state that the subject (for example a class) defines specified in the object attribute.",
                        attributeConcept));

        // Method relations
        final List<ActualObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(extractConcept(conceptManager, "Method"));
        addToDefault(
                new FamixRelation(
                        "definesMethod",
                        "This relation is used to state that the subject (for example a class) defines specified in the object method.",
                        methodConcept));

        // Type relations
        final List<ActualObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(extractConcept(conceptManager, "FamixClass"));
        typeConcepts.add(extractConcept(conceptManager, "Enum"));
        typeConcepts.add(extractConcept(conceptManager, "AnnotationType"));
        addToDefault(
                new FamixRelation(
                        "imports",
                        "This relation is used to state that the subject (for example a class) imports (has dependency to) specified in the object type, class etc.",
                        typeConcepts));

        // Class and Enum relations
        final List<ActualObjectType> classEnumConcepts = new LinkedList<>();
        classEnumConcepts.add(extractConcept(conceptManager, "FamixClass"));
        classEnumConcepts.add(extractConcept(conceptManager, "Enum"));
        addToDefault(
                new FamixRelation(
                        "definesNestedType",
                        "This relation is used to state that the subject (for example a class) defines a nested specified in the object type.",
                        classEnumConcepts));

        // Type + NameSpace relations
        final List<ActualObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(extractConcept(conceptManager, "Namespace"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "FamixClass"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "Enum"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "AnnotationType"));
        addToDefault(
                new FamixRelation(
                        "namespaceContains",
                        "This relation is used to state that the subject (namespace) contains a specified in the object type, class etc.",
                        namespaceContainsConcepts));

        // Type + Primitive relations
        final List<ActualObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(extractConcept(conceptManager, "FamixClass"));
        typesAndprimitives.add(extractConcept(conceptManager, "Enum"));
        typesAndprimitives.add(extractConcept(conceptManager, "AnnotationType"));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        addToDefault(
                new FamixRelation(
                        "hasDeclaredType",
                        "This relation is used to state that the subject (for example attribute) has a specified in the object type.",
                        typesAndprimitives));
    }

    private void initializeConformanceRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // Relations "hasProofText" and "hasViolationText" are excluded here as they are currently
        // unused
        // TODO: "hasRuleID" and "hasCheckingDate" are also excluded as their ObjectType is unclear
        addToDefault(
                new ConformanceRelation(
                        "hasRuleRepresentation",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object representation (in most cases string representation).",
                        new ArrayList<>(Arrays.asList(new StringValue("")))));
        addToDefault(
                new ConformanceRelation(
                        "hasRuleType",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object rule type.",
                        new ArrayList<>(Arrays.asList(new StringValue("")))));
        addToDefault(
                new ConformanceRelation(
                        "hasNotInferredStatement",
                        "This relation is used to state that the subject hasn't / doesn't correspond to the specified in the object statements.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "This relation is used to state that the subject has / corresponds to the specified in the object statements.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "AssertedStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "hasSubject",
                        "This relation is used to state that the subject (for example statement) has a specified subject.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "hasPredicate",
                        "This relation is used to state that the subject (for example statement) has a specified predicate.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "hasObject",
                        "This relation is used to state that the subject (for example statement) has a specified object.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "proofs",
                        "This relation is used to state that the subject (proof) verifies a specified in the object violation.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        addToDefault(
                new ConformanceRelation(
                        "hasDetected",
                        "This relation is used to state that the subject (conformance check) has detected a specified in the object violation.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        addToDefault(
                new ConformanceRelation(
                        "hasViolation",
                        "This relation is used to state that the subject has a specified in the object violation.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        addToDefault(
                new ConformanceRelation(
                        "violates",
                        "This relation is used to state that the subject violates a specified in the object rule.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureRule")))));
        addToDefault(
                new ConformanceRelation(
                        "validates",
                        "This relation is used to state that the subject validates a specified in the object rule or conformance check.",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureRule")))));
    }

    private Concept extractConcept(final ConceptManager conceptManager, final String conceptName)
            throws ConceptDoesNotExistException {
        return conceptManager
                .getConceptByName(conceptName)
                .orElseThrow(() -> new ConceptDoesNotExistException(conceptName));
    }

    public List<Relation> getInputRelations() {
        return relations.stream()
                .filter(Predicate.not(ConformanceRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<Relation> getOutputRelations() {
        return relations.stream()
                .filter(Predicate.not(JenaBuiltinRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<CustomRelation> getCustomRelations() {
        return relations.stream()
                .filter(CustomRelation.class::isInstance)
                .map(CustomRelation.class::cast)
                .collect(Collectors.toList());
    }
}
