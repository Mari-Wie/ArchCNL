package org.archcnl.owlify.famix.ontology;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

public class DefinedTypeCacheTest {

    private DefinedTypeCache cache;
    private Individual type1;
    private Individual type2;

    @Before
    public void setUp() {
        cache = new DefinedTypeCache();

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntClass clazz = model.createClass("Test");

        type1 = clazz.createIndividual("Individual1");
        type2 = clazz.createIndividual("Individual2");
    }

    @Test
    public void testUnknownClass() {
        assertFalse(cache.isDefined("non.existent.Type"));
    }

    @Test
    public void testKnownClass() {
        final String name = "existent.Type";
        cache.addDefinedType(name, type1);

        assertTrue(cache.isDefined(name));
        assertSame(type1, cache.getIndividual(name));
    }

    @Test
    public void testTwoKnownClasses() {
        final String name1 = "existent.Type";
        final String name2 = "existent.another.Type";
        cache.addDefinedType(name1, type1);
        cache.addDefinedType(name2, type2);

        assertTrue(cache.isDefined(name1));
        assertTrue(cache.isDefined(name2));
        assertSame(type1, cache.getIndividual(name1));
        assertSame(type2, cache.getIndividual(name2));
    }
}
