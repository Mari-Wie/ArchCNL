package org.archcnl.webui.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;

import org.archcnl.webui.datatypes.mappings.Relation.RelationType;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;
import org.archcnl.webui.exceptions.RelationAlreadyExistsException;
import org.archcnl.webui.exceptions.RelationDoesNotExistException;

public class RelationManager {

    private List<Relation> relations;
    private ConceptManager conceptManager;

    public RelationManager(ConceptManager conceptManager) throws ConceptDoesNotExistException {
        this.conceptManager = conceptManager;
        relations = new LinkedList<>();
        initializeRelations();
    }

    public void addRelation(Relation relation) throws RelationAlreadyExistsException {
        if (!doesRelationExist(relation)) {
            relations.add(relation);
        } else {
            throw new RelationAlreadyExistsException(relation.getName());
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

    public boolean doesRelationExist(Relation relation) {
        for (Relation existingRelation : relations) {
            if (relation.getName().equals(existingRelation.getName())) {
                return true;
            }
        }
        return false;
    }

    private void initializeRelations() throws ConceptDoesNotExistException {
        // Type relation
        relations.add(new Relation("is-of-type", "type"));

        // Regex relations
        relations.add(new Relation("matches", RelationType.matches));

        // String relations
        List<Concept> stringConcept = new LinkedList<>();
        stringConcept.add(conceptManager.getConceptByName("string"));
        relations.add(new Relation("hasModifier", RelationType.famix, stringConcept));
        relations.add(new Relation("hasName", RelationType.famix, stringConcept));
        relations.add(new Relation("hasSignature", RelationType.famix, stringConcept));
        relations.add(new Relation("hasValue", RelationType.famix, stringConcept));
        relations.add(new Relation("hasFullQualifiedName", RelationType.famix, stringConcept));

        // Bool relations
        List<Concept> boolConcept = new LinkedList<>();
        boolConcept.add(conceptManager.getConceptByName("bool"));
        relations.add(new Relation("isConstructor", RelationType.famix, boolConcept));
        relations.add(new Relation("isExternal", RelationType.famix, boolConcept));
        relations.add(new Relation("isInterface", RelationType.famix, boolConcept));

        // Object relations

        // FamixClass relations
        List<Concept> famixClassConcept = new LinkedList<>();
        famixClassConcept.add(conceptManager.getConceptByName("FamixClass"));
        relations.add(new Relation("hasDefiningClass", RelationType.famix, famixClassConcept));
        relations.add(new Relation("hasDeclaredException", RelationType.famix, famixClassConcept));
        relations.add(new Relation("hasCaughtException", RelationType.famix, famixClassConcept));
        relations.add(new Relation("throwsException", RelationType.famix, famixClassConcept));
        relations.add(new Relation("hasSubClass", RelationType.famix, famixClassConcept));
        relations.add(new Relation("hasSuperClass", RelationType.famix, famixClassConcept));

        // Parameter relations
        List<Concept> parameterConcept = new LinkedList<>();
        parameterConcept.add(conceptManager.getConceptByName("Parameter"));
        relations.add(new Relation("definesParameter", RelationType.famix, parameterConcept));

        // LocalVariable relations
        List<Concept> localVariableConcept = new LinkedList<>();
        localVariableConcept.add(conceptManager.getConceptByName("LocalVariable"));
        relations.add(new Relation("definesVariable", RelationType.famix, localVariableConcept));

        // AnnotationInstance relations
        List<Concept> annotationInstanceConcept = new LinkedList<>();
        annotationInstanceConcept.add(conceptManager.getConceptByName("AnnotationInstance"));
        relations.add(
                new Relation(
                        "hasAnnotationInstance", RelationType.famix, annotationInstanceConcept));

        // AnnotationType relations
        List<Concept> annotationTypeConcept = new LinkedList<>();
        annotationTypeConcept.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new Relation("hasAnnotationType", RelationType.famix, annotationTypeConcept));

        // AnnotationTypeAttribute relations
        List<Concept> annotationTypeAttributeConcept = new LinkedList<>();
        annotationTypeAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationTypeAttribute"));
        relations.add(
                new Relation(
                        "hasAnnotationTypeAttribute",
                        RelationType.famix,
                        annotationTypeAttributeConcept));

        // AnnotationInstanceAttribute relations
        List<Concept> annotationInstanceAttributeConcept = new LinkedList<>();
        annotationInstanceAttributeConcept.add(
                conceptManager.getConceptByName("AnnotationInstanceAttribute"));
        relations.add(
                new Relation(
                        "hasAnnotationInstanceAttribute",
                        RelationType.famix,
                        annotationInstanceAttributeConcept));

        // Attribute relations
        List<Concept> attributeConcept = new LinkedList<>();
        attributeConcept.add(conceptManager.getConceptByName("Attribute"));
        relations.add(new Relation("definesAttribute", RelationType.famix, attributeConcept));

        // Method relations
        List<Concept> methodConcept = new LinkedList<>();
        methodConcept.add(conceptManager.getConceptByName("Method"));
        relations.add(new Relation("definesMethod", RelationType.famix, methodConcept));

        // Type relations
        List<Concept> typeConcepts = new LinkedList<>();
        typeConcepts.add(conceptManager.getConceptByName("FamixClass"));
        typeConcepts.add(conceptManager.getConceptByName("Enum"));
        typeConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(new Relation("imports", RelationType.famix, typeConcepts));

        // Type + NameSpace relations
        List<Concept> namespaceContainsConcepts = new LinkedList<>();
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Namespace"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("FamixClass"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("Enum"));
        namespaceContainsConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        relations.add(
                new Relation("namespaceContains", RelationType.famix, namespaceContainsConcepts));

        // Type + Primitive relations
        List<Concept> typeAndprimitiveConcepts = new LinkedList<>();
        typeAndprimitiveConcepts.add(conceptManager.getConceptByName("FamixClass"));
        typeAndprimitiveConcepts.add(conceptManager.getConceptByName("Enum"));
        typeAndprimitiveConcepts.add(conceptManager.getConceptByName("AnnotationType"));
        typeAndprimitiveConcepts.add(conceptManager.getConceptByName("string"));
        typeAndprimitiveConcepts.add(conceptManager.getConceptByName("bool"));
        // TODO: Add other primitives
        relations.add(
                new Relation("hasDeclaredType", RelationType.famix, typeAndprimitiveConcepts));
    }

	public List<Relation> getRelations() {
		return relations;
	}
}
