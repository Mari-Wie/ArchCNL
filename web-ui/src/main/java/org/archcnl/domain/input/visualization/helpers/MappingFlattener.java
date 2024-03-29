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
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredMapping;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class MappingFlattener {

    private List<ColoredVariant> variants;
    private Optional<Variable> thenSubject;
    private Optional<ObjectType> thenObject;

    public MappingFlattener(ColoredMapping coloredMapping) {
        this.variants = coloredMapping.getVariants();
        this.thenSubject = Optional.of(coloredMapping.getThenTriplet().getSubject());
        this.thenObject = Optional.of(coloredMapping.getThenTriplet().getObject());
    }

    public MappingFlattener(List<ColoredTriplet> ruleTriplets) {
        this.variants = Arrays.asList(new ColoredVariant(ruleTriplets));
        this.thenSubject = Optional.empty();
        this.thenObject = Optional.empty();
    }

    /**
     * Dissolves all mappings from present custom relations into the given variants. Will create new
     * copies of variants when a relation mapping with multiple variants is encountered.
     *
     * @return The given variants including dissolved relation mappings
     * @throws MappingToUmlTranslationFailedException
     */
    public List<ColoredVariant> flattenCustomRelations()
            throws MappingToUmlTranslationFailedException {
        Optional<Variable> thenObjectOpt = Optional.empty();
        if (thenObject.isPresent() && thenObject.get() instanceof Variable) {
            thenObjectOpt = Optional.of((Variable) thenObject.get());
        }
        return flattenRelationMappings(
                variants,
                new HashSet<>(),
                Optional.empty(),
                Optional.empty(),
                thenSubject,
                thenObjectOpt);
    }

    private List<ColoredVariant> flattenRelationMappings(
            List<ColoredVariant> variants,
            Set<Variable> parentVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Optional<Variable> thenSubject,
            Optional<Variable> thenObjectOpt)
            throws MappingToUmlTranslationFailedException {
        List<ColoredVariant> flattenedVariants = new ArrayList<>();
        for (ColoredVariant variant : variants) {
            flattenedVariants.addAll(
                    flattenWhenTriplets(
                            variant,
                            new HashSet<>(parentVariables),
                            parentSubject,
                            parentObject,
                            thenSubject,
                            thenObjectOpt));
        }
        return flattenedVariants;
    }

    /**
     * Dissolves all mappings behind custom relations into the given variant.
     *
     * @param variant The variant to be flattened
     * @param parentVariables Variables that can't be used again
     * @param parentSubject Every occurrence of the thenSubject will be changed to this variable
     * @param parentObject Every occurrence of the thenObject will be changed to this variable
     * @param thenSubject This variable will be replaced by parentSubject
     * @param thenObjectOpt This variable will be replaced by parentObject
     * @return Given variants after flattening of relation mappings
     * @throws MappingToUmlTranslationFailedException
     */
    private List<ColoredVariant> flattenWhenTriplets(
            ColoredVariant variant,
            Set<Variable> parentVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Optional<Variable> thenSubject,
            Optional<Variable> thenObjectOpt)
            throws MappingToUmlTranslationFailedException {

        useParentOrUniqueVariables(
                variant, parentVariables, parentSubject, parentObject, thenSubject, thenObjectOpt);

        List<List<ColoredTriplet>> flattened = Arrays.asList(new ArrayList<>());
        Set<Variable> usedVariables = getVariablesInUse(variant);

        for (ColoredTriplet triplet : variant.getTriplets()) {
            Relation predicate = triplet.getPredicate();

            if (predicate instanceof CustomRelation) {
                CustomRelation relation = (CustomRelation) predicate;
                Variable subject = triplet.getSubject();
                Variable object = (Variable) triplet.getObject();

                List<ColoredVariant> subVariants = getVariants(relation);
                copyParentColorState(subVariants, triplet.getColorState(), subject, object);

                subVariants =
                        flattenRelationMappings(
                                subVariants,
                                usedVariables,
                                Optional.of(subject),
                                Optional.of(object),
                                Optional.of(getThenSubject(relation)),
                                Optional.of(getThenObject(relation)));

                usedVariables.addAll(getVariablesInUse(subVariants));
                flattened = cartesianProduct(flattened, subVariants);
            }
            for (List<ColoredTriplet> innerList : flattened) {
                innerList.add(triplet);
            }
        }
        return flattened.stream().map(ColoredVariant::new).collect(Collectors.toList());
    }

    private void copyParentColorState(
            List<ColoredVariant> variants, ColorState state, Variable subject, Variable object) {
        for (ColoredVariant variant : variants) {
            for (ColoredTriplet triplet : variant.getTriplets()) {
                if (doesNotUseThenVariables(triplet, subject, object)) {
                    triplet.setColorState(state);
                }
            }
        }
    }

    private boolean doesNotUseThenVariables(
            ColoredTriplet triplet, Variable subject, Variable object) {
        ObjectType tripletObject = triplet.getObject();
        Variable tripletSubject = triplet.getSubject();
        return !tripletSubject.equals(subject)
                && !tripletSubject.equals(object)
                && !tripletObject.equals(subject)
                && !tripletObject.equals(object);
    }

    private Set<Variable> getVariablesInUse(List<ColoredVariant> variants) {
        Set<Variable> variables = new HashSet<>();
        for (ColoredVariant variant : variants) {
            variables.addAll(getVariablesInUse(variant));
        }
        return variables;
    }

    private Set<Variable> getVariablesInUse(ColoredVariant variant) {
        Set<Variable> usedVariables = new HashSet<>();
        for (Triplet triplet : variant.getTriplets()) {
            usedVariables.add(triplet.getSubject());
            if (triplet.getObject() instanceof Variable) {
                usedVariables.add((Variable) triplet.getObject());
            }
        }
        return usedVariables;
    }

    private void useParentOrUniqueVariables(
            ColoredVariant variant,
            Set<Variable> usedVariables,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Optional<Variable> thenSubject,
            Optional<Variable> thenObjectOpt) {

        Map<Variable, Variable> renamedVariables = new HashMap<>();
        if (parentSubject.isPresent() && thenSubject.isPresent()) {
            renamedVariables.put(thenSubject.get(), parentSubject.get());
        }
        if (parentObject.isPresent() && thenObjectOpt.isPresent()) {
            renamedVariables.put(thenObjectOpt.get(), parentObject.get());
        }

        for (ColoredTriplet triplet : variant.getTriplets()) {
            Variable subject =
                    NamePicker.pickUniqueVariable(
                            usedVariables, renamedVariables, triplet.getSubject());

            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                Variable objectVar = (Variable) object;
                object = NamePicker.pickUniqueVariable(usedVariables, renamedVariables, objectVar);
            }

            triplet.setSubject(subject);
            triplet.setObject(object);
        }
    }

    /** Append all lists of triplets from b to all lists from a in all combinations */
    private List<List<ColoredTriplet>> cartesianProduct(
            List<List<ColoredTriplet>> a, List<ColoredVariant> b) {
        List<List<ColoredTriplet>> product = new ArrayList<>();
        for (var listFromA : a) {
            for (var andTripletsFromB : b) {
                List<ColoredTriplet> combined = new ArrayList<>(listFromA);
                combined.addAll(andTripletsFromB.getTriplets());
                product.add(combined);
            }
        }
        return product;
    }

    private List<ColoredVariant> getVariants(CustomRelation relation)
            throws MappingToUmlTranslationFailedException {
        Optional<RelationMapping> mapping = relation.getMapping();
        if (mapping.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no mapping");
        } else if (mapping.get().getWhenTriplets().isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    relation.getName() + " has no whenTriplets");
        }
        return ColoredMapping.fromMapping(mapping.get()).getVariants();
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
