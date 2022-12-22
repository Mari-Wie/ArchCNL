package org.archcnl.domain.input.visualization.visualizers.mappings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.diagram.PlantUmlBlock;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.diagram.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class ConceptVariant extends MappingVariant {

    public ConceptVariant(
            ColoredVariant variant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {

        super(
                variant.getTriplets(),
                thenTriplet,
                conceptManager,
                relationManager,
                usedVariables,
                variantName);
        if (parentSubject.isPresent()) {
            useParentSubject(parentSubject.get());
        }
        pickUniqueVariables();
        buildContentParts();
    }

    public List<String> getIdentifiers() {
        PlantUmlBlock element = getThenSubjectBlock();
        if (element instanceof ConceptVisualizer) {
            return ((ConceptVisualizer) element).getIdentifiers();
        }
        return elementMap.get(thenTriplet.getSubject()).getIdentifiers();
    }

    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        getThenSubjectBlock().setProperty(property, object);
    }

    public void removeUmlElementsWithParents() {
        List<PlantUmlPart> filteredElements = new ArrayList<>();
        for (PlantUmlPart part : umlElements) {
            if (part instanceof PlantUmlBlock) {
                PlantUmlBlock block = (PlantUmlBlock) part;
                if (!block.hasParentBeenFound()) {
                    filteredElements.add(part);
                }
            } else {
                filteredElements.add(part);
            }
        }
        umlElements = filteredElements;
    }

    public List<PlantUmlElement> getBaseElements() {
        PlantUmlBlock thenElement = getThenSubjectBlock();
        if (thenElement instanceof ConceptVisualizer) {
            return ((ConceptVisualizer) thenElement).getBaseElements();
        }
        return Arrays.asList((PlantUmlElement) thenElement);
    }

    private void useParentSubject(Variable parentSubject) {
        Variable thenSubject = thenTriplet.getSubject();

        for (ColoredTriplet triplet : whenTriplets) {
            Variable oldSubject = triplet.getSubject();
            ObjectType oldObject = triplet.getObject();

            Variable newSubject = oldSubject.equals(thenSubject) ? parentSubject : oldSubject;
            ObjectType newObject = oldObject.equals(thenSubject) ? parentSubject : oldObject;
            triplet.setSubject(newSubject);
            triplet.setObject(newObject);
        }

        thenSubject = new Variable(parentSubject.getName());
        Relation thenPredicate = thenTriplet.getPredicate();
        ObjectType thenObject = thenTriplet.getObject();
        thenTriplet = new Triplet(thenSubject, thenPredicate, thenObject);
    }

    private PlantUmlBlock getThenSubjectBlock() {
        return elementMap.get(thenTriplet.getSubject());
    }
}
