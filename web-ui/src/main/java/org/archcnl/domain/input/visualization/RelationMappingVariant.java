package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.CustomRelationConnection;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationMappingVariant extends MappingVariant {

    public RelationMappingVariant(
            AndTriplets whenVariant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {

        super(whenVariant, thenTriplet, conceptManager, usedVariables, variantName);
        pickUniqueVariables();
        buildContentParts();
    }

    @Override
    protected void buildContentParts() throws MappingToUmlTranslationFailedException {
        MappingTranslator translator = new MappingTranslator(whenTriplets, conceptManager);
        elementMap = translator.createElementMap(usedVariables);
        umlElements = translator.translateToPlantUmlModel(elementMap);

        List<String> subjectIds = elementMap.get(thenTriplet.getSubject()).getIdentifier();
        List<String> objectIds = elementMap.get(thenTriplet.getObject()).getIdentifier();
        for (String subjectId : subjectIds) {
            for (String objectId : objectIds) {
                CustomRelation relation = (CustomRelation) thenTriplet.getPredicate();
                CustomRelationConnection connection =
                        new CustomRelationConnection(subjectId, objectId, relation);
                umlElements.add(connection);
            }
        }
    }
}
