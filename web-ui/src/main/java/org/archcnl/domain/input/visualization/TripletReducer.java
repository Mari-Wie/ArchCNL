package org.archcnl.domain.input.visualization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.InheritanceRelation;

public class TripletReducer {

    private static final Concept inheritance = new FamixConcept("Inheritance", "");
    private List<ColoredTriplet> whenTriplets;
    private Set<Variable> variables;

    public TripletReducer(List<ColoredTriplet> triplets, Set<Variable> variables) {
        this.whenTriplets = triplets;
        this.variables = variables;
    }

    public List<ColoredTriplet> reduce() throws MappingToUmlTranslationFailedException {
        reduceSimpleRegexTriplets();
        reduceInheritanceTriplets();
        return whenTriplets;
    }

    private void reduceSimpleRegexTriplets() throws MappingToUmlTranslationFailedException {
        Map<ObjectType, String> regexSubjectToString = buildRegexSubjectToStringMap();
        injectRegexStringsIntoCallingTriplets(regexSubjectToString);
    }

    private Map<ObjectType, String> buildRegexSubjectToStringMap()
            throws MappingToUmlTranslationFailedException {
        Map<ObjectType, String> regexSubjectToString = new HashMap<>();
        for (Triplet triplet : whenTriplets) {
            if (isRegexTriplet(triplet)) {
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

    private void injectRegexStringsIntoCallingTriplets(
            Map<ObjectType, String> regexSubjectToString) {
        for (ColoredTriplet triplet : whenTriplets) {
            ObjectType object = triplet.getObject();
            if (regexSubjectToString.containsKey(object)) {
                StringValue newObject = new StringValue(regexSubjectToString.get(object));
                triplet.setObject(newObject);
            }
        }
        whenTriplets =
                whenTriplets.stream().filter(t -> !isRegexTriplet(t)).collect(Collectors.toList());
    }

    private void reduceInheritanceTriplets() throws MappingToUmlTranslationFailedException {
        Map<Variable, InheritanceRelation> inheritanceMap = buildInheritanceMap();
        replaceInheritanceTriplets(inheritanceMap);
    }

    private Map<Variable, InheritanceRelation> buildInheritanceMap() {
        Map<Variable, InheritanceRelation> inheritanceMap = new HashMap<>();
        for (Variable variable : variables) {
            if (variable.getDynamicTypes().contains(inheritance)) {
                InheritanceRelation inheritanceRelation = new InheritanceRelation();
                inheritanceMap.put(variable, inheritanceRelation);
            }
        }
        return inheritanceMap;
    }

    private void replaceInheritanceTriplets(Map<Variable, InheritanceRelation> inheritanceMap)
            throws MappingToUmlTranslationFailedException {
        for (ColoredTriplet triplet : whenTriplets) {
            if (triplet.getPredicate().getName().equals("hasSubClass")) {
                InheritanceRelation relation = inheritanceMap.get(triplet.getSubject());
                relation.setHasSubClass((Variable) triplet.getObject());
                relation.setColorState(triplet.getColorState());
            } else if (triplet.getPredicate().getName().equals("hasSuperClass")) {
                InheritanceRelation relation = inheritanceMap.get(triplet.getSubject());
                relation.setHasSuperClass((Variable) triplet.getObject());
                relation.setColorState(triplet.getColorState());
            }
        }
        whenTriplets =
                whenTriplets.stream()
                        .filter(
                                t ->
                                        !t.getObject().equals(inheritance)
                                                && !t.getPredicate().getName().equals("hasSubClass")
                                                && !t.getPredicate()
                                                        .getName()
                                                        .equals("hasSuperClass"))
                        .collect(Collectors.toList());
        for (InheritanceRelation inheritanceRelation : inheritanceMap.values()) {
            whenTriplets.add(inheritanceRelation.buildInteritanceTriplet());
        }
    }

    private boolean isRegexTriplet(Triplet triplet) {
        return triplet.getPredicate().equals(JenaBuiltinRelation.getRegexRelation());
    }
}
