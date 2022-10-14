package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;

public abstract class MappingVariant {

    protected List<Triplet> whenTriplets;
    protected Triplet thenTriplet;
    protected ConceptManager conceptManager;
    protected Set<Variable> usedVariables;
    private String variantName;

    protected Map<Variable, PlantUmlBlock> elementMap;
    protected List<PlantUmlPart> umlElements;

    protected MappingVariant(
            AndTriplets whenVariant,
            Triplet thenTriplet,
            ConceptManager conceptManager,
            Set<Variable> usedVariables,
            String variantName)
            throws MappingToUmlTranslationFailedException {
        this.whenTriplets = whenVariant.getTriplets();
        this.thenTriplet = thenTriplet;
        this.conceptManager = conceptManager;
        this.usedVariables = usedVariables;
        this.variantName = variantName;
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
        List<Triplet> modifiedTriplets = new ArrayList<>();

        for (Triplet triplet : whenTriplets) {
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

    protected abstract void buildContentParts() throws MappingToUmlTranslationFailedException;
}
