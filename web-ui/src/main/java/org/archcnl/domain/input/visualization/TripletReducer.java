package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.InheritanceRelation;

public class TripletReducer {

    private List<Triplet> triplets;
    private Map<Variable, Set<ActualObjectType>> variableTypesMap;

    public TripletReducer(
            List<Triplet> triplets, Map<Variable, Set<ActualObjectType>> variableTypesMap) {
        this.triplets = triplets;
        this.variableTypesMap = variableTypesMap;
    }

    public List<Triplet> reduce() throws MappingToUmlTranslationFailedException {
        reduceSimpleRegexTriplets();
        reduceInheritanceTriplets();
        return triplets;
    }

    private void reduceSimpleRegexTriplets() throws MappingToUmlTranslationFailedException {
        Map<ObjectType, String> regexSubjectToString = buildRegexSubjectToStringMap();
        triplets = injectRegexStringsIntoCallingTriplets(regexSubjectToString);
    }

    private Map<ObjectType, String> buildRegexSubjectToStringMap()
            throws MappingToUmlTranslationFailedException {
        Map<ObjectType, String> regexSubjectToString = new HashMap<>();
        for (Triplet triplet : triplets) {
            if (triplet.getPredicate().equals(JenaBuiltinRelation.getRegexRelation())) {
                if (!(triplet.getObject() instanceof StringValue)) {
                    throw new MappingToUmlTranslationFailedException(
                            "Complex regex relations can't be translated: "
                                    + triplet.transformToAdoc());
                }
                String object = ((StringValue) triplet.getObject()).getValue();
                regexSubjectToString.put(triplet.getSubject(), object);
            }
        }
        return regexSubjectToString;
    }

    private List<Triplet> injectRegexStringsIntoCallingTriplets(
            Map<ObjectType, String> regexSubjectToString) {
        List<Triplet> tripletsWithoutRegex = new ArrayList<>();
        for (Triplet triplet : triplets) {
            ObjectType object = triplet.getObject();
            if (regexSubjectToString.containsKey(object)) {
                StringValue newObject = new StringValue(regexSubjectToString.get(object));
                Triplet newTriplet =
                        new Triplet(triplet.getSubject(), triplet.getPredicate(), newObject);
                tripletsWithoutRegex.add(newTriplet);
            }
        }
        return tripletsWithoutRegex;
    }

    private void reduceInheritanceTriplets() throws MappingToUmlTranslationFailedException {
        Map<Variable, InheritanceRelation> inheritanceMap = buildInheritanceMap();
        triplets = replaceInheritanceTriplets(inheritanceMap);
    }

    private Map<Variable, InheritanceRelation> buildInheritanceMap() {
        Concept inheritance = new FamixConcept("Inheritance", "");
        Map<Variable, InheritanceRelation> inheritanceMap = new HashMap<>();
        for (Entry<Variable, Set<ActualObjectType>> entry : variableTypesMap.entrySet()) {
            if (entry.getValue().contains(inheritance)) {
                InheritanceRelation inheritanceRelation = new InheritanceRelation();
                inheritanceMap.put(entry.getKey(), inheritanceRelation);
            }
        }
        return inheritanceMap;
    }

    private List<Triplet> replaceInheritanceTriplets(
            Map<Variable, InheritanceRelation> inheritanceMap)
            throws MappingToUmlTranslationFailedException {
        List<Triplet> newTriplets = new ArrayList<>();
        for (Triplet triplet : triplets) {
            if (triplet.getPredicate().getName().equals("hasSubClass")) {
                inheritanceMap
                        .get(triplet.getSubject())
                        .setHasSubClass((Variable) triplet.getObject());
            } else if (triplet.getPredicate().getName().equals("hasSuperClass")) {
                inheritanceMap
                        .get(triplet.getSubject())
                        .setHasSuperClass((Variable) triplet.getObject());
            } else {
                newTriplets.add(triplet);
            }
        }
        for (InheritanceRelation inheritanceRelation : inheritanceMap.values()) {
            newTriplets.add(inheritanceRelation.buildInteritanceTriplet());
        }
        return newTriplets;
    }
}
