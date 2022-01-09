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

    public RelationManager(ConceptManager conceptManager) throws ConceptDoesNotExistException {
        relations = new LinkedList<>();
        initializeTypeRelation();
        initializeBoolRelations();
        initializeStringRelations();
        initializeSpecialRelations();
        initializeObjectRelations(conceptManager);
        initializeConformanceRelations(conceptManager);
    }

    public void addRelation(Relation relation) throws RelationAlreadyExistsException {
        if (!doesRelationExist(relation)) {
            relations.add(relation);
            propertyChangeSupport.firePropertyChange("newRelation", null, relation);
        } else {
            throw new RelationAlreadyExistsException(relation.getName());
        }
    }

    public void relationHasBeenUpdated(Relation relation) {
        propertyChangeSupport.firePropertyChange("relationUpdated", null, relation);
    }

    public void addOrAppend(CustomRelation relation) throws UnrelatedMappingException {
        try {
            if (!doesRelationExist(relation)) {
                addRelation(relation);
            } else {
                Optional<Relation> existingRelationOpt = getRelationByName(relation.getName());
                if (existingRelationOpt.isPresent()
                        && existingRelationOpt.get() instanceof CustomRelation) {
                    CustomRelation existingCustomRelation =
                            (CustomRelation) existingRelationOpt.get();
                    Optional<RelationMapping> existingMapping = existingCustomRelation.getMapping();
                    Optional<RelationMapping> newMapping = relation.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomRelation.setMapping(newMapping.get());
                    }
                }
            }
        } catch (RelationAlreadyExistsException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of of mapping \""
                            + relation.getName()
                            + "\" failed unexpectedly.");
        }
    }

    public Optional<Relation> getRelationByName(String name) {
        return relations.stream().filter(relation -> name.equals(relation.getName())).findAny();
    }

    public Optional<Relation> getRelationByRealName(String realName) {
        for (Relation relation : relations) {
            if (relation instanceof JenaBuiltinRelation) {
                JenaBuiltinRelation specialRelation = (JenaBuiltinRelation) relation;
                if (realName.equals(specialRelation.getRealName())) {
                    return Optional.of(specialRelation);
                }
            } else if (relation instanceof TypeRelation) {
                TypeRelation typeRelation = (TypeRelation) relation;
                if (realName.equals(typeRelation.getRealName())) {
                    return Optional.of(typeRelation);
                }
            }
        }
        return Optional.empty();
    }

    public boolean doesRelationExist(Relation relation) {
        return relations.stream()
                .anyMatch(
                        existingRelation -> relation.getName().equals(existingRelation.getName()));
    }

    private void initializeSpecialRelations() {
        List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new JenaBuiltinRelation("matches", "regex", "", stringConcept));
    }

    private void initializeTypeRelation() {
        relations.add(new TypeRelation("is-of-type", "type", ""));
    }

    private void initializeStringRelations() {
        List<ActualObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new FamixRelation("hasModifier", "", stringConcept));
        relations.add(new FamixRelation("hasName", "", stringConcept));
        relations.add(new FamixRelation("hasSignature", "", stringConcept));
        relations.add(new FamixRelation("hasValue", "", stringConcept));
        relations.add(new FamixRelation("hasFullQualifiedName", "", stringConcept));
    }

    private void initializeBoolRelations() {
        List<ActualObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        relations.add(new FamixRelation("isConstructor", "", boolConcept));
        relations.add(new FamixRelation("isExternal", "", boolConcept));
        relations.add(new FamixRelation("isInterface", "", boolConcept));
    }

    private void initializeObjectRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        List<ActualObjectType> famixClassConcept = new LinkedList<>();
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
        List<ActualObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(
                conceptManager
                        .getConceptByName("Parameter")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Parameter")));
        relations.add(new FamixRelation("definesParameter", "", parameterConcept));

        // LocalVariable relations
        List<ActualObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(
                conceptManager
                        .getConceptByName("LocalVariable")
                        .orElseThrow(() -> new ConceptDoesNotExistException("LocalVariable")));
        relations.add(new FamixRelation("definesVariable", "", localVariableConcept));

        // AnnotationInstance relations
        List<ActualObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(
                conceptManager
                        .getConceptByName("AnnotationInstance")
                        .orElseThrow(() -> new ConceptDoesNotExistException("AnnotationInstance")));
        relations.add(new FamixRelation("hasAnnotationInstance", "", annotationInstanceConcept));

        // AnnotationType relations
        List<ActualObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(
                conceptManager
                        .getConceptByName("AnnotationType")
                        .orElseThrow(() -> new ConceptDoesNotExistException("AnnotationType")));
        relations.add(new FamixRelation("hasAnnotationType", "", annotationTypeConcept));

        // AnnotationTypeAttribute relations
        List<ActualObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                conceptManager
                        .getConceptByName("AnnotationTypeAttribute")
                        .orElseThrow(
                                () -> new ConceptDoesNotExistException("AnnotationTypeAttribute")));
        relations.add(
                new FamixRelation(
                        "hasAnnotationTypeAttribute", "", annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        List<ActualObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                conceptManager
                        .getConceptByName("AnnotationInstanceAttribute")
                        .orElseThrow(
                                () ->
                                        new ConceptDoesNotExistException(
                                                "AnnotationInstanceAttribute")));
        relations.add(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute", "", annotationInstanceAttributeConcept));

        // Attribute relations
        List<ActualObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(
                conceptManager
                        .getConceptByName("Attribute")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Attribute")));
        relations.add(new FamixRelation("definesAttribute", "", attributeConcept));

        // Method relations
        List<ActualObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(
                conceptManager
                        .getConceptByName("Method")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Method")));
        relations.add(new FamixRelation("definesMethod", "", methodConcept));

        // Type relations
        List<ActualObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        typeConcepts.add(
                conceptManager
                        .getConceptByName("Enum")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Enum")));
        typeConcepts.add(
                conceptManager
                        .getConceptByName("AnnotationType")
                        .orElseThrow(() -> new ConceptDoesNotExistException("AnnotationType")));
        relations.add(new FamixRelation("imports", "", typeConcepts));

        // Class and Enum relations
        List<ActualObjectType> classEnumConcepts = new LinkedList<>();
        classEnumConcepts.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        classEnumConcepts.add(
                conceptManager
                        .getConceptByName("Enum")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Enum")));
        relations.add(new FamixRelation("definesNestedType", "", classEnumConcepts));

        // Type + NameSpace relations
        List<ActualObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(
                conceptManager
                        .getConceptByName("Namespace")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Namespace")));
        namespaceContainsConcepts.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        namespaceContainsConcepts.add(
                conceptManager
                        .getConceptByName("Enum")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Enum")));
        namespaceContainsConcepts.add(
                conceptManager
                        .getConceptByName("AnnotationType")
                        .orElseThrow(() -> new ConceptDoesNotExistException("AnnotationType")));
        relations.add(new FamixRelation("namespaceContains", "", namespaceContainsConcepts));

        // Type + Primitive relations
        List<ActualObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(
                conceptManager
                        .getConceptByName("FamixClass")
                        .orElseThrow(() -> new ConceptDoesNotExistException("FamixClass")));
        typesAndprimitives.add(
                conceptManager
                        .getConceptByName("Enum")
                        .orElseThrow(() -> new ConceptDoesNotExistException("Enum")));
        typesAndprimitives.add(
                conceptManager
                        .getConceptByName("AnnotationType")
                        .orElseThrow(() -> new ConceptDoesNotExistException("AnnotationType")));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        relations.add(new FamixRelation("hasDeclaredType", "", typesAndprimitives));
    }

    private void initializeConformanceRelations(ConceptManager conceptManager)
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
                                        conceptManager
                                                .getConceptByName("NotInferredStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "NotInferredStatement"))))));
        relations.add(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("AssertedStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "AssertedStatement"))))));
        relations.add(
                new ConformanceRelation(
                        "hasSubject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("NotInferredStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "NotInferredStatement")),
                                        conceptManager
                                                .getConceptByName("AssertedStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "AssertedStatement"))))));
        relations.add(
                new ConformanceRelation(
                        "hasPredicate",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("NotInferredStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "NotInferredStatement")),
                                        conceptManager
                                                .getConceptByName("AssertedStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "AssertedStatement"))))));
        relations.add(
                new ConformanceRelation(
                        "hasObject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("NotInferredStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "NotInferredStatement")),
                                        conceptManager
                                                .getConceptByName("AssertedStatement")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "AssertedStatement"))))));
        relations.add(
                new ConformanceRelation(
                        "proofs",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("ArchitectureViolation")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "ArchitectureViolation"))))));
        relations.add(
                new ConformanceRelation(
                        "hasDetected",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("ArchitectureViolation")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "ArchitectureViolation"))))));
        relations.add(
                new ConformanceRelation(
                        "hasViolation",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("ArchitectureViolation")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "ArchitectureViolation"))))));
        relations.add(
                new ConformanceRelation(
                        "violates",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("ArchitectureRule")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "ArchitectureRule"))))));
        relations.add(
                new ConformanceRelation(
                        "validates",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager
                                                .getConceptByName("ArchitectureRule")
                                                .orElseThrow(
                                                        () ->
                                                                new ConceptDoesNotExistException(
                                                                        "ArchitectureRule"))))));
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
