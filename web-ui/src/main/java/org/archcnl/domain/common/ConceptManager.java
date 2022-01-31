package org.archcnl.domain.common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import java.lang.RuntimeException;

public class ConceptManager extends HierarchyManager<Concept> {

    private TreeMap<String, Concept> concepts;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ConceptManager() {
        super();
        concepts = new TreeMap<String, Concept>();
        // TODO: move this to somewhere where to ConceptManager is created and files are loaded,
        // also
        // this has to be in something like: create empty project
        addHierarchyRoot("Default Concepts");
        addHierarchyRoot("Custom Concepts");

        initializeConcepts();
    }

    public void addConcept(Concept concept) throws ConceptAlreadyExistsException {
        if (!doesConceptExist(concept)) {
            concepts.put(concept.getName(), concept);
            propertyChangeSupport.firePropertyChange("newConcept", null, concept);
        } else {
            throw new ConceptAlreadyExistsException(concept.getName());
        }
    }

    public void addToParent(Concept concept, HierarchyNode<Concept> parent)
            throws ConceptAlreadyExistsException {
            addConcept(concept);
            parent.add(concept);
    }

    public void addToParent(Concept concept, String parentName)
            throws ConceptAlreadyExistsException {
            addConcept(concept);
            Optional<HierarchyNode<Concept>> parent =
                hierarchy_roots.stream()
                .filter(node -> parentName.equals(node.getName()))
                .findAny();
            if(!parent.isPresent()){
            // TODO: error handling
            }
            parent.get().add(concept);
    }


    public void conceptHasBeenUpdated(Concept concept) {
        propertyChangeSupport.firePropertyChange("conceptUpdated", null, concept);
    }

    public void append(CustomConcept concept)throws UnrelatedMappingException{
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

    public void addOrAppend(CustomConcept concept) throws UnrelatedMappingException {
        try {
            if (!doesConceptExist(concept)) {
                addToParent(concept, "Custom Concepts");
            } else {
                append(concept);
            }
        } catch (ConceptAlreadyExistsException e) {
            // cannot occur << CODE SMELL
            throw new RuntimeException(
                    "Adding and appending of mapping \""
                    + concept.getName()
                    + "\" failed unexpectedly.");
        }
    }

    public Optional<Concept> getConceptByName(String name) {
        return Optional.ofNullable(concepts.get(name));
    }

    public boolean doesConceptExist(Concept concept) {
        return concepts.containsValue(concept);
    }

    // TODO: kick the inits out of here and load/init them from outside the manager
    private void initFamixConcept(String name, String description) {
        try {
            addToParent(new FamixConcept(name, description), "Default Concepts");
        } catch (ConceptAlreadyExistsException e) {
            e.printStackTrace();
            throw new RuntimeException("Concept Already Exitsts");
        }
    }

    private void initConformanceConcept(String name, String description) {
        try {
            addToParent(new ConformanceConcept(name, description), "Default Concepts");
        } catch (ConceptAlreadyExistsException e) {
            e.printStackTrace();
            throw new RuntimeException("Concept already exists");
        }
    }

    private void initializeConcepts() {
        // Famix
        initFamixConcept(
                "FamixClass",
                "Models a class or an interface from the object-oriented programming style.");
        initFamixConcept(
                "Namespace",
                "A namespace serves as a unique identifier and can contain Enums or FamixClasses. Some programming languages call this concept a \"package\". The top-level namespace has an empty name.");
        initFamixConcept(
                "Enum", "Models an enumerated type from the object-oriented programming style.");
        initFamixConcept(
                "AnnotationType",
                "Models the declaration of a user-defined annotation. In Java source code they can be declared with the \"@interface\" prefix.");
        initFamixConcept(
                "Method",
                "Models a method, an interface operation, or a constructor from the object-oriented programming style.");
        initFamixConcept(
                "Attribute",
                "Models a field or attribute of an enum, class, or interface as known from the object-oriented programming style.");
        initFamixConcept(
                "Inheritance",
                "Models an inheritance relation between classes, or interfaces in the object-oriented programming style. Use the \"hasSuperClass\" and the \"hasSubClass\" relations to access the types involved in the inheritance.");
        initFamixConcept(
                "AnnotationInstance",
                "Models the use of an annotation type in the source code. For instance, in Java when some class is annotated with the annotation \"@Deprecated\", this is an annotation instance of the annotation type \"Deprecated\".");
        initFamixConcept(
                "AnnotationTypeAttribute",
                "Models an attribute defined in the declaration of an annotation type.");
        initFamixConcept(
                "AnnotationInstanceAttribute",
                "Models an attribute-value pair present in a particular annotation (instance). In Java for example, when the annotation @Deprecated(since=\"today\") is modeled, there would be the member value pair (\"since\", \"today\").");
        initFamixConcept(
                "Parameter",
                "Models a parameter of a method, an interface operation, or a constructor from the object-oriented programming style.");
        initFamixConcept(
                "LocalVariable",
                "Models a local variable defined in a method, or a constructor from the object-oriented programming style.");

        // Conformance
        initConformanceConcept(
                "ConformanceCheck",
                "A ConformanceCheck checks if the actual architecture conforms to the specified architecture. The architecture specification is done using ArchitectureRules. Instances where these rules are not fulfilled are modelled as ArchitectureViolations."
                + "A ConformanceCheck is responsible for adding rules with their violations to the architecture model");
        initConformanceConcept(
                "ArchitectureRule",
                "Models a rule about the architecture that should be satisfied. It is specified in a controlled natural language. Instances in the architecture where ArchitectureRules are not fulfilled will be modeled as ArchitectureViolations.");
        initConformanceConcept(
                "ArchitectureViolation",
                "Models a violation to an ArchitectureRule. The ArchitectureViolation is proven by a proof which consists of several statments about the architecture, that in total contradict the ArchitectureRule.");
        initConformanceConcept(
                "Proof",
                "Models an instance in the architecture where an ArchitectureRule is not met. A proof verifies an ArchitectureViolation by several statements about the architecture, that in total contradict the ArchitectureRule.");
        initConformanceConcept(
                "AssertedStatement",
                "Models a fact about the architecture, which in combination with other statements about the architecture is used to prove ArchitectureViolations.");
        initConformanceConcept(
                "NotInferredStatement",
                "Models a statement about the architecture that can not be deducted from it, which in combination with other statements about the architecture is used to prove ArchitectureViolations.");
    }

    public List<Concept> getInputConcepts() {
        return concepts.values().stream()
            .filter(Predicate.not(ConformanceConcept.class::isInstance))
            .collect(Collectors.toList());
    }

    public List<Concept> getOutputConcepts() {
        return concepts.values().stream().collect(Collectors.toList());
    }

    public List<CustomConcept> getCustomConcepts() {
        return concepts.values().stream()
            .filter(CustomConcept.class::isInstance)
            .map(CustomConcept.class::cast)
            .collect(Collectors.toList());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
