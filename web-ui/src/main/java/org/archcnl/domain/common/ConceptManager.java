package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;

public class ConceptManager {

    private List<Concept> concepts;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ConceptManager() {
        concepts = new LinkedList<>();
        initializeConcepts();
    }

    public void addConcept(Concept concept) throws ConceptAlreadyExistsException {
        if (!doesConceptExist(concept)) {
            concepts.add(concept);
            propertyChangeSupport.firePropertyChange("newConcept", null, concept);
        } else {
            throw new ConceptAlreadyExistsException(concept.getName());
        }
    }

    public void conceptHasBeenUpdated(Concept concept) {
        propertyChangeSupport.firePropertyChange("conceptUpdated", null, concept);
    }

    public void addOrAppend(CustomConcept concept) throws UnrelatedMappingException {
        try {
            if (!doesConceptExist(concept)) {
                addConcept(concept);
            } else {
                Concept existingConcept = getConceptByName(concept.getName());
                if (existingConcept instanceof CustomConcept) {
                    CustomConcept existingCustomConcept = (CustomConcept) existingConcept;
                    Optional<ConceptMapping> existingMapping = existingCustomConcept.getMapping();
                    Optional<ConceptMapping> newMapping = concept.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomConcept.setMapping(newMapping.get());
                    }
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
        return concepts.stream()
                .filter(concept -> name.equals(concept.getName()))
                .findAny()
                .orElseThrow(() -> new ConceptDoesNotExistException(name));
    }

    public boolean doesConceptExist(Concept concept) {
        return concepts.stream()
                .anyMatch(existingConcept -> concept.getName().equals(existingConcept.getName()));
    }

    private void initializeConcepts() {
        // Famix
        concepts.add(new DefaultConcept("FamixClass", ""));
        concepts.add(new DefaultConcept("Namespace", ""));
        concepts.add(new DefaultConcept("Enum", ""));
        concepts.add(new DefaultConcept("AnnotationType", ""));
        concepts.add(new DefaultConcept("Method", ""));
        concepts.add(new DefaultConcept("Attribute", ""));
        concepts.add(new DefaultConcept("Inheritance", ""));
        concepts.add(new DefaultConcept("AnnotationInstance", ""));
        concepts.add(new DefaultConcept("AnnotationTypeAttribute", ""));
        concepts.add(new DefaultConcept("AnnotationInstanceAttribute", ""));
        concepts.add(new DefaultConcept("Parameter", ""));
        concepts.add(new DefaultConcept("LocalVariable", ""));

        // Conformance
        concepts.add(new ConformanceConcept("ConformanceCheck", ""));
        concepts.add(new ConformanceConcept("ArchitectureRule", ""));
        concepts.add(new ConformanceConcept("ArchitectureViolation", ""));
        concepts.add(new ConformanceConcept("Proof", ""));
        concepts.add(new ConformanceConcept("AssertedStatement", ""));
        concepts.add(new ConformanceConcept("NotInferredStatement", ""));
    }

    public List<Concept> getInputConcepts() {
        return concepts.stream()
                .filter(Predicate.not(ConformanceConcept.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<Concept> getOutputConcepts() {
        return concepts;
    }

    public List<CustomConcept> getCustomConcepts() {
        return concepts.stream()
                .filter(CustomConcept.class::isInstance)
                .map(CustomConcept.class::cast)
                .collect(Collectors.toList());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
