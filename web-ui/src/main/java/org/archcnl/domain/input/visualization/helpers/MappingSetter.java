package org.archcnl.domain.input.visualization.helpers;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class MappingSetter {

    private MappingSetter() {}

    public static void setMappingInThenTriplet(
            RelationMapping mapping, ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        CustomRelation predicate = (CustomRelation) mapping.getThenTriplet().getPredicate();
        try {
            predicate.setMapping(mapping, conceptManager);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    public static void setMappingInThenTriplet(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();
        try {
            concept.setMapping(mapping);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    public static void setMappingsInWhenPart(
            List<AndTriplets> variants,
            RelationManager relationManager,
            ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        for (AndTriplets variant : variants) {
            for (Triplet triplet : variant.getTriplets()) {
                try {
                    setRelationMapping(relationManager, conceptManager, triplet);
                    setConceptMapping(relationManager, conceptManager, triplet);
                } catch (UnrelatedMappingException e) {
                    throw new MappingToUmlTranslationFailedException(e.getMessage());
                }
            }
        }
    }

    public static void setMappingsInTriplets(
            List<ColoredTriplet> ruleTriplets,
            RelationManager relationManager,
            ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        for (Triplet triplet : ruleTriplets) {
            try {
                setRelationMapping(relationManager, conceptManager, triplet);
                setConceptMapping(relationManager, conceptManager, triplet);
            } catch (UnrelatedMappingException e) {
                throw new MappingToUmlTranslationFailedException(e.getMessage());
            }
        }
    }

    private static void setConceptMapping(
            RelationManager relationManager, ConceptManager conceptManager, Triplet triplet)
            throws UnrelatedMappingException, MappingToUmlTranslationFailedException {
        if (triplet.getObject() instanceof CustomConcept) {
            CustomConcept object = (CustomConcept) triplet.getObject();
            String name = object.getName();
            var conceptFromManager = conceptManager.getConceptByName(name).get();
            var concept = (CustomConcept) conceptFromManager;
            Optional<ConceptMapping> mappingOpt = concept.getMapping();
            if (mappingOpt.isPresent()) {
                object.setMapping(mappingOpt.get());
                setMappingsInWhenPart(
                        mappingOpt.get().getWhenTriplets(), relationManager, conceptManager);
            }
        }
    }

    private static void setRelationMapping(
            RelationManager relationManager, ConceptManager conceptManager, Triplet triplet)
            throws UnrelatedMappingException, MappingToUmlTranslationFailedException {
        if (triplet.getPredicate() instanceof CustomRelation) {
            CustomRelation predicate = (CustomRelation) triplet.getPredicate();
            String name = predicate.getName();
            var predicateFromManager = relationManager.getRelationByName(name).get();
            var relation = (CustomRelation) predicateFromManager;
            Optional<RelationMapping> mappingOpt = relation.getMapping();
            if (mappingOpt.isPresent()) {
                predicate.setMapping(mappingOpt.get(), conceptManager);
                setMappingsInWhenPart(
                        mappingOpt.get().getWhenTriplets(), relationManager, conceptManager);
            }
        }
    }
}
