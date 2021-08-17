package org.archcnl.webui.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.webui.datatypes.mappings.Concept.ConceptType;
import org.archcnl.webui.exceptions.ConceptAlreadyExistsException;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;

public class ConceptManager {

    private List<Concept> concepts;

    public ConceptManager() {
        concepts = new LinkedList<>();

        initializeConcepts();
    }

    public void addConcept(Concept concept) throws ConceptAlreadyExistsException {
        if (!doesConceptExist(concept)) {
            concepts.add(concept);
        } else {
            throw new ConceptAlreadyExistsException(concept.getName());
        }
    }

    public Concept getConceptByName(String name) throws ConceptDoesNotExistException {
        for (Concept concept : concepts) {
            if (name.equals(concept.getName())) {
                return concept;
            }
        }
        throw new ConceptDoesNotExistException(name);
    }

    public boolean doesConceptExist(Concept concept) {
        for (Concept existingConcept : concepts) {
            if (concept.getName().equals(existingConcept.getName())) {
                return true;
            }
        }
        return false;
    }

    private void initializeConcepts() {
        concepts.add(new Concept("FamixClass", ConceptType.famix));
        concepts.add(new Concept("Namespace", ConceptType.famix));
        concepts.add(new Concept("Enum", ConceptType.famix));
        concepts.add(new Concept("AnnotationType", ConceptType.famix));
        concepts.add(new Concept("Method", ConceptType.famix));
        concepts.add(new Concept("Attribute", ConceptType.famix));
        concepts.add(new Concept("Inheritance", ConceptType.famix));
        concepts.add(new Concept("AnnotationInstance", ConceptType.famix));
        concepts.add(new Concept("AnnotationTypeAttribute", ConceptType.famix));
        concepts.add(new Concept("AnnotationInstanceAttribute", ConceptType.famix));
        concepts.add(new Concept("Parameter", ConceptType.famix));
        concepts.add(new Concept("LocalVariable", ConceptType.famix));
        concepts.add(new Concept("string", ConceptType.famix));
        concepts.add(new Concept("bool", ConceptType.famix));
    }
}
