package org.archcnl.domain.input.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;

import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;

public class RelationManager {

    private List<Relation> relations;

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
        } else {
            throw new RelationAlreadyExistsException(relation.getName());
        }
    }

    public void addOrAppend(CustomRelation relation) {
        try {
            if (!doesRelationExist(relation)) {
                addRelation(relation);
            } else {
                Relation existingRelation = getRelationByName(relation.getName());
                if (existingRelation instanceof CustomRelation) {
                    CustomRelation existingCustomRelation = (CustomRelation) existingRelation;
                    existingCustomRelation
                            .getMapping()
                            .addAllAndTriplets(relation.getMapping().getWhenTriplets());
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
        for (Relation relation : relations) {
            if (name.equals(relation.getName())) {
                return relation;
            }
        }
        throw new RelationDoesNotExistException(name);
    }

    public Relation getRelationByRealName(String realName) throws RelationDoesNotExistException {
        for (Relation relation : relations) {
            if (relation instanceof SpecialRelation) {
                SpecialRelation specialRelation = (SpecialRelation) relation;
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
        for (Relation existingRelation : relations) {
            if (relation.getName().equals(existingRelation.getName())) {
                return true;
            }
        }
        return false;
    }

    private void initializeSpecialRelations() {
        List<ObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new SpecialRelation("matches", "regex", stringConcept));
    }

    private void initializeTypeRelation() {
        relations.add(new TypeRelation("is-of-type", "type"));
    }

    private void initializeStringRelations() {
        List<ObjectType> stringConcept = new LinkedList<>();
        stringConcept.add(new StringValue(""));
        relations.add(new DefaultRelation("hasModifier", stringConcept));
        relations.add(new DefaultRelation("hasName", stringConcept));
        relations.add(new DefaultRelation("hasSignature", stringConcept));
        relations.add(new DefaultRelation("hasValue", stringConcept));
        relations.add(new DefaultRelation("hasFullQualifiedName", stringConcept));
    }

    private void initializeBoolRelations() {
        List<ObjectType> boolConcept = new LinkedList<>();
        boolConcept.add(new BooleanValue(false));
        relations.add(new DefaultRelation("isConstructor", boolConcept));
        relations.add(new DefaultRelation("isExternal", boolConcept));
        relations.add(new DefaultRelation("isInterface", boolConcept));
    }

    private void initializeObjectRelations(ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        // FamixClass relations
        List<ObjectType> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(conceptManager.getConceptByName("FamixClass"));
        relations.add(new DefaultRelation("hasDefiningClass", famixClassConcept));
        relations.add(new DefaultRelation("hasDeclaredException", famixClassConcept));
        relations.add(new DefaultRelation("hasCaughtException", famixClassConcept));
        relations.add(new DefaultRelation("throwsException", famixClassConcept));
        relations.add(new DefaultRelation("hasSubClass", famixClassConcept));
        relations.add(new DefaultRelation("hasSuperClass", famixClassConcept));

        // Parameter relations
        List<ObjectType> parameterConcept = new LinkedList<>();
        parameterConcept.add(conceptManager.getConceptByName("Parameter"));
        relations.add(new DefaultRelation("definesParameter", parameterConcept));

        // LocalVariable relations
        List<ObjectType> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(conceptManager.getConceptByName("LocalVariable"));
        relations.add(new DefaultRelation("definesVariable", localVariableConcept));

        // AnnotationInstance relations
        List<ObjectType> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(conceptManager.getConceptByName("AnnotationInstance"));
        relations.add(new DefaultRelation("hasAnnotationInstance", annotationInstanceConcept));

        // AnnotationType relations
        List<ObjectType> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("hasAnnotationType", annotationTypeConcept));

        // AnnotationTypeAttribute relations
        List<ObjectType> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationTypeAttribute"));
        relations.add(
                new DefaultRelation("hasAnnotationTypeAttribute", annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        List<ObjectType> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationInstanceAttribute"));
        relations.add(
                new DefaultRelation(
                        "hasAnnotationInstanceAttribute", annotationInstanceAttributeConcept));

        // Attribute relations
        List<ObjectType> attributeConcept = new LinkedList<>();
        attributeConcept.add(conceptManager.getConceptByName("Attribute"));
        relations.add(new DefaultRelation("definesAttribute", attributeConcept));

        // Method relations
        List<ObjectType> methodConcept = new LinkedList<>();
        methodConcept.add(conceptManager.getConceptByName("Method"));
        relations.add(new DefaultRelation("definesMethod", methodConcept));

        // Type relations
        List<ObjectType> typeConcepts = new LinkedList<>();
        typeConcepts.add(conceptManager.getConceptByName("FamixClass"));
        typeConcepts.add(conceptManager.getConceptByName("Enum"));
        typeConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("imports", typeConcepts));

        // Type + NameSpace relations
        List<ObjectType> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Namespace"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("FamixClass"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Enum"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new DefaultRelation("namespaceContains", namespaceContainsConcepts));

        // Type + Primitive relations
        List<ObjectType> typesAndprimitives = new LinkedList<>();
        typesAndprimitives.add(conceptManager.getConceptByName("FamixClass"));
        typesAndprimitives.add(conceptManager.getConceptByName("Enum"));
        typesAndprimitives.add(conceptManager.getConceptByName("AnnotationType"));
        typesAndprimitives.add(new StringValue(""));
        typesAndprimitives.add(new BooleanValue(false));
        relations.add(new DefaultRelation("hasDeclaredType", typesAndprimitives));
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public List<CustomRelation> getCustomRelations() {
        List<CustomRelation> customRelations = new LinkedList<>();
        for (Relation relation : getRelations()) {
            if (relation instanceof CustomRelation) {
                customRelations.add((CustomRelation) relation);
            }
        }
        return customRelations;
    }
}
