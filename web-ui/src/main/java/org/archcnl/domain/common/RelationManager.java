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
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
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
                Relation existingRelation = getRelationByName(relation.getName());
                if (existingRelation instanceof CustomRelation) {
                    CustomRelation existingCustomRelation = (CustomRelation) existingRelation;
                    Optional<RelationMapping> existingMapping = existingCustomRelation.getMapping();
                    Optional<RelationMapping> newMapping = relation.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomRelation.setMapping(newMapping.get());
                    }
                }
            }
        } catch (RelationAlreadyExistsException | RelationDoesNotExistException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of of mapping \""
                            + relation.getName()
                            + "\" failed unexpectedly.");
        }
    }

    public Relation getRelationByName(String name) throws RelationDoesNotExistException {
        return relations.stream()
                .filter(relation -> name.equals(relation.getName()))
                .findAny()
                .orElseThrow(() -> new RelationDoesNotExistException(name));
    }

    public Relation getRelationByRealName(String realName) throws RelationDoesNotExistException {
        for (Relation relation : relations) {
            if (relation instanceof JenaBuiltinRelation) {
                JenaBuiltinRelation specialRelation = (JenaBuiltinRelation) relation;
                if (realName.equals(specialRelation.getRealName())) {
                    return specialRelation;
                }
            } else if (relation instanceof TypeRelation) {
                TypeRelation typeRelation = (TypeRelation) relation;
                if (realName.equals(typeRelation.getRealName())) {
                    return typeRelation;
                }
            }
        }
        throw new RelationDoesNotExistException(realName);
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
        relations.add(new DefaultRelation("hasModifier", "", stringConcept));
        relations.add(new DefaultRelation("hasName", "", stringConcept));
        relations.add(new DefaultRelation("hasSignature", "", stringConcept));
        relations.add(new DefaultRelation("hasValue", "", stringConcept));
        relations.add(new DefaultRelation("hasFullQualifiedName", "", stringConcept));
    }

    private void initializeBoolRelations() {
        List<ActualObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        relations.add(new DefaultRelation("isConstructor", "", boolConcept));
        relations.add(new DefaultRelation("isExternal", "", boolConcept));
        relations.add(new DefaultRelation("isInterface", "", boolConcept));
    }

    private void initializeObjectRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        List<ActualObjectType> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(conceptManager.getConceptByName("FamixClass"));
        relations.add(new DefaultRelation("hasDefiningClass", "", famixClassConcept));
        relations.add(new DefaultRelation("hasDeclaredException", "", famixClassConcept));
        relations.add(new DefaultRelation("hasCaughtException", "", famixClassConcept));
        relations.add(new DefaultRelation("throwsException", "", famixClassConcept));
        relations.add(new DefaultRelation("hasSubClass", "", famixClassConcept));
        relations.add(new DefaultRelation("hasSuperClass", "", famixClassConcept));

        // Parameter relations
        List<ActualObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(conceptManager.getConceptByName("Parameter"));
        relations.add(new DefaultRelation("definesParameter", "", parameterConcept));

        // LocalVariable relations
        List<ActualObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(conceptManager.getConceptByName("LocalVariable"));
        relations.add(new DefaultRelation("definesVariable", "", localVariableConcept));

        // AnnotationInstance relations
        List<ActualObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(conceptManager.getConceptByName("AnnotationInstance"));
        relations.add(new DefaultRelation("hasAnnotationInstance", "", annotationInstanceConcept));

        // AnnotationType relations
        List<ActualObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("hasAnnotationType", "", annotationTypeConcept));

        // AnnotationTypeAttribute relations
        List<ActualObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationTypeAttribute"));
        relations.add(
                new DefaultRelation(
                        "hasAnnotationTypeAttribute", "", annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        List<ActualObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationInstanceAttribute"));
        relations.add(
                new DefaultRelation(
                        "hasAnnotationInstanceAttribute", "", annotationInstanceAttributeConcept));

        // Attribute relations
        List<ActualObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(conceptManager.getConceptByName("Attribute"));
        relations.add(new DefaultRelation("definesAttribute", "", attributeConcept));

        // Method relations
        List<ActualObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(conceptManager.getConceptByName("Method"));
        relations.add(new DefaultRelation("definesMethod", "", methodConcept));

        // Type relations
        List<ActualObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(conceptManager.getConceptByName("FamixClass"));
        typeConcepts.add(conceptManager.getConceptByName("Enum"));
        typeConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("imports", "", typeConcepts));

        // Class and Enum relations
        List<ActualObjectType> classEnumConcepts = new LinkedList<>();
        classEnumConcepts.add(conceptManager.getConceptByName("FamixClass"));
        classEnumConcepts.add(conceptManager.getConceptByName("Enum"));
        relations.add(new DefaultRelation("definesNestedType", "", classEnumConcepts));

        // Type + NameSpace relations
        List<ActualObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Namespace"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("FamixClass"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Enum"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("namespaceContains", "", namespaceContainsConcepts));

        // Type + Primitive relations
        List<ActualObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(conceptManager.getConceptByName("FamixClass"));
        typesAndprimitives.add(conceptManager.getConceptByName("Enum"));
        typesAndprimitives.add(conceptManager.getConceptByName("AnnotationType"));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        relations.add(new DefaultRelation("hasDeclaredType", "", typesAndprimitives));
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
                                        conceptManager.getConceptByName("NotInferredStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasAssertedStatement",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasSubject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("NotInferredStatement"),
                                        conceptManager.getConceptByName("AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasPredicate",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("NotInferredStatement"),
                                        conceptManager.getConceptByName("AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "hasObject",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("NotInferredStatement"),
                                        conceptManager.getConceptByName("AssertedStatement")))));
        relations.add(
                new ConformanceRelation(
                        "proofs",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName(
                                                "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "hasDetected",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName(
                                                "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "hasViolation",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName(
                                                "ArchitectureViolation")))));
        relations.add(
                new ConformanceRelation(
                        "violates",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("ArchitectureRule")))));
        relations.add(
                new ConformanceRelation(
                        "validates",
                        "",
                        new ArrayList<>(
                                Arrays.asList(
                                        conceptManager.getConceptByName("ArchitectureRule")))));
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
