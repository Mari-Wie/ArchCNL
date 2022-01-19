package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class RelationManager {

    private List<Relation> relations;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public RelationManager(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        relations = new LinkedList<>();
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
        propertyChangeSupport.firePropertyChange("newRelation", null, relation);
    }

    public void relationHasBeenUpdated(final Relation relation) {
        propertyChangeSupport.firePropertyChange("relationUpdated", null, relation);
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

    private void initializeSpecialRelations() {
        final List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(
                new JenaBuiltinRelation(
                        "matches",
                        "regex",
                        "This relation is used to state that a literal given as the subject matches against the regular expression pattern stated in the object. The syntax of the regular expression pattern is according to java.util.regex.",
                        stringConcept));
    }

    private void initializeTypeRelation() {
        relations.add(
                new TypeRelation(
                        "is-of-type",
                        "type",
                        "This relation is used to state that the subject is an instance of the class stated in the object."));
    }

    private void initializeStringRelations() {
        final List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(
                new FamixRelation(
                        "hasModifier",
                        "This relation is used to state that the subject has a modifier with the name stated in the object. Examples for modifiers are access modifiers (e.g. public) and mutability modifiers (e.g. final).",
                        stringConcept));
        relations.add(
                new FamixRelation(
                        "hasName",
                        "This relation is used to state that the subject has the name which is stated in the object.",
                        stringConcept));
        relations.add(
                new FamixRelation(
                        "hasSignature",
                        "This relation is used to state that a method has the signature which is stated in the object.",
                        stringConcept));
        relations.add(
                new FamixRelation(
                        "hasValue",
                        "This relation is used to state that an AnnotationInstanceAttribute (which is an attribute-value pair) has the value which is stated in the object.",
                        stringConcept));
        relations.add(
                new FamixRelation(
                        "hasFullQualifiedName",
                        "This relation is used to state that an entity has the name which is stated in the object.",
                        stringConcept));
    }

    private void initializeBoolRelations() {
        final List<ActualObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        relations.add(new FamixRelation("isConstructor", "", boolConcept));
        relations.add(new FamixRelation("isExternal", "", boolConcept));
        relations.add(new FamixRelation("isInterface", "", boolConcept));
    }

    private void initializeObjectRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        final List<ActualObjectType> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        relations.add(new FamixRelation("hasDefiningClass", "", famixClassConcept));
        relations.add(new FamixRelation("hasDeclaredException", "", famixClassConcept));
        relations.add(new FamixRelation("hasCaughtException", "", famixClassConcept));
        relations.add(new FamixRelation("throwsException", "", famixClassConcept));
        relations.add(new FamixRelation("hasSubClass", "", famixClassConcept));
        relations.add(new FamixRelation("hasSuperClass", "", famixClassConcept));

        // Parameter relations
        final List<ActualObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(extractConcept(conceptManager, "Parameter"));
        relations.add(new FamixRelation("definesParameter", "", parameterConcept));

        // LocalVariable relations
        final List<ActualObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(extractConcept(conceptManager, "LocalVariable"));
        relations.add(new FamixRelation("definesVariable", "", localVariableConcept));

        // AnnotationInstance relations
        final List<ActualObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(extractConcept(conceptManager, "AnnotationInstance"));
        relations.add(new FamixRelation("hasAnnotationInstance", "", annotationInstanceConcept));

        // AnnotationType relations
        final List<ActualObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(extractConcept(conceptManager, "AnnotationType"));
        relations.add(new FamixRelation("hasAnnotationType", "", annotationTypeConcept));

        // AnnotationTypeAttribute relations
        final List<ActualObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                extractConcept(conceptManager, "AnnotationTypeAttribute"));
        relations.add(
                new FamixRelation(
                        "hasAnnotationTypeAttribute", "", annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        final List<ActualObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                extractConcept(conceptManager, "AnnotationInstanceAttribute"));
        relations.add(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute", "", annotationInstanceAttributeConcept));

        // Attribute relations
        final List<ActualObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(extractConcept(conceptManager, "Attribute"));
        relations.add(new FamixRelation("definesAttribute", "", attributeConcept));

        // Method relations
        final List<ActualObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(extractConcept(conceptManager, "Method"));
        relations.add(new FamixRelation("definesMethod", "", methodConcept));

        // Type relations
        final List<ActualObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(extractConcept(conceptManager, "FamixClass"));
        typeConcepts.add(extractConcept(conceptManager, "Enum"));
        typeConcepts.add(extractConcept(conceptManager, "AnnotationType"));
        relations.add(new FamixRelation("imports", "", typeConcepts));

        // Class and Enum relations
        final List<ActualObjectType> classEnumConcepts = new LinkedList<>();
        classEnumConcepts.add(extractConcept(conceptManager, "FamixClass"));
        classEnumConcepts.add(extractConcept(conceptManager, "Enum"));
        relations.add(new FamixRelation("definesNestedType", "", classEnumConcepts));

        // Type + NameSpace relations
        final List<ActualObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(extractConcept(conceptManager, "Namespace"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "FamixClass"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "Enum"));
        namespaceContainsConcepts.add(extractConcept(conceptManager, "AnnotationType"));
        relations.add(new FamixRelation("namespaceContains", "", namespaceContainsConcepts));

        // Type + Primitive relations
        final List<ActualObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(extractConcept(conceptManager, "FamixClass"));
        typesAndprimitives.add(extractConcept(conceptManager, "Enum"));
        typesAndprimitives.add(extractConcept(conceptManager, "AnnotationType"));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        relations.add(new FamixRelation("hasDeclaredType", "", typesAndprimitives));
    }

    private void initializeConformanceRelations(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // Relations "hasProofText" and "hasViolationText" are excluded here as they are currently
        // unused
        // TODO: "hasRuleID" and "hasCheckingDate" are also excluded as their ObjectType is unclear
        relations.add(
                new ConformanceRelation(
                        "hasRuleRepresentation",
                        "",
                        new ArrayList<>(Arrays.asList(new StringValue("")))));
        relations.add(
                new ConformanceRelation(
                        "hasRuleType", "", new ArrayList<>(Arrays.asList(new StringValue("")))));
        relations.add(
                new ConformanceRelation(
                        "hasNotInferredStatement",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasSubject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasPredicate",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasObject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "NotInferredStatement"),
                                        extractConcept(conceptManager, "AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "proofs",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "hasDetected",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "hasViolation",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "violates",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        extractConcept(conceptManager, "ArchitectureRule")))));
        relations.add(
                new ConformanceRelation(
                        "validates",
                        "",
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

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
