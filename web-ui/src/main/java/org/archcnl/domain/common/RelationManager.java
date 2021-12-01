package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
        List<ObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new JenaBuiltinRelation("matches", "regex", stringConcept));
    }

    private void initializeTypeRelation() {
        relations.add(new TypeRelation("is-of-type", "type"));
    }

    private void initializeStringRelations() {
        List<ObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new FamixRelation("hasModifier", stringConcept));
        relations.add(new FamixRelation("hasName", stringConcept));
        relations.add(new FamixRelation("hasSignature", stringConcept));
        relations.add(new FamixRelation("hasValue", stringConcept));
        relations.add(new FamixRelation("hasFullQualifiedName", stringConcept));
    }

    private void initializeBoolRelations() {
        List<ObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        relations.add(new FamixRelation("isConstructor", boolConcept));
        relations.add(new FamixRelation("isExternal", boolConcept));
        relations.add(new FamixRelation("isInterface", boolConcept));
    }

    private void initializeObjectRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        List<ObjectType> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(conceptManager.getConceptByName("FamixClass"));
        relations.add(new FamixRelation("hasDefiningClass", famixClassConcept));
        relations.add(new FamixRelation("hasDeclaredException", famixClassConcept));
        relations.add(new FamixRelation("hasCaughtException", famixClassConcept));
        relations.add(new FamixRelation("throwsException", famixClassConcept));
        relations.add(new FamixRelation("hasSubClass", famixClassConcept));
        relations.add(new FamixRelation("hasSuperClass", famixClassConcept));

        // Parameter relations
        List<ObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(conceptManager.getConceptByName("Parameter"));
        relations.add(new FamixRelation("definesParameter", parameterConcept));

        // LocalVariable relations
        List<ObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(conceptManager.getConceptByName("LocalVariable"));
        relations.add(new FamixRelation("definesVariable", localVariableConcept));

        // AnnotationInstance relations
        List<ObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(conceptManager.getConceptByName("AnnotationInstance"));
        relations.add(new FamixRelation("hasAnnotationInstance", annotationInstanceConcept));

        // AnnotationType relations
        List<ObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new FamixRelation("hasAnnotationType", annotationTypeConcept));

        // AnnotationTypeAttribute relations
        List<ObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationTypeAttribute"));
        relations.add(
                new FamixRelation("hasAnnotationTypeAttribute", annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        List<ObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationInstanceAttribute"));
        relations.add(
                new FamixRelation(
                        "hasAnnotationInstanceAttribute", annotationInstanceAttributeConcept));

        // Attribute relations
        List<ObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(conceptManager.getConceptByName("Attribute"));
        relations.add(new FamixRelation("definesAttribute", attributeConcept));

        // Method relations
        List<ObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(conceptManager.getConceptByName("Method"));
        relations.add(new FamixRelation("definesMethod", methodConcept));

        // Type relations
        List<ObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(conceptManager.getConceptByName("FamixClass"));
        typeConcepts.add(conceptManager.getConceptByName("Enum"));
        typeConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new FamixRelation("imports", typeConcepts));

        // Class and Enum relations
        List<ObjectType> classEnumConcepts = new LinkedList<>();
        classEnumConcepts.add(conceptManager.getConceptByName("FamixClass"));
        classEnumConcepts.add(conceptManager.getConceptByName("Enum"));
        relations.add(new FamixRelation("definesNestedType", classEnumConcepts));

        // Type + NameSpace relations
        List<ObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Namespace"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("FamixClass"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Enum"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new FamixRelation("namespaceContains", namespaceContainsConcepts));

        // Type + Primitive relations
        List<ObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(conceptManager.getConceptByName("FamixClass"));
        typesAndprimitives.add(conceptManager.getConceptByName("Enum"));
        typesAndprimitives.add(conceptManager.getConceptByName("AnnotationType"));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        relations.add(new FamixRelation("hasDeclaredType", typesAndprimitives));
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public List<CustomRelation> getCustomRelations() {
        return getRelations().stream()
                .filter(CustomRelation.class::isInstance)
                .map(CustomRelation.class::cast)
                .collect(Collectors.toList());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
