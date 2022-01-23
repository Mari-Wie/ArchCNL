package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.ConformanceConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
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
        concepts.add(
                new FamixConcept(
                        "FamixClass",
                        "Models a class or an interface from the object-oriented programming style."));
        concepts.add(
                new FamixConcept(
                        "Namespace",
                        "A namespace serves as a unique identifier and can contain Enums or FamixClasses. Some programming languages call this concept a \"package\". The top-level namespace has an empty name."));
        concepts.add(
                new FamixConcept(
                        "Enum",
                        "Models an enumerated type from the object-oriented programming style."));
        concepts.add(
                new FamixConcept(
                        "AnnotationType",
                        "Models the declaration of a user-defined annotation. In Java source code they can be declared with the \"@interface\" prefix."));
        concepts.add(
                new FamixConcept(
                        "Method",
                        "Models a method, an interface operation, or a constructor from the object-oriented programming style."));
        concepts.add(
                new FamixConcept(
                        "Attribute",
                        "Models a field or attribute of an enum, class, or interface as known from the object-oriented programming style."));
        concepts.add(
                new FamixConcept(
                        "Inheritance",
                        "Models an inheritance relation between classes, or interfaces in the object-oriented programming style. Use the \"hasSuperClass\" and the \"hasSubClass\" relations to access the types involved in the inheritance."));
        concepts.add(
                new FamixConcept(
                        "AnnotationInstance",
                        "Models the use of an annotation type in the source code. For instance, in Java when some class is annotated with the annotation \"@Deprecated\", this is an annotation instance of the annotation type \"Deprecated\"."));
        concepts.add(
                new FamixConcept(
                        "AnnotationTypeAttribute",
                        "Models an attribute defined in the declaration of an annotation type."));
        concepts.add(
                new FamixConcept(
                        "AnnotationInstanceAttribute",
                        "Models an attribute-value pair present in a particular annotation (instance). In Java for example, when the annotation @Deprecated(since=\"today\") is modeled, there would be the member value pair (\"since\", \"today\")."));
        concepts.add(
                new FamixConcept(
                        "Parameter",
                        "Models a parameter of a method, an interface operation, or a constructor from the object-oriented programming style."));
        concepts.add(
                new FamixConcept(
                        "LocalVariable",
                        "Models a local variable defined in a method, or a constructor from the object-oriented programming style."));

        // Conformance
        concepts.add(
                new ConformanceConcept(
                        "ConformanceCheck",
                        "A ConformanceCheck checks if the actual architecture conforms to the specified architecture. The architecture specification is done using ArchitectureRules. Instances where these rules are not fulfilled are modelled as ArchitectureViolations."
                                + "A ConformanceCheck is responsible for adding rules with their violations to the architecture model"));
        concepts.add(
                new ConformanceConcept(
                        "ArchitectureRule",
                        "Models a rule about the architecture that should be satisfied. It is specified in a controlled natural language. Instances in the architecture where ArchitectureRules are not fulfilled will be modeled as ArchitectureViolations."));
        concepts.add(
                new ConformanceConcept(
                        "ArchitectureViolation",
                        "Models a violation to an ArchitectureRule. The ArchitectureViolation is proven by a proof which consists of several statments about the architecture, that in total contradict the ArchitectureRule."));
        concepts.add(
                new ConformanceConcept(
                        "Proof",
                        "Models an instance in the architecture where an ArchitectureRule is not met. A proof verifies an ArchitectureViolation by several statements about the architecture, that in total contradict the ArchitectureRule."));
        concepts.add(
                new ConformanceConcept(
                        "AssertedStatement",
                        "Models a fact about the architecture, which in combination with other statements about the architecture is used to prove ArchitectureViolations."));
        concepts.add(
                new ConformanceConcept(
                        "NotInferredStatement",
                        "Models a statement about the architecture that can not be deducted from it, which in combination with other statements about the architecture is used to prove ArchitectureViolations."));
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
