package ontology;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Before;
import org.junit.Test;

public class FamixOntologyTest {

	private FamixOntology ontology;

	@Before
	public void initialize() {
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		ontology = new FamixOntology(famixOntologyInputStream);
	}

	@Test
	public void testFamixNamedEntityIndividualHasNameSet() {
		Individual individual = ontology.getFamixClassWithName("TestClass");
		ontology.setHasNamePropertyForNamedEntity("TestClass", individual);
		DatatypeProperty nameProperty = ontology.getHasNameProperty();
		RDFNode propertyValue = individual.getPropertyValue(nameProperty);
		String name = propertyValue.asLiteral().getString();
		assertEquals("TestClass", name);
	}

	@Test
	public void testFamixClassIndividualHasIsInterfaceSet() {
		Individual individual = ontology.getFamixClassWithName("TestClass");
		ontology.setIsInterfaceForFamixClass(true, individual);
		DatatypeProperty isInterfaceProperty = ontology.getIsInterfaceProperty();
		RDFNode propertyValue = individual.getPropertyValue(isInterfaceProperty);
		boolean isInterface = propertyValue.asLiteral().getBoolean();
		assertEquals(true, isInterface);
	}

	@Test
	public void testFamixClassIndividualHasModifiersSet() {

		Individual individual = ontology.getFamixClassWithName("TestClass");
		ontology.setHasModifierForNamedEntity("public", individual);
		ontology.setHasModifierForNamedEntity("static", individual);
		DatatypeProperty hasModifierProperty = ontology.getHasModifierProperty();
		NodeIterator propertyValues = individual.listPropertyValues(hasModifierProperty);
		List<String> result = new ArrayList<String>();
		
		while(propertyValues.hasNext()) {
			result.add(propertyValues.next().asLiteral().getString());
		}
		
		assertThat(result, containsInAnyOrder("public", "static"));

	}
	
	@Test
	public void testFamixClassIndividualHasAnnotationSet() {

		Individual individual = ontology.getFamixClassWithName("TestClass");
		Individual annotationIndividual = ontology.getAnnotationTypeIndividualWithName("TestAnnotation");
		ontology.setHasAnnotationInstanceForEntity(annotationIndividual, individual);
		ObjectProperty hasModifierProperty = ontology.getHasAnnotationProperty();
		RDFNode propertyValue = individual.getPropertyValue(hasModifierProperty);
		assertEquals(annotationIndividual, propertyValue);

	}
	
	@Test
	public void testInheritanceHasSubclassAndSuperclassSet() {

		Individual subClass = ontology.getFamixClassWithName("TestClass");
		Individual superClass = ontology.getFamixClassWithName("TestClass2");
		Individual inheritance = ontology.getInheritanceBetweenSubClassAndSuperClass(subClass, superClass);
		ObjectProperty subClassProperty = ontology.getSubClassProperty();
		ObjectProperty superClassProperty = ontology.getSuperClassProperty();
		RDFNode subValue = inheritance.getPropertyValue(subClassProperty);
		RDFNode superValue = inheritance.getPropertyValue(superClassProperty);
		
		assertEquals(subClass, subValue);
		assertEquals(superClass, superValue);
	}

}
