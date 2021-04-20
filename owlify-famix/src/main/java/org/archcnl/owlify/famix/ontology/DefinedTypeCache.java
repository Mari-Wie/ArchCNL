package org.archcnl.owlify.famix.ontology;

import java.util.HashMap;
import java.util.Map;
import org.apache.jena.ontology.Individual;

public class DefinedTypeCache {

    private Map<String, Individual> types;

    public DefinedTypeCache() {
        types = new HashMap<>();
    }

    public void addUserDefinedType(String fullyQualifiedName, Individual typeIndividual) {

        assert (!isUserDefined(fullyQualifiedName));

        types.put(fullyQualifiedName, typeIndividual);
    }

    public Individual getIndividual(String fullyQualifiedName) {

        assert (isUserDefined(fullyQualifiedName));

        return types.getOrDefault(fullyQualifiedName, null);
    }

    public boolean isUserDefined(String fullyQualifiedName) {
        return types.containsKey(fullyQualifiedName);
    }
}
