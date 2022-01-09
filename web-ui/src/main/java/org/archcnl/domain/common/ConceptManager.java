package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
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
                Optional<Concept> existingConceptOpt = getConceptByName(concept.getName());
                if (existingConceptOpt.isPresent()
                        && existingConceptOpt.get() instanceof CustomConcept) {
                    CustomConcept existingCustomConcept = (CustomConcept) existingConceptOpt.get();
                    Optional<ConceptMapping> existingMapping = existingCustomConcept.getMapping();
                    Optional<ConceptMapping> newMapping = concept.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomConcept.setMapping(newMapping.get());
                    }
                }
            }
        } catch (ConceptAlreadyExistsException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of mapping \""
                            + concept.getName()
                            + "\" failed unexpectedly.");
        }
    }

    public Optional<Concept> getConceptByName(String name) {
        return concepts.stream().filter(concept -> name.equals(concept.getName())).findAny();
    }

    public boolean doesConceptExist(Concept concept) {
        return concepts.stream()
                .anyMatch(existingConcept -> concept.getName().equals(existingConcept.getName()));
    }

    private void initializeConcepts() {
        // Famix
        concepts.add(new FamixConcept("FamixClass", ""));
        concepts.add(new FamixConcept("Namespace", ""));
        concepts.add(new FamixConcept("Enum", ""));
        concepts.add(new FamixConcept("AnnotationType", ""));
        concepts.add(new FamixConcept("Method", ""));
        concepts.add(new FamixConcept("Attribute", ""));
        concepts.add(new FamixConcept("Inheritance", ""));
        concepts.add(new FamixConcept("AnnotationInstance", ""));
        concepts.add(new FamixConcept("AnnotationTypeAttribute", ""));
        concepts.add(new FamixConcept("AnnotationInstanceAttribute", ""));
        concepts.add(new FamixConcept("Parameter", ""));
        concepts.add(new FamixConcept("LocalVariable", ""));

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
