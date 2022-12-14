package org.archcnl.domain.input.visualization.visualizers.mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.MappingTranslator;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.diagram.PlantUmlBlock;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;

public abstract class MappingVariant {

    protected List<ColoredTriplet> whenTriplets;
    protected Triplet thenTriplet;
    protected ConceptManager conceptManager;
    private RelationManager relationManager;
    protected Set<Variable> usedVariables;
    private String variantName;

    protected Map<Variable, PlantUmlBlock> elementMap;
    protected List<PlantUmlPart> umlElements;

    protected MappingVariant(
            List<ColoredTriplet> whenTriplets,
            Triplet thenTriplet,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables,
            String variantName) {
        this.whenTriplets = whenTriplets;
        this.thenTriplet = thenTriplet;
        this.conceptManager = conceptManager;
        this.usedVariables = usedVariables;
        this.variantName = variantName;
        this.relationManager = relationManager;
    }

    public String buildPlantUmlCode(boolean withBorder) {
        String content =
                umlElements.stream()
                        .map(PlantUmlPart::buildPlantUmlCode)
                        .collect(Collectors.joining("\n"));

        return withBorder ? surroundWithBorder(content) : content;
    }

    protected void pickUniqueVariables() {
        Map<Variable, Variable> renamedVariables = new HashMap<>();
        List<ColoredTriplet> modifiedTriplets = new ArrayList<>();
        for (ColoredTriplet triplet : whenTriplets) {
            Variable subject =
                    NamePicker.pickUniqueVariable(
                            usedVariables, renamedVariables, triplet.getSubject());

            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                Variable objectVar = (Variable) object;
                object = NamePicker.pickUniqueVariable(usedVariables, renamedVariables, objectVar);
            }

            Relation predicate = triplet.getPredicate();
            ColoredTriplet newTriplet = new ColoredTriplet(subject, predicate, object);
            newTriplet.setColorState(triplet.getColorState());
            newTriplet.setCardinality(triplet.getCardinality());
            newTriplet.setQuantity(triplet.getQuantity());
            modifiedTriplets.add(newTriplet);
        }
        whenTriplets = modifiedTriplets;
        updateVariablesInThenTriplet(renamedVariables);
    }

    private void updateVariablesInThenTriplet(Map<Variable, Variable> renamedVariables) {
        Variable thenSubject = thenTriplet.getSubject();
        ObjectType thenObject = thenTriplet.getObject();
        if (renamedVariables.containsKey(thenSubject)) {
            thenSubject = renamedVariables.get(thenSubject);
        }
        if (renamedVariables.containsKey(thenObject)) {
            thenObject = renamedVariables.get(thenObject);
        }
        thenTriplet = new Triplet(thenSubject, thenTriplet.getPredicate(), thenObject);
    }

    private String surroundWithBorder(String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("package ");
        builder.append(variantName);
        builder.append(" <<Cloud>> {\n");
        builder.append(content);
        builder.append("\n}");
        return builder.toString();
    }

    protected void buildContentParts() throws MappingToUmlTranslationFailedException {
        MappingTranslator translator =
                new MappingTranslator(whenTriplets, conceptManager, relationManager);
        elementMap = translator.createElementMap(usedVariables);
        umlElements = translator.translateToPlantUmlModel(elementMap);
    }
}
