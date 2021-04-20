package org.archcnl.owlify.famix.ontology;

import java.util.HashMap;
import java.util.Map;
import org.apache.jena.ontology.Individual;

public class AnnotationAttributeCache {

    private Map<String, Map<String, Individual>> cache;

    public AnnotationAttributeCache() {
        cache = new HashMap<>();
    }

    public boolean isKnownAttribute(String fullyQualifiedName, String attributeName) {
        return cache.containsKey(fullyQualifiedName)
                && cache.get(fullyQualifiedName).containsKey(attributeName);
    }

    public void addAnnotationAttribute(
            String fullyQualifiedName, String attribute, Individual attributeIndividual) {
        assert !isKnownAttribute(fullyQualifiedName, attribute);

        if (!cache.containsKey(fullyQualifiedName)) {
            cache.put(fullyQualifiedName, new HashMap<>());
        }

        cache.get(fullyQualifiedName).put(attribute, attributeIndividual);
    }

    public Individual getAnnotationAttribute(String fullyQualifiedName, String attribute) {
        assert isKnownAttribute(fullyQualifiedName, attribute);

        return cache.get(fullyQualifiedName).get(attribute);
    }
}
