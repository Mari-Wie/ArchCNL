import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLProperty;

import api.OWLAPIImpl;
import api.OntologyAPI;

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
	public void testGetClass() {
		final String className = "Foo";
		OWLClass c = api.getOWLClass(IRI_PATH, className);
		
		assertEquals(IRI_PATH + "#" + className, c.getIRI().toString());
		
		assertTrue(c.isOWLClass());
		assertTrue(c.isClassExpressionLiteral());
		
		assertFalse(c.isAnonymous());
		assertFalse(c.isAxiom());
		assertFalse(c.isBuiltIn());
		assertFalse(c.isIndividual());
		assertFalse(c.isIRI());
		assertFalse(c.isOntology());
		assertFalse(c.isOWLAnnotationProperty());
		assertFalse(c.isOWLDataProperty());
		assertFalse(c.isOWLDatatype());
		assertFalse(c.isOWLObjectProperty());
	}

	@Test
	public void testGetObjectProperty() {
		final String roleName = "develops";
		final String roleNameLemmatized = "develop";
		OWLProperty c = api.getOWLObjectProperty(IRI_PATH, roleName);
		
		assertEquals(IRI_PATH + "#" + roleNameLemmatized, c.getIRI().toString());
		
		assertTrue(c.isOWLObjectProperty());
		
		assertFalse(c.isOWLDataProperty());
		assertFalse(c.isOWLClass());
		assertFalse(c.isAnonymous());
		assertFalse(c.isAxiom());
		assertFalse(c.isBuiltIn());
		assertFalse(c.isIndividual());
		assertFalse(c.isIRI());
		assertFalse(c.isOntology());
		assertFalse(c.isOWLAnnotationProperty());
		assertFalse(c.isOWLDatatype());
	}
	
	@Test
	public void testGetDatatypeProperty() {
		final String roleName = "develops";
		OWLProperty c = api.getOWLDatatypeProperty(IRI_PATH, roleName);
		
		assertEquals(IRI_PATH + "#" + roleName, c.getIRI().toString());
		assertTrue(c.isOWLDataProperty());
		
		assertFalse(c.isOWLObjectProperty());
		assertFalse(c.isOWLClass());
		assertFalse(c.isAnonymous());
		assertFalse(c.isAxiom());
		assertFalse(c.isBuiltIn());
		assertFalse(c.isIndividual());
		assertFalse(c.isIRI());
		assertFalse(c.isOntology());
		assertFalse(c.isOWLAnnotationProperty());
		assertFalse(c.isOWLDatatype());
	}
	
	@Test
	public void testGetTopClass() {
		OWLClassExpression c = api.getOWLTop();
		
		assertTrue(c.isOWLClass());
		assertTrue(c.isClassExpressionLiteral());
		
		assertEquals("http://www.w3.org/2002/07/owl#Thing", c.asOWLClass().getIRI().toString());
		
		assertTrue(c.asOWLClass().isBuiltIn());

		assertFalse(c.isAnonymous());
		assertFalse(c.isAxiom());
		assertFalse(c.isIndividual());
		assertFalse(c.isIRI());
		assertFalse(c.isOntology());
		assertFalse(c.asOWLClass().isOWLAnnotationProperty());
		assertFalse(c.asOWLClass().isOWLDataProperty());
		assertFalse(c.asOWLClass().isOWLDatatype());
		assertFalse(c.asOWLClass().isOWLObjectProperty());
	}
}
