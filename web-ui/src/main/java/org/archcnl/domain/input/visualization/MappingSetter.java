package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class MappingSetter {

    private RelationManager relationManager;
    private ConceptManager conceptManager;

    public MappingSetter(RelationManager relationManager, ConceptManager conceptManager) {
        this.relationManager = relationManager;
        this.conceptManager = conceptManager;
    }

    public void setMappingsInMapping(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        setMappingsInVariants(mapping.getWhenTriplets());
        try {
            ((CustomConcept) mapping.getThenTriplet().getObject()).setMapping(mapping);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    public void setMappingsInMapping(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        setMappingsInVariants(mapping.getWhenTriplets());
        try {
            ((CustomRelation) mapping.getThenTriplet().getPredicate())
                    .setMapping(mapping, conceptManager);
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    public void setMappingsInTriplets(List<ColoredTriplet> triplets)
            throws MappingToUmlTranslationFailedException {
        for (Triplet triplet : triplets) {
            setMappingsInTriplet(triplet);
        }
    }

    private void setMappingsInVariants(List<AndTriplets> variants)
            throws MappingToUmlTranslationFailedException {
        for (AndTriplets variant : variants) {
            for (Triplet triplet : variant.getTriplets()) {
                setMappingsInTriplet(triplet);
            }
        }
    }

    private void setMappingsInTriplet(Triplet triplet)
            throws MappingToUmlTranslationFailedException {
        try {
            if (triplet.getPredicate() instanceof CustomRelation) {
                setMappingFromManager((CustomRelation) triplet.getPredicate());
            }
            if (triplet.getObject() instanceof CustomConcept) {
                setMappingFromManager((CustomConcept) triplet.getObject());
            }
        } catch (UnrelatedMappingException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private void setMappingFromManager(CustomConcept object)
            throws UnrelatedMappingException, MappingToUmlTranslationFailedException {
        var conceptFromManager = (CustomConcept) getConcept(object.getName());
        Optional<ConceptMapping> mappingOpt = conceptFromManager.getMapping();
        if (mappingOpt.isPresent()) {
            object.setMapping(mappingOpt.get());
            setMappingsInMapping(mappingOpt.get());
        }
    }

    private void setMappingFromManager(CustomRelation predicate)
            throws UnrelatedMappingException, MappingToUmlTranslationFailedException {
        var predicateFromManager = (CustomRelation) getRelation(predicate.getName());
        Optional<RelationMapping> mappingOpt = predicateFromManager.getMapping();
        if (mappingOpt.isPresent()) {
            predicate.setMapping(mappingOpt.get(), conceptManager);
            setMappingsInMapping(mappingOpt.get());
        }
    }

    private Concept getConcept(String conceptName) throws MappingToUmlTranslationFailedException {
        Optional<Concept> concept = conceptManager.getConceptByName(conceptName);
        if (concept.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(conceptName + " doesn't exist");
        }
        return concept.get();
    }

    private Relation getRelation(String relationName)
            throws MappingToUmlTranslationFailedException {
        Optional<Relation> extractedRelation = relationManager.getRelationByName(relationName);
        if (extractedRelation.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(relationName + " doesn't exist");
        }
        return extractedRelation.get();
    }
}
