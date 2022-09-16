package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.owlify.core.MainOntology.MainDatatypeProperties;
import org.archcnl.owlify.core.MainOntology.MainObjectProperties;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties;

public class TripletContainer {

    private List<Triplet> dataPropertyTriplets;
    private List<Triplet> objectPropertyTriplets;
    private Map<Variable, List<Triplet>> dataTripletsGroupedBySubject;

    public TripletContainer(List<Triplet> triplets) {
        setDataPropertyTriplets(triplets);
        setObjectPropertyTriplets(triplets);
        groupDataPropertyTripletsBySubject();
    }

    private void setDataPropertyTriplets(List<Triplet> triplets) {
        dataPropertyTriplets =
                triplets.stream()
                        .filter(t -> isDataProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setObjectPropertyTriplets(List<Triplet> triplets) {
        objectPropertyTriplets =
                triplets.stream()
                        .filter(t -> isObjectProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void groupDataPropertyTripletsBySubject() {
        dataTripletsGroupedBySubject = new HashMap<>();
        for (Triplet triplet : dataPropertyTriplets) {
            Variable key = triplet.getSubject();
            dataTripletsGroupedBySubject.putIfAbsent(key, new ArrayList<>());
            dataTripletsGroupedBySubject.get(key).add(triplet);
        }
    }

    private boolean isDataProperty(Relation relation) {
        String name = relation.getName();
        boolean isMainDataProperty = EnumUtils.isValidEnum(MainDatatypeProperties.class, name);
        boolean isFamixDataProperty = EnumUtils.isValidEnum(FamixDatatypeProperties.class, name);
        return isMainDataProperty || isFamixDataProperty;
    }

    private boolean isObjectProperty(Relation relation) {
        String name = relation.getName();
        boolean isMainObjectProperty = EnumUtils.isValidEnum(MainObjectProperties.class, name);
        boolean isFamixObjectProperty = EnumUtils.isValidEnum(FamixObjectProperties.class, name);
        return isMainObjectProperty || isFamixObjectProperty;
    }
}
