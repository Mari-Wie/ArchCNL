package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Attribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class FieldTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testAttributeOwlTransformation() {
        final String parentName = "namespace.SomeClass";
        final String name = "field";
        final Type type = new Type("double", "double", true);
        final List<AnnotationInstance> annotations =
                Arrays.asList(new AnnotationInstance("Deprecated", new ArrayList<>()));
        final List<Modifier> modifiers = Arrays.asList(new Modifier("private"));
        Field field = new Field(name, type, annotations, modifiers);

        Individual parent = ontology.createIndividual(FamixClass, parentName);

        field.modelIn(ontology, parentName, parent);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(Attribute.individualUri("namespace.SomeClass.field"));

        assertNotNull(individual);
        assertEquals(Attribute.uri(), individual.getOntClass().getURI());
        assertTrue(
                ontology.codeModel().contains(parent, ontology.get(definesAttribute), individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual,
                                ontology.get(hasDeclaredType),
                                type.getIndividual(ontology)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }
}
