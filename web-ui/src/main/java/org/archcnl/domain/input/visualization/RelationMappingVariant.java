package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.CustomRelationConnection;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class RelationMappingVariant {

    private String variantName;
    private ConceptManager conceptManager;
    private Set<Variable> usedVariables;
    private Map<Variable, PlantUmlBlock> elementMap;
    private List<PlantUmlPart> umlElements;
    private List<Triplet> whenTriplets;
    private Triplet thenTriplet;

    public RelationMappingVariant(
            AndTriplets whenVariant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            Optional<Variable> parentSubject,
            Optional<Variable> parentObject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        this.variantName = variantName;
        this.conceptManager = conceptManager;
        this.usedVariables = usedVariables;
        this.whenTriplets = whenVariant.getTriplets();
        this.thenTriplet = thenTriplet;
        buildContentParts();
    }

    public String buildPlantUmlCode(boolean withBorder) {
        StringBuilder builder = new StringBuilder();
        if (withBorder) {
            builder.append("package ");
            builder.append(variantName);
            builder.append(" <<Cloud>> {\n");
        }
        builder.append(
                umlElements.stream()
                        .map(PlantUmlPart::buildPlantUmlCode)
                        .collect(Collectors.joining("\n")));
        if (withBorder) {
            builder.append("\n}");
        }
        return builder.toString();
    }

    public void buildContentParts() throws MappingToUmlTranslationFailedException {
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
