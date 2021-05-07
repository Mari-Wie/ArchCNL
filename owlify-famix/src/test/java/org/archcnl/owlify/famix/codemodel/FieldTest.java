package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class FieldTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testAttribute() {

        final String name = "field";
        final Type type = new Type("double", true);
        final List<AnnotationInstance> annotations =
                Arrays.asList(new AnnotationInstance("Deprecated", new ArrayList<>()));
        final List<Modifier> modifiers = Arrays.asList(new Modifier("private"));
        Field field = new Field(name, type, annotations, modifiers);

        Individual parent =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual("namespace.SomeClass");

        field.modelIn(ontology, parent);

        Individual individual = ontology.codeModel().getIndividual("namespace.SomeClass.field");
        Individual typeIndividual =
                ontology.codeModel().getIndividual(FamixURIs.PREFIX + type.getName());
        Property definesAttribute = ontology.codeModel().getProperty(FamixURIs.DEFINES_ATTRIBUTE);
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);

        assertNotNull(individual);
        assertEquals(FamixURIs.ATTRIBUTE, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().contains(parent, definesAttribute, individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasDeclaredType, typeIndividual));
        assertNotNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));
    }
}
