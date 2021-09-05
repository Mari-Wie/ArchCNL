package org.archcnl.domain.input.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;

import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;

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

    public void addOrAppend(CustomConcept concept) {
        try {
            if (!doesConceptExist(concept)) {
                addConcept(concept);
            } else {
                Concept existingConcept = getConceptByName(concept.getName());
                if (existingConcept instanceof CustomConcept) {
                    CustomConcept existingCustomConcept = (CustomConcept) existingConcept;
                    existingCustomConcept
                            .getMapping()
                            .addAllAndTriplets(concept.getMapping().getWhenTriplets());
                }
            }
        } catch (ConceptAlreadyExistsException | ConceptDoesNotExistException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of mapping \""
                            + concept.getName()
                            + "\" failed unexpectedly.");
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
        concepts.add(new DefaultConcept("FamixClass"));
        concepts.add(new DefaultConcept("Namespace"));
        concepts.add(new DefaultConcept("Enum"));
        concepts.add(new DefaultConcept("AnnotationType"));
        concepts.add(new DefaultConcept("Method"));
        concepts.add(new DefaultConcept("Attribute"));
        concepts.add(new DefaultConcept("Inheritance"));
        concepts.add(new DefaultConcept("AnnotationInstance"));
        concepts.add(new DefaultConcept("AnnotationTypeAttribute"));
        concepts.add(new DefaultConcept("AnnotationInstanceAttribute"));
        concepts.add(new DefaultConcept("Parameter"));
        concepts.add(new DefaultConcept("LocalVariable"));
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public List<CustomConcept> getCustomConcepts() {
        List<CustomConcept> customConcepts = new LinkedList<>();
        for (Concept concept : getConcepts()) {
            if (concept instanceof CustomConcept) {
                customConcepts.add((CustomConcept) concept);
            }
        }
        return customConcepts;
    }
}
