package org.archcnl.owlcreator.impl;

import org.archcnl.owlcreator.api.OntologyAPI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLProperty;

public class OWLAPIImplTest {

    private OntologyAPI api;
    final String IRI_PATH = "http://test";
    final String FILE_PATH = "./src/test/resources/ontology.owl";

    // Only a few methods with suitable testability are tested.
    // The other methods are not, because OWLAPIImpl is just a
    // simple wrapper of some calls to the external library
    // org.semanticweb.owlapi which is assumed to work correctly.

    @Before
    public void setUp() throws Exception {
        api = new OWLAPIImpl();
    }

    @Test
    public void givenName_whenCreatingOWLClass_thenReturnValidOWLClassWithNameAsEndingOfIRI() {
        // given
        final String className = "Foo";
        // when
        OWLClass c = api.createOWLClass(IRI_PATH, className);
        // then
        Assert.assertEquals(IRI_PATH + "#" + className, c.getIRI().toString());

        Assert.assertTrue(c.isOWLClass());
        Assert.assertTrue(c.isClassExpressionLiteral());

        Assert.assertFalse(c.isAnonymous());
        Assert.assertFalse(c.isAxiom());
        Assert.assertFalse(c.isBuiltIn());
        Assert.assertFalse(c.isIndividual());
        Assert.assertFalse(c.isIRI());
        Assert.assertFalse(c.isOntology());
        Assert.assertFalse(c.isOWLAnnotationProperty());
        Assert.assertFalse(c.isOWLDataProperty());
        Assert.assertFalse(c.isOWLDatatype());
        Assert.assertFalse(c.isOWLObjectProperty());
    }

    @Test
    public void
            givenName_whenCreatingOWLObjectProperty_thenReturnValidOWLObjectPropertyWithNameAsEndingOfIRI() {
        // given
        final String roleName = "develops";
        // when
        OWLProperty c = api.createOWLObjectProperty(IRI_PATH, roleName);
        // then
        Assert.assertEquals(IRI_PATH + "#" + roleName, c.getIRI().toString());

        Assert.assertTrue(c.isOWLObjectProperty());

        Assert.assertFalse(c.isOWLDataProperty());
        Assert.assertFalse(c.isOWLClass());
        Assert.assertFalse(c.isAnonymous());
        Assert.assertFalse(c.isAxiom());
        Assert.assertFalse(c.isBuiltIn());
        Assert.assertFalse(c.isIndividual());
        Assert.assertFalse(c.isIRI());
        Assert.assertFalse(c.isOntology());
        Assert.assertFalse(c.isOWLAnnotationProperty());
        Assert.assertFalse(c.isOWLDatatype());
    }

    @Test
    public void
            givenName_whenCreatingOWLDatatypeProperty_thenReturnValidOWLDatatypePropertyWithNameAsEndingOfIRI() {
        final String roleName = "develops";
        OWLProperty c = api.createOWLDatatypeProperty(IRI_PATH, roleName);

        Assert.assertEquals(IRI_PATH + "#" + roleName, c.getIRI().toString());
        Assert.assertTrue(c.isOWLDataProperty());

        Assert.assertFalse(c.isOWLObjectProperty());
        Assert.assertFalse(c.isOWLClass());
        Assert.assertFalse(c.isAnonymous());
        Assert.assertFalse(c.isAxiom());
        Assert.assertFalse(c.isBuiltIn());
        Assert.assertFalse(c.isIndividual());
        Assert.assertFalse(c.isIRI());
        Assert.assertFalse(c.isOntology());
        Assert.assertFalse(c.isOWLAnnotationProperty());
        Assert.assertFalse(c.isOWLDatatype());
    }

    @Test
    public void whenRetrievingTopmostOWLClass_thenReturnTheOWLClassThing() {
        // given, when
        OWLClassExpression c = api.getOWLTop();
        // then
        Assert.assertTrue(c.isOWLClass());
        Assert.assertTrue(c.isClassExpressionLiteral());

        Assert.assertEquals(
                "http://www.w3.org/2002/07/owl#Thing", c.asOWLClass().getIRI().toString());

        Assert.assertTrue(c.asOWLClass().isBuiltIn());

        Assert.assertFalse(c.isAnonymous());
        Assert.assertFalse(c.isAxiom());
        Assert.assertFalse(c.isIndividual());
        Assert.assertFalse(c.isIRI());
        Assert.assertFalse(c.isOntology());
        Assert.assertFalse(c.asOWLClass().isOWLAnnotationProperty());
        Assert.assertFalse(c.asOWLClass().isOWLDataProperty());
        Assert.assertFalse(c.asOWLClass().isOWLDatatype());
        Assert.assertFalse(c.asOWLClass().isOWLObjectProperty());
    }
}
