package org.archcnl.conformancechecking.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.archcnl.conformancechecking.impl.ConformanceCheckOntologyClassesAndProperties.ConformanceCheckDatatypeProperties;
import org.archcnl.conformancechecking.impl.ConformanceCheckOntologyClassesAndProperties.ConformanceCheckOntClasses;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConformanceCheckOntologyTest {

    private ArchitectureRule rule1;
    private ArchitectureRule rule2;
    private String cnlSentence1 = "No A can use B.";
    private String cnlSentence2 = "Every C must use D.";

    @Before
    public void setUp() {
        Model emptyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        rule1 =
                new ArchitectureRule(
                        0, cnlSentence1, RuleType.DOMAIN_RANGE, emptyModel, null, null);
        rule2 =
                new ArchitectureRule(
                        0, cnlSentence2, RuleType.DOMAIN_RANGE, emptyModel, null, null);
    }

    @Test
    public void givenArchitectureRule_whenStoringRule_thenRuleIsStoredInModel()
            throws FileNotFoundException {
        // given
        ConformanceCheckOntology ontology = new ConformanceCheckOntology();
        ontology.newConformanceCheck();
        // when
        ontology.storeArchitectureRule(rule1);

        // then
        Assert.assertNotNull(ontology.getModel());
        String architectureRuleUri =
                ConformanceCheckOntologyClassesAndProperties.ConformanceCheckOntClasses
                        .ArchitectureRule.getUri();
        List<Individual> architectureRules =
                ontology.getModel()
                        .listIndividuals(ontology.getModel().getOntClass(architectureRuleUri))
                        .toList();
        Assert.assertEquals(architectureRules.size(), 1);
        Individual architectureRule = architectureRules.get(0);
        Assert.assertTrue(
                architectureRule.hasProperty(
                        ontology.getModel()
                                .getProperty(
                                        ConformanceCheckOntologyClassesAndProperties
                                                .ConformanceCheckDatatypeProperties
                                                .hasRuleRepresentation
                                                .getUri()),
                        cnlSentence1));

        OntModel expectedModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            expectedModel.read(
                    new FileReader("src/test/resources/architecture-rule-expected.owl"), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // remove checking date, as this prevents isomorphism
        DatatypeProperty hasCheckingDate =
                ConformanceCheckOntologyClassesAndProperties.get(
                        ConformanceCheckDatatypeProperties.hasCheckingDate, expectedModel);
        expectedModel
                .getProperty(
                        ConformanceCheckOntologyClassesAndProperties.createIndividual(
                                ConformanceCheckOntClasses.ConformanceCheck, expectedModel),
                        hasCheckingDate)
                .remove();
        hasCheckingDate =
                ConformanceCheckOntologyClassesAndProperties.get(
                        ConformanceCheckDatatypeProperties.hasCheckingDate, ontology.getModel());
        ontology.getModel()
                .getProperty(
                        ConformanceCheckOntologyClassesAndProperties.createIndividual(
                                ConformanceCheckOntClasses.ConformanceCheck, ontology.getModel()),
                        hasCheckingDate)
                .remove();

        Assert.assertTrue(ontology.getModel().isIsomorphicWith(expectedModel));
    }

    @Test
    public void givenDifferentRules_whenStoringInDifferentModels_thenModelsAreNotIsomorphic() {
        // given
        ConformanceCheckOntology ontology1 = new ConformanceCheckOntology();
        ontology1.newConformanceCheck();

        ConformanceCheckOntology ontology2 = new ConformanceCheckOntology();
        ontology2.newConformanceCheck();
        // when
        ontology1.storeArchitectureRule(rule1);
        ontology2.storeArchitectureRule(rule2);
        // then
        Assert.assertFalse(ontology1.getModel().isIsomorphicWith(ontology2.getModel()));
    }
}
