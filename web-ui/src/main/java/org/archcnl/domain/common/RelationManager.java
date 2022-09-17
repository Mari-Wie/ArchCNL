package org.archcnl.domain.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.ConformanceRelation;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class RelationManager extends HierarchyManager<Relation> {

    private Map<String, Relation> relations;
    private ConceptManager conceptManager;

    public RelationManager(final ConceptManager conceptManager)
            throws ConceptDoesNotExistException {
        this.relations = new HashMap<>();
        this.conceptManager = conceptManager;

        addHierarchyRoot("Default Relations");
        addHierarchyRoot("Custom Relations");
        initializeDefaultRelations();
    }

    public void addRelation(final Relation relation) throws RelationAlreadyExistsException {
        if (doesRelationExist(relation.getName())) {
            throw new RelationAlreadyExistsException(relation.getName());
        }
        relations.put(relation.getName(), relation);
    }

    public void addToParent(Relation relation, String parentName)
            throws RelationAlreadyExistsException {
        addRelation(relation);
        Optional<HierarchyNode<Relation>> parent =
                hierarchyRoots.stream().filter(node -> parentName.equals(node.getName())).findAny();
        if (!parent.isPresent()) {
            // TODO: error handling
        }
        parent.get().add(relation);
    }
    // TODO: fix this lazy coupout by removing it and refactoring the init functions. This function
    // is only here because I was to lazy to fix all the init functions.
    private void addToDefault(Relation relation) {
        try {
            addToParent(relation, "Default Relations");
        } catch (RelationAlreadyExistsException e) {
            e.printStackTrace();
            throw new RuntimeException("Relation " + relation.transformToGui() + " already exists");
        }
    }

    public void addOrAppend(final CustomRelation relation) throws UnrelatedMappingException {
        try {
            if (!doesRelationExist(relation.getName())) {
                addToParent(relation, "Custom Relations");
            } else {
                final Optional<Relation> existingRelationOpt =
                        getRelationByName(relation.getName());
                if (existingRelationOpt.isPresent()
                        && existingRelationOpt.get() instanceof CustomRelation) {
                    final CustomRelation existingCustomRelation =
                            (CustomRelation) existingRelationOpt.get();
                    final Optional<RelationMapping> existingMapping =
                            existingCustomRelation.getMapping();
                    final Optional<RelationMapping> newMapping = relation.getMapping();
                    if (existingMapping.isPresent() && newMapping.isPresent()) {
                        existingMapping.get().addAllAndTriplets(newMapping.get().getWhenTriplets());
                    } else if (existingMapping.isEmpty() && newMapping.isPresent()) {
                        existingCustomRelation.setMapping(newMapping.get(), conceptManager);
                    }
                }
            }
        } catch (final RelationAlreadyExistsException e) {
            // cannot occur
            throw new RuntimeException(
                    "Adding and appending of of mapping \""
                            + relation.getName()
                            + "\" failed unexpectedly.");
        }
    }

    public Optional<Relation> getRelationByName(final String name) {
        return Optional.ofNullable(relations.get(name));
    }

    public Optional<Relation> getRelationByRealName(final String realName) {
        for (final Relation relation : relations.values()) {
            if (relation instanceof JenaBuiltinRelation) {
                final JenaBuiltinRelation specialRelation = (JenaBuiltinRelation) relation;
                if (realName.equals(specialRelation.getRealName())) {
                    return Optional.of(specialRelation);
                }
            } else if (relation instanceof TypeRelation) {
                final TypeRelation typeRelation = (TypeRelation) relation;
                if (realName.equals(typeRelation.getRealName())) {
                    return Optional.of(typeRelation);
                }
            }
        }
        return Optional.empty();
    }

    public boolean doesRelationExist(final String name) {
        return relations.containsKey(name);
    }

    public void updateName(String oldName, String newName) throws RelationAlreadyExistsException {
        if (!relations.containsKey(oldName)) {
            return;
        }
        if (doesRelationExist(newName) && !oldName.equals(newName)) {
            throw new RelationAlreadyExistsException(newName);
        }
        Relation relation = relations.remove(oldName);
        relation.changeName(newName);
        relations.put(newName, relation);
    }

    public void removeRelation(Relation relation) {
        if (relation != null) {
            relations.remove(relation.getName());
        }
        removeFromHierarchy(new HierarchyNode<>(relation));
    }

    public void removeNode(HierarchyNode<Relation> node) {
        if (node.hasEntry()) {
            relations.remove(node.getName());
        }
        removeFromHierarchy(node);
    }

    public List<Relation> getInputRelations() {
        return relations.values().stream()
                .filter(Predicate.not(ConformanceRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<Relation> getOutputRelations() {
        return relations.values().stream()
                .filter(Predicate.not(JenaBuiltinRelation.class::isInstance))
                .collect(Collectors.toList());
    }

    public List<CustomRelation> getCustomRelations() {
        return relations.values().stream()
                .filter(CustomRelation.class::isInstance)
                .map(CustomRelation.class::cast)
                .collect(Collectors.toList());
    }

    private void initializeDefaultRelations() throws ConceptDoesNotExistException {
        DefaultRelationInitializer initializer = new DefaultRelationInitializer(conceptManager);
        initializer.getDefaultRelations().forEach(this::addToDefault);
    }
}
