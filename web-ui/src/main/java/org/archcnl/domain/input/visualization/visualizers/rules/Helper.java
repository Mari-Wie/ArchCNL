package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;

public class Helper {

    private Helper() {}

    public static Relation getRelation(String relationName, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        Optional<Relation> extractedRelation = relationManager.getRelationByName(relationName);
        if (extractedRelation.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(relationName + " doesn't exist");
        }
        return extractedRelation.get();
    }

    public static Concept getConcept(String conceptName, ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        Optional<Concept> concept = conceptManager.getConceptByName(conceptName);
        if (concept.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(conceptName + " doesn't exist");
        }
        return concept.get();
    }

    public static void tryToFindMatch(Matcher matcher)
            throws MappingToUmlTranslationFailedException {
        boolean found = matcher.matches();
        if (!found) {
            throw new MappingToUmlTranslationFailedException("No match found");
        }
    }

    public static ColoredTriplet getTripletWithBaseType(
            Triplet triplet, ConceptManager conceptManager, Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        ActualObjectType subjectConcept = getConcept(triplet.getObject().getName(), conceptManager);
        if (subjectConcept instanceof CustomConcept) {
            CustomConcept concept = (CustomConcept) subjectConcept;
            Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
            subjectConcept = baseTypes.iterator().next();
        }
        Relation typeRelation = TypeRelation.getTyperelation();
        String name = NamePicker.getStringWithFirstLetterInLowerCase(subjectConcept.getName());
        Variable nameVariable =
                NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), new Variable(name));
        return new ColoredTriplet(nameVariable, typeRelation, subjectConcept);
    }

    public static ColoredTriplet getBaseSubjectTypeTriplet(
            Relation relation, Variable subject, Set<Variable> usedVariables) {
        subject = NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
        var objectType = relation.getRelatableSubjectTypes().iterator().next();
        return new ColoredTriplet(subject, TypeRelation.getTyperelation(), objectType);
    }

    public static ColoredTriplet getBaseObjectTypeTriplet(
            Relation relation, Variable subject, Set<Variable> usedVariables) {
        subject = NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
        var objectType = relation.getRelatableObjectTypes().iterator().next();
        return new ColoredTriplet(subject, TypeRelation.getTyperelation(), objectType);
    }
}
