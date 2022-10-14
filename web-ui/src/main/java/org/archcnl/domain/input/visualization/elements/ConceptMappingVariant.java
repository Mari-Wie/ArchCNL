package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.MappingTranslator;
import org.archcnl.domain.input.visualization.PlantUmlBlock;
import org.archcnl.domain.input.visualization.PlantUmlPart;
import org.archcnl.domain.input.visualization.UniqueNamePicker;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.MultipleBaseElementsException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class ConceptMappingVariant {

    private List<Triplet> whenTriplets;
    private final String variantName;
    private Variable thenSubject;
    private ConceptManager conceptManager;
    private Set<Variable> usedVariables;

    private Map<Variable, PlantUmlBlock> elementMap;
    private List<PlantUmlPart> umlElements;

    public ConceptMappingVariant(
            AndTriplets andTriplets,
            Variable thenSubject,
            String variantName,
            ConceptManager conceptManager,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        this.variantName = variantName;
        this.conceptManager = conceptManager;
        this.usedVariables = usedVariables;

        if (parentSubject.isEmpty()) {
            this.whenTriplets = andTriplets.getTriplets();
            this.thenSubject = thenSubject;
        } else {
            Variable parentSubjectCopy = new Variable(parentSubject.get().getName());
            this.whenTriplets =
                    useParentSubject(thenSubject, parentSubjectCopy, andTriplets.getTriplets());
            this.thenSubject = parentSubjectCopy;
        }

        pickUniqueVariables(usedVariables);
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
    }

    public String getIdentifier() {
        var element = elementMap.get(thenSubject);
        if (element instanceof CustomConceptVisualizer) {
            return ((CustomConceptVisualizer) element).getIdentifier().get(0);
        }
        return thenSubject.getName();
    }

    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        elementMap.get(thenSubject).setProperty(property, object);
    }

    private void pickUniqueVariables(Set<Variable> usedVariables) {
        Map<Variable, Variable> renamedVariables = new HashMap<>();
        List<Triplet> modifiedTriplets = new ArrayList<>();
        for (Triplet triplet : whenTriplets) {
            Variable oldSubject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType oldObject = triplet.getObject();

            Variable newSubject =
                    UniqueNamePicker.pickUniqueVariable(
                            usedVariables, renamedVariables, oldSubject);
            ObjectType newObject = null;
            if (oldObject instanceof Variable) {
                newObject =
                        UniqueNamePicker.pickUniqueVariable(
                                usedVariables, renamedVariables, (Variable) oldObject);
            } else {
                newObject = oldObject;
            }
            modifiedTriplets.add(new Triplet(newSubject, predicate, newObject));
        }
        whenTriplets = modifiedTriplets;

        if (renamedVariables.containsKey(thenSubject)) {
            thenSubject = renamedVariables.get(thenSubject);
        }
    }

    private List<Triplet> useParentSubject(
            Variable thenSubject, Variable parentSubject, List<Triplet> triplets) {
        List<Triplet> tripletsWithParentSubject = new ArrayList<>();
        for (Triplet triplet : triplets) {
            Variable oldSubject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType oldObject = triplet.getObject();

            Variable newSubject = oldSubject.equals(thenSubject) ? parentSubject : oldSubject;
            ObjectType newObject = oldObject.equals(thenSubject) ? parentSubject : oldObject;
            tripletsWithParentSubject.add(new Triplet(newSubject, predicate, newObject));
        }
        return tripletsWithParentSubject;
    }

    public PlantUmlElement getBaseElement() throws MultipleBaseElementsException {
        PlantUmlBlock thenElement = elementMap.get(thenSubject);
        if (thenElement instanceof CustomConceptVisualizer) {
            return ((CustomConceptVisualizer) thenElement).getBaseElement();
        }
        return (PlantUmlElement) thenElement;
    }
}
