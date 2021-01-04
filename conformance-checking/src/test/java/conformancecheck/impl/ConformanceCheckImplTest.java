package conformancecheck.impl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;

import conformancecheck.api.IConformanceCheck;
import conformancecheck.api.CheckedRule;
import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;
import datatypes.ConstraintViolation.ConstraintViolationBuilder;
import datatypes.RuleType;

public class ConformanceCheckImplTest {
	@Test
	public void testCoarse() throws FileNotFoundException {
		// setup:
		IConformanceCheck check = new ConformanceCheckImpl();
		check.createNewConformanceCheck();
		
		ArchitectureRule rule0 = new ArchitectureRule(0, "Only LayerOne can use LayerTwo.", RuleType.DOMAIN_RANGE, "architecture0.owl");
		ArchitectureRule rule1 = new ArchitectureRule(1, "No LayerTwo can use LayerOne.", RuleType.NEGATION, "architecture1.owl");
		
		List<ConstraintViolation> violations = new ArrayList<>();
		ConstraintViolationBuilder violation = new ConstraintViolationBuilder();
		violation.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1");
		violation.addViolation(
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://www.w3.org/2000/01/rdf-schema#domain", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerOne");
		violation.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0");
		violation.addViolation(
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://www.w3.org/2000/01/rdf-schema#range", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerTwo");
		violation.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0");
		violations.add(violation.build());
		
		ConstraintViolationBuilder violation2 = new ConstraintViolationBuilder();
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0");
		violation2.addViolation(
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://www.w3.org/2000/01/rdf-schema#domain", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerOne");
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1");
		violation2.addViolation(
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://www.w3.org/2000/01/rdf-schema#range", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerTwo");
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1");
		violations.add(violation2.build());
		
		CheckedRule r0 = new CheckedRule(rule0, new ArrayList<>());
		CheckedRule r1 = new CheckedRule(rule1, violations);
		
		// validate the results for the 1st rule:
		final String outputPath0 = "src/test/resources/check0.owl";
		check.validateRule(r0, "./src/test/resources/tmp0.owl", outputPath0);
		
		OntModel expected0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected0.read(new FileReader("./src/test/resources/check0-expected.owl"), "");
		
		OntModel actual0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual0.read(new FileReader(outputPath0), "");
		
		// workaround: remove the checking date
		expected0.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(expected0), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(expected0))
		.remove();
		actual0.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(actual0), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(actual0)).
		remove();
		
		assertTrue(expected0.isIsomorphicWith(actual0));
		
		// 2nd rule:
		final String outputPath1 = "src/test/resources/check1.owl";
		check.validateRule(r1, "./src/test/resources/tmp1.owl", outputPath1);
		
		OntModel expected1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected1.read(new FileReader("./src/test/resources/check1-expected.owl"), "");
		
		OntModel actual1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual1.read(new FileReader(outputPath1), "");
		
		// workaround: remove the checking date
		expected1.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(expected1), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(expected1))
		.remove();
		actual1.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(actual1), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(actual1))
		.remove();
		// remove it twice, because there are two statements: one originates from the input file (tmp1.owl),
		// the other one is generated by the check object. Under normal conditions, both statements would be
		// equivalent and thus the duplicate would not occur.
		actual1.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(actual1), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(actual1))
		.remove();
		
		assertTrue(expected1.isIsomorphicWith(actual1));
	}
}
