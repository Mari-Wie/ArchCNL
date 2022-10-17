package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.MultipleBaseElementsException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class ConceptMappingVariant extends MappingVariant {

    public ConceptMappingVariant(
            AndTriplets whenVariant,
            Triplet thenTriplet,
            String variantName,
            ConceptManager conceptManager,
            Optional<Variable> parentSubject,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {

        super(whenVariant, thenTriplet, conceptManager, usedVariables, variantName);
        if (parentSubject.isPresent()) {
            useParentSubject(parentSubject.get());
        }
        pickUniqueVariables();
        buildContentParts();
    }

    public List<String> getIdentifiers() {
        var element = getThenSubjectBlock();
        if (element instanceof CustomConceptVisualizer) {
            return ((CustomConceptVisualizer) element).getIdentifiers();
        }
        return Arrays.asList(thenTriplet.getSubject().getName());
    }

    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        getThenSubjectBlock().setProperty(property, object);
    }

    private void useParentSubject(Variable parentSubject) {
        List<Triplet> tripletsWithParentSubject = new ArrayList<>();
        Variable thenSubject = thenTriplet.getSubject();

        for (Triplet triplet : whenTriplets) {
            Variable oldSubject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType oldObject = triplet.getObject();

            Variable newSubject = oldSubject.equals(thenSubject) ? parentSubject : oldSubject;
            ObjectType newObject = oldObject.equals(thenSubject) ? parentSubject : oldObject;
            tripletsWithParentSubject.add(new Triplet(newSubject, predicate, newObject));
        }
        whenTriplets = tripletsWithParentSubject;

        thenSubject = new Variable(parentSubject.getName());
        Relation thenPredicate = thenTriplet.getPredicate();
        ObjectType thenObject = thenTriplet.getObject();
        thenTriplet = new Triplet(thenSubject, thenPredicate, thenObject);
    }

    public PlantUmlElement getBaseElement() throws MultipleBaseElementsException {
        PlantUmlBlock thenElement = getThenSubjectBlock();
        if (thenElement instanceof CustomConceptVisualizer) {
            return ((CustomConceptVisualizer) thenElement).getBaseElement();
        }
        return (PlantUmlElement) thenElement;
    }

    private PlantUmlBlock getThenSubjectBlock() {
        return elementMap.get(thenTriplet.getSubject());
    }
}
