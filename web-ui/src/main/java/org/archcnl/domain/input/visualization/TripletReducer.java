package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class TripletReducer {

    private List<Triplet> triplets;

    public TripletReducer(List<Triplet> triplets) {
        this.triplets = triplets;
    }

    public List<Triplet> reduce() throws MappingToUmlTranslationFailedException {
        triplets = reduceSimpleRegexTriplets(triplets);
        return triplets;
    }

    private List<Triplet> reduceSimpleRegexTriplets(List<Triplet> triplets)
            throws MappingToUmlTranslationFailedException {
        Map<ObjectType, String> regexSubjectToString = buildRegexSubjectToStringMap(triplets);
        return injectRegexStringsIntoCallingTriplets(triplets, regexSubjectToString);
    }

    private Map<ObjectType, String> buildRegexSubjectToStringMap(List<Triplet> triplets)
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
            List<Triplet> triplets, Map<ObjectType, String> regexSubjectToString) {
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
}
