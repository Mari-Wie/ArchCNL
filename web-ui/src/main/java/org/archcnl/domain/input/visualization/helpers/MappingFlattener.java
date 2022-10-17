package org.archcnl.domain.input.visualization.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class MappingFlattener {

    private List<AndTriplets> andTriplets;
    private Variable thenSubject;
    private ObjectType thenObject;

    private MappingFlattener(
            List<AndTriplets> andTriplets, Variable thenSubject, ObjectType thenObject) {
        this.andTriplets = andTriplets;
        this.thenSubject = thenSubject;
        this.thenObject = thenObject;
    }

    public static List<AndTriplets> flattenCustomRelations(
            List<AndTriplets> whenTriplets, Triplet thenTriplet)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener =
                new MappingFlattener(
                        whenTriplets, thenTriplet.getSubject(), thenTriplet.getObject());
        return flattener.flatten();
    }

    private List<AndTriplets> flatten() throws MappingToUmlTranslationFailedException {
        Optional<Variable> thenObjectOpt = Optional.empty();
        if (thenObject instanceof Variable) {
            thenObjectOpt = Optional.of((Variable) thenObject);
        }
        return flattenRelationMappings(
                andTriplets,
                new HashSet<>(),
                Optional.empty(),
                Optional.empty(),
                thenSubject,
                thenObjectOpt);
    }

    private List<AndTriplets> flattenRelationMappings(
            List<AndTriplets> andTriplets,
            Set<Variable> parentVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Variable thenSubject,
            Optional<Variable> thenObjectOpt)
            throws MappingToUmlTranslationFailedException {
        List<AndTriplets> flattenedAndTriplets = new ArrayList<>();
        for (AndTriplets whenTriplets : andTriplets) {
            flattenedAndTriplets.addAll(
                    flattenWhenTriplets(
                            whenTriplets,
                            parentVariables,
                            parentSubject,
                            parentObject,
                            thenSubject,
                            thenObjectOpt));
        }
        return flattenedAndTriplets;
    }

    private List<AndTriplets> flattenWhenTriplets(
            AndTriplets whenTriplets,
            Set<Variable> parentVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Variable thenSubject,
            Optional<Variable> thenObjectOpt)
            throws MappingToUmlTranslationFailedException {

        whenTriplets =
                useParentOrUniqueVariables(
                        whenTriplets,
                        parentVariables,
                        parentSubject,
                        parentObject,
                        thenSubject,
                        thenObjectOpt);
        List<List<Triplet>> flattened = Arrays.asList(new ArrayList<>());
        Set<Variable> usedVariables = getVariablesInUse(whenTriplets);

        for (Triplet triplet : whenTriplets.getTriplets()) {
            Relation predicate = triplet.getPredicate();

            if (predicate instanceof CustomRelation) {
                CustomRelation relation = (CustomRelation) predicate;
                Variable subject = triplet.getSubject();
                Variable object = (Variable) triplet.getObject();

                List<AndTriplets> variants =
                        flattenRelationMappings(
                                getWhenTriplets(relation),
                                usedVariables,
                                Optional.of(subject),
                                Optional.of(object),
                                getThenSubject(relation),
                                Optional.of(getThenObject(relation)));

                usedVariables.addAll(getVariablesInUse(variants)); // maybe remove again
                flattened = cartesianProduct(flattened, variants);
            } else {
                for (List<Triplet> innerList : flattened) {
                    innerList.add(triplet);
                }
            }
        }
        return flattened.stream().map(AndTriplets::new).collect(Collectors.toList());
    }

    private Set<Variable> getVariablesInUse(List<AndTriplets> variants) {
        Set<Variable> variables = new HashSet<>();
        for (AndTriplets andTriplets : variants) {
            variables.addAll(getVariablesInUse(andTriplets));
        }
        return variables;
    }

    private Set<Variable> getVariablesInUse(AndTriplets whenTriplets) {
        Set<Variable> usedVariables = new HashSet<>();
        for (Triplet triplet : whenTriplets.getTriplets()) {
            usedVariables.add(triplet.getSubject());
            if (triplet.getObject() instanceof Variable) {
                usedVariables.add((Variable) triplet.getObject());
            }
        }
        return usedVariables;
    }

    private AndTriplets useParentOrUniqueVariables(
            AndTriplets whenTriplets,
            Set<Variable> usedVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Variable thenSubject,
            Optional<Variable> thenObjectOpt) {

        List<Triplet> modifiedTriplets = new ArrayList<>();
        Map<Variable, Variable> renamedVariables = new HashMap<>();
        if (parentSubject.isPresent()) {
            renamedVariables.put(thenSubject, parentSubject.get());
        }
        if (parentObject.isPresent() && thenObjectOpt.isPresent()) {
            renamedVariables.put(thenObjectOpt.get(), parentObject.get());
        }

        for (Triplet triplet : whenTriplets.getTriplets()) {
            Variable subject =
                    NamePicker.pickUniqueVariable(
                            usedVariables, renamedVariables, triplet.getSubject());

            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                Variable objectVar = (Variable) object;
                object = NamePicker.pickUniqueVariable(usedVariables, renamedVariables, objectVar);
            }

            Relation predicate = triplet.getPredicate();
            modifiedTriplets.add(new Triplet(subject, predicate, object));
        }
        return new AndTriplets(modifiedTriplets);
    }

    private List<List<Triplet>> cartesianProduct(List<List<Triplet>> a, List<AndTriplets> b) {
        List<List<Triplet>> product = new ArrayList<>();
        for (var listFromA : a) {
            for (var andTripletsFromB : b) {
                List<Triplet> combined = new ArrayList<>(listFromA);
                combined.addAll(andTripletsFromB.getTriplets());
                product.add(combined);
            }
        }
        return product;
    }

    private List<AndTriplets> getWhenTriplets(CustomRelation relation)
            throws MappingToUmlTranslationFailedException {
        Optional<RelationMapping> mapping = relation.getMapping();
        if (mapping.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no mapping");
        } else if (mapping.get().getWhenTriplets().isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no whenTriplets");
        }
        return mapping.get().getWhenTriplets();
    }

    private Variable getThenSubject(CustomRelation relation)
            throws MappingToUmlTranslationFailedException {
        Optional<RelationMapping> mapping = relation.getMapping();
        if (mapping.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no mapping");
        }
        return mapping.get().getThenTriplet().getSubject();
    }

    private Variable getThenObject(CustomRelation relation)
            throws MappingToUmlTranslationFailedException {
        Optional<RelationMapping> mapping = relation.getMapping();
        if (mapping.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no mapping");
        } else if (!(mapping.get().getThenTriplet().getObject() instanceof Variable)) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no variable as a thenObject");
        }
        return (Variable) mapping.get().getThenTriplet().getObject();
    }
}
