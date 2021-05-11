package org.archcnl.owlify.famix.ontology;

import java.util.HashMap;
import java.util.Map;
import org.apache.jena.ontology.Individual;

public class DefinedTypeCache {

    private Map<String, Individual> types;

    public DefinedTypeCache() {
        types = new HashMap<>();
    }

    public void addDefinedType(String fullyQualifiedName, Individual typeIndividual) {

        assert (!isDefined(fullyQualifiedName));

        types.put(fullyQualifiedName, typeIndividual);
    }

    public Individual getIndividual(String fullyQualifiedName) {

        assert (isDefined(fullyQualifiedName));

        return types.getOrDefault(fullyQualifiedName, null);
    }

    public boolean isDefined(String fullyQualifiedName) {
        return types.containsKey(fullyQualifiedName);
    }
}
