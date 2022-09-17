package org.archcnl.domain.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class RelationManager extends HierarchyManager<Relation> {

    private Map<String, Relation> relations;
    private ConceptManager conceptManager;

    public RelationManager(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        this.relations = new HashMap<>();
        this.conceptManager = conceptManager;

        addHierarchyRoot("Default Relations");
        addHierarchyRoot("Custom Relations");
        initializeTypeRelation();
        initializeBoolRelations(conceptManager);
        initializeStringRelations(conceptManager);
        initializeSpecialRelations();
        initializeObjectRelations(conceptManager);
        initializeConformanceRelations(conceptManager);
    }

    public void addRelation(final Relation relation) throws RelationAlreadyExistsException {
        if (doesRelationExist(relation.getName())) {
            throw new RelationAlreadyExistsException(relation.getName());
        }
        relations.put(relation.getName(), relation);
    }

    public void addToParent(Relation relation, String parentName)
            throws RelationAlreadyExistsException {
        addRelation(relation);
        Optional<HierarchyNode<Relation>> parent =
                hierarchyRoots.stream().filter(node -> parentName.equals(node.getName())).findAny();
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
            if (!doesRelationExist(relation.getName())) {
                addToParent(relation, "Custom Relations");
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
                        existingCustomRelation.setMapping(newMapping.get(), conceptManager);
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
        return Optional.ofNullable(relations.get(name));
    }

    public Optional<Relation> getRelationByRealName(final String realName) {
        for (final Relation relation : relations.values()) {
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

    public boolean doesRelationExist(final String name) {
        return relations.containsKey(name);
    }

    public void updateName(String oldName, String newName) throws RelationAlreadyExistsException {
        if (!relations.containsKey(oldName)) {
            return;
        }
        if (doesRelationExist(newName) && !oldName.equals(newName)) {
            throw new RelationAlreadyExistsException(newName);
        }
        Relation relation = relations.remove(oldName);
        relation.changeName(newName);
        relations.put(newName, relation);
    }

    public void removeRelation(Relation relation) {
        if (relation != null) {
            relations.remove(relation.getName());
        }
        removeFromHierarchy(new HierarchyNode<>(relation));
    }

    public void removeNode(HierarchyNode<Relation> node) {
        if (node.hasEntry()) {
            relations.remove(node.getName());
        }
        removeFromHierarchy(node);
    }

    private void initializeSpecialRelations() {
        addToDefault(JenaBuiltinRelation.getRegexRelation());
    }

    private void initializeTypeRelation() {
        addToDefault(TypeRelation.getTyperelation());
    }

    private void initializeStringRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        final Set<ActualObjectType> stringValue = Collections.singleton(new StringValue(""));

        addToDefault(
                new FamixRelation(
                        "hasModifier",
                        "This relation is used to state that the subject has a modifier with the name stated in the object. Examples for modifiers are access modifiers (e.g. public) and mutability modifiers (e.g. final).",
                        getHasModifierSubjects(),
                        stringValue));
        addToDefault(
                new FamixRelation(
                        "hasName",
                        "This relation is used to state that the subject has the name which is stated in the object.",
                        getNamesEntities(),
                        stringValue));
        addToDefault(
                new FamixRelation(
                        "isLocatedAt",
                        "This relation is used to state that the subject is located at the locations stated in the object.",
                        getIsLocatedAtSubjects(),
                        stringValue));
        addToDefault(
                new FamixRelation(
                        "hasSignature",
                        "This relation is used to state that a method has the signature which is stated in the object.",
                        Collections.singleton(getConcept("Method")),
                        stringValue));
        addToDefault(
                new FamixRelation(
                        "hasFullQualifiedName",
                        "This relation is used to state that an entity has the name which is stated in the object.",
                        getFamixTypes(),
                        stringValue));
        addToDefault(
                new FamixRelation(
                        "hasValue",
                        "This relation is used to state that an AnnotationInstanceAttribute (which is an attribute-value pair) has the value which is stated in the object.",
                        Collections.singleton(getConcept("AnnotationInstanceAttribute")),
                        stringValue));
    }

    private void initializeBoolRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        final Set<ActualObjectType> boolObject = Collections.singleton(new BooleanValue(false));
        addToDefault(
                new FamixRelation(
                        "isConstructor",
                        "This relation is used to state that the subject is or isn't a constructor (based on a value in the object). A constructor is special method that is called when an object is instantiated.",
                        Collections.singleton(getConcept("Method")),
                        boolObject));
        addToDefault(
                new FamixRelation(
                        "isExternal",
                        "This relation is used to state that the subject is or isn't an external type (based on a value in the object).",
                        getFamixTypes(),
                        boolObject));
        addToDefault(
                new FamixRelation(
                        "isInterface",
                        "This relation is used to state that the subject is or isn't an interface (based on a value in the object). An interface is an abstract type that is used to specify a behavior that classes must implement.",
                        Collections.singleton(getConcept("FamixClass")),
                        boolObject));
    }

    private void initializeObjectRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        Set<ActualObjectType> famixClass = Collections.singleton(getConcept("FamixClass"));
        Set<ActualObjectType> method = Collections.singleton(getConcept("Method"));
        Set<ActualObjectType> inheritance = Collections.singleton(getConcept("Inheritance"));
        addToDefault(
                new FamixRelation(
                        "hasDeclaredException",
                        "This relation is used to state that the subject (for example a method) has a specified in the object exception type.",
                        method,
                        famixClass));
        addToDefault(
                new FamixRelation(
                        "hasCaughtException",
                        "This relation is used to state that the subject (for example a method) catches a specified in the object exception type.",
                        method,
                        famixClass));
        addToDefault(
                new FamixRelation(
                        "throwsException",
                        "This relation is used to state that the subject (for example a method) throws a specified in the object exception type.",
                        method,
                        famixClass));
        addToDefault(
                new FamixRelation(
                        "hasSubClass",
                        "This relation is used to state that the subject has a sub class of the specified in the object type.",
                        inheritance,
                        famixClass));
        addToDefault(
                new FamixRelation(
                        "hasSuperClass",
                        "This relation is used to state that the subject extends a super class of the specified in the object type.",
                        inheritance,
                        famixClass));

        // Parameter relations
        Set<ActualObjectType> parameter = Collections.singleton(getConcept("Parameter"));
        addToDefault(
                new FamixRelation(
                        "definesParameter",
                        "This relation is used to state that the subject (for example a method) has specified in the object parameters.",
                        method,
                        parameter));

        // LocalVariable relations
        Set<ActualObjectType> localVariable = Collections.singleton(getConcept("LocalVariable"));
        addToDefault(
                new FamixRelation(
                        "definesVariable",
                        "This relation is used to state that the subject (for example a method) defines a specified in the object variable.",
                        method,
                        localVariable));

        // AnnotationInstance relations
        Set<ActualObjectType> annotationInstance =
                Collections.singleton(getConcept("AnnotationInstance"));
        addToDefault(
                new FamixRelation(
                        "hasAnnotationInstance",
                        "This relation is used to state that the subject has a specified in the object annotation.",
                        getHasAnnotationInstanceSubjects(),
                        annotationInstance));

        // AnnotationType relations
        addToDefault(
                new FamixRelation(
                        "hasAnnotationType",
                        "This relation is used to state that the subject has a specified in the object annotations type.",
                        annotationInstance,
                        Collections.singleton(getConcept("AnnotationType"))));

        // AnnotationTypeAttribute relations
        addToDefault(
                new FamixRelation(
                        "hasAnnotationTypeAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object attributes of an annotation type.",
                        getHasAnnotationTypeAttributeSubjects(),
                        Collections.singleton(getConcept("AnnotationTypeAttribute"))));

        // AnnotationInstanceAttribute relations
        addToDefault(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute",
                        "This relation is used to state that the subject has in the annotation specified in the object an attribute-value pair.",
                        annotationInstance,
                        Collections.singleton(getConcept("AnnotationInstanceAttribute"))));

        // Attribute relations
        Set<ActualObjectType> attribute = Collections.singleton(getConcept("Attribute"));
        addToDefault(
                new FamixRelation(
                        "definesAttribute",
                        "This relation is used to state that the subject (for example a class) defines specified in the object attribute.",
                        getFamixClassAndEnum(),
                        attribute));

        // Method relations
        addToDefault(
                new FamixRelation(
                        "definesMethod",
                        "This relation is used to state that the subject (for example a class) defines specified in the object method.",
                        getFamixClassAndEnum(),
                        method));

        // Type relations
        addToDefault(
                new FamixRelation(
                        "imports",
                        "This relation is used to state that the subject (for example a class) imports (has dependency to) specified in the object type, class etc.",
                        getFamixTypes(),
                        getFamixTypes()));

        // Class and Enum relations
        addToDefault(
                new FamixRelation(
                        "definesNestedType",
                        "This relation is used to state that the subject (for example a class) defines a nested specified in the object type.",
                        getFamixClassAndEnum(),
                        getFamixTypes()));

        // Type + NameSpace relations
        addToDefault(
                new FamixRelation(
                        "namespaceContains",
                        "This relation is used to state that the subject (namespace) contains a specified in the object type, class etc.",
                        Collections.singleton(getConcept("Namespace")),
                        getNamespaceContainsObjects()));

        // Type + Primitive relations
        addToDefault(
                new FamixRelation(
                        "hasDeclaredType",
                        "This relation is used to state that the subject (for example attribute) has a specified in the object type.",
                        getHasDeclaredTypeSubjects(),
                        getHasDeclaredTypeObjects()));
    }

    private void initializeConformanceRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // Relations "hasProofText" and "hasViolationText" are excluded here as they are currently
        // unused
        // TODO: "hasRuleID" and "hasCheckingDate" are also excluded as their ObjectType is unclear
        final Set<ActualObjectType> architectureRule =
                new HashSet<>(Arrays.asList(getConcept("ArchitectureRule")));
        addToDefault(
                new ConformanceRelation(
                        "hasRuleRepresentation",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object representation (in most cases string representation).",
                        architectureRule,
                        new HashSet<>(Arrays.asList(new StringValue("")))));
        addToDefault(
                new ConformanceRelation(
                        "hasRuleType",
                        "This relation is used to state that the subject (for example architecture rule) has a specified in the object rule type.",
                        architectureRule,
                        new HashSet<>(Arrays.asList(new StringValue("")))));
        final Set<ActualObjectType> proof = new HashSet<>(Arrays.asList(getConcept("Proof")));
        addToDefault(
                new ConformanceRelation(
                        "hasNotInferredStatement",
                        "This relation is used to state that the subject hasn't / doesn't correspond to the specified in the object statements.",
                        proof,
                        new HashSet<>(Arrays.asList(getConcept("NotInferredStatement")))));
        addToDefault(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "This relation is used to state that the subject has / corresponds to the specified in the object statements.",
                        proof,
                        new HashSet<>(Arrays.asList(getConcept("AssertedStatement")))));
        final Set<ActualObjectType> statements = new HashSet<>();
        statements.add(getConcept("NotInferredStatement"));
        statements.add(getConcept("AssertedStatement"));
        addToDefault(
                new ConformanceRelation(
                        "hasSubject",
                        "This relation is used to state that the subject (for example statement) has a specified subject.",
                        statements,
                        new HashSet<>()));
        addToDefault(
                new ConformanceRelation(
                        "hasPredicate",
                        "This relation is used to state that the subject (for example statement) has a specified predicate.",
                        statements,
                        new HashSet<>()));
        addToDefault(
                new ConformanceRelation(
                        "hasObject",
                        "This relation is used to state that the subject (for example statement) has a specified object.",
                        statements,
                        new HashSet<>()));
        final Set<ActualObjectType> architecturViolation =
                new HashSet<>(Arrays.asList(getConcept("ArchitectureViolation")));
        addToDefault(
                new ConformanceRelation(
                        "proofs",
                        "This relation is used to state that the subject (proof) verifies a specified in the object violation.",
                        proof,
                        architecturViolation));
        final Set<ActualObjectType> conformanceCheck =
                new HashSet<>(Arrays.asList(getConcept("ConformanceCheck")));
        addToDefault(
                new ConformanceRelation(
                        "hasDetected",
                        "This relation is used to state that the subject (conformance check) has detected a specified in the object violation.",
                        conformanceCheck,
                        architecturViolation));
        addToDefault(
                new ConformanceRelation(
                        "hasViolation",
                        "This relation is used to state that the subject has a specified in the object violation.",
                        architectureRule,
                        architecturViolation));
        addToDefault(
                new ConformanceRelation(
                        "violates",
                        "This relation is used to state that the subject violates a specified in the object rule.",
                        architecturViolation,
                        architectureRule));
        addToDefault(
                new ConformanceRelation(
                        "validates",
                        "This relation is used to state that the subject validates a specified in the object rule or conformance check.",
                        conformanceCheck,
                        architectureRule));
    }

    private Concept getConcept(final String conceptName) throws ConceptDoesNotExistException {
        return conceptManager
                .getConceptByName(conceptName)
                .orElseThrow(() -> new ConceptDoesNotExistException(conceptName));
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
        isLocatedAtSubjects.add(getConcept("PrimitiveType"));
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

    public List<Relation> getInputRelations() {
        return relations.values().stream()
                .filter(Predicate.not(ConformanceRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<Relation> getOutputRelations() {
        return relations.values().stream()
                .filter(Predicate.not(JenaBuiltinRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<CustomRelation> getCustomRelations() {
        return relations.values().stream()
                .filter(CustomRelation.class::isInstance)
                .map(CustomRelation.class::cast)
                .collect(Collectors.toList());
    }
}
