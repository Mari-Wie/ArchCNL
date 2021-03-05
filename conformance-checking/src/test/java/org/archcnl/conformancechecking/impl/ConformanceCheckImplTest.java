package org.archcnl.conformancechecking.impl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.ConstraintViolation;
import org.archcnl.common.datatypes.RuleType;
import org.archcnl.common.datatypes.ConstraintViolation.ConstraintViolationBuilder;
import org.archcnl.conformancechecking.api.CheckedRule;
import org.archcnl.conformancechecking.api.IConformanceCheck;
import org.archcnl.conformancechecking.impl.ConformanceCheckImpl;
import org.archcnl.conformancechecking.impl.ConformanceCheckOntologyClassesAndProperties;
import org.junit.Test;

public class ConformanceCheckImplTest {
	@Test
	public void testCoarse() throws FileNotFoundException {
		// setup:
		IConformanceCheck check = new ConformanceCheckImpl();
		check.createNewConformanceCheck();
		
		ArchitectureRule rule0 = new ArchitectureRule(0, "Only LayerOne can use LayerTwo.", RuleType.DOMAIN_RANGE, "architecture0.owl");
		ArchitectureRule rule1 = new ArchitectureRule(1, "No LayerTwo can use LayerOne.", RuleType.NEGATION, "architecture1.owl");
		
		List<ConstraintViolation> violations1 = new ArrayList<>();
		ConstraintViolationBuilder violation = new ConstraintViolationBuilder();
		violation.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0");
		
		
		violation.addNotInferredStatement(
		        "http://arch-ont.org/ontologies/famix.owl#TestLayerOne0", 
		        "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", 
		        "http://www.arch-ont.org/ontologies/architecture.owl#LayerTwo");
		violations1.add(violation.build());

		List<ConstraintViolation> violations2 = new ArrayList<>();
		ConstraintViolationBuilder violation2 = new ConstraintViolationBuilder();
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.arch-ont.org/ontologies/architecture.owl#use", 
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0");
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerOne0", 
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#type", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerOne");
		violation2.addViolation(
				"http://arch-ont.org/ontologies/famix.owl#TestLayerTwo1", 
				"http://www.w3.org/1999/02/22-rdf-syntax-ns#type", 
				"http://www.arch-ont.org/ontologies/architecture.owl#LayerTwo");
		violations2.add(violation2.build());
		
		CheckedRule r0 = new CheckedRule(rule0, violations1);
		CheckedRule r1 = new CheckedRule(rule1, violations2);
		
		final String outputPath = "src/test/resources/check.owl";
		check.validateRule(r0, "./src/test/resources/mapped.owl", outputPath);
        check.validateRule(r1, "./src/test/resources/mapped.owl", outputPath);
		
		OntModel expected = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected.read(new FileReader("./src/test/resources/check-expected.owl"), "");
		
		OntModel actual = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual.read(new FileReader(outputPath), "");
		
		// workaround: remove the checking date
		expected.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(expected), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(expected))
		.remove();
		actual.getProperty(
				ConformanceCheckOntologyClassesAndProperties.getConformanceCheckIndividual(actual), 
				ConformanceCheckOntologyClassesAndProperties.getDateProperty(actual)).
		remove();

		System.out.println("IN EXPECTED BUT NOT IN ACTUAL:");
		expected.listStatements().forEachRemaining((s) -> {
		   if (!actual.contains(s)) {
		       System.out.println(s.toString());
		   }
		});

        System.out.println("IN ACTUAL BUT NOT IN EXPECTED:");
		actual.listStatements().forEachRemaining((s) -> {
	           if (!expected.contains(s)) {
	               System.out.println(s.toString());
	           }
	        });
		
		assertTrue(expected.isIsomorphicWith(actual)); 
	}
}
