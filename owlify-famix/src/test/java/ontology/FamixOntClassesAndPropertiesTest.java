package ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.jena.ontology.Individual;
//import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

public class FamixOntClassesAndPropertiesTest {

	private FamixOntClassesAndProperties classesAndProperties;
	private OntModel model;
	
	@Before
	public void intialize() {
		classesAndProperties = new FamixOntClassesAndProperties();
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read("./ontology/famix.owl");
	}
	
	@Test
	public void testIndividualForFamixClassIsNotNull() {
		Individual individual = classesAndProperties.getFamixClassIndividual(model, "Test", 0);
		assertNotNull(individual);
	}
	
	@Test
	public void testReturnsCorrespondingOntClassForFamixClassIdentifier() {
		Individual individual = classesAndProperties.getFamixClassIndividual(model, "Test", 0);
		assertEquals("FamixClass", individual.getOntClass().getLocalName());
	}
	
	@Test
	public void testIndividualForAnnotationTypeIsNotNull() {
		Individual individual = classesAndProperties.getAnnotationTypeIndividual(model, "Test", 0);
		assertNotNull(individual);
	}
	
	@Test
	public void testReturnsCorrespondingOntClassForAnnotationIdentifier() {
		Individual individual = classesAndProperties.getAnnotationTypeIndividual(model, "Test", 0);
		assertEquals("AnnotationType", individual.getOntClass().getLocalName());
	}
	
	@Test
	public void testIndividualForEnumTypeIsNotNull() {
		Individual individual = classesAndProperties.getEnumTypeIndividual(model, "Test", 0);
		assertNotNull(individual);
	}
	
	@Test
	public void testReturnsCorrespondingOntClassForEnumIdentifier() {
		Individual individual = classesAndProperties.getEnumTypeIndividual(model, "Test", 0);
		assertEquals("Enum", individual.getOntClass().getLocalName());
	}

}
