package org.archcnl.architecturereasoning.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureToCodeMapperTest {

    private ArchitectureToCodeMapper mapper;
    private OntModel codeModel;
    private Individual classA;
    private Individual classB;
    private Individual classC;
    private Individual classD;
    private OntModel ruleModel;
    private ObjectProperty uses;
    private final String ontology = "http://test.owl";
    private final String namespace = ontology + "#";
    private List<ArchitectureRule> architectureModel;
    private BufferedReader nonTransitiveMapping;
    private BufferedReader transitiveMapping;

    @Before
    public void setUp() {
        mapper = new ArchitectureToCodeMapper();
        createCodeModel();
        createRuleModel();

        // Architecture model: classA must not use classB.
        architectureModel =
                Arrays.asList(
                        new ArchitectureRule(
                                0,
                                "No ClassA can use ClassB.",
                                RuleType.NEGATION,
                                ruleModel,
                                null,
                                null));

        // Mapping: When some class imports another one, the former uses the latter.
        nonTransitiveMapping =
                new BufferedReader(
                        new StringReader(
                                "@prefix test: <"
                                        + namespace
                                        + ">\n"
                                        + "[useMapping: (?class test:import ?class2) (?class rdf:type test:class) (?class2 rdf:type test:class) -> (?class test:use ?class2)]\n"));
        // Mapping: When some class imports another one, the former uses the latter.
        // Also, construct the transitive closure of the property use.
        transitiveMapping =
                new BufferedReader(
                        new StringReader(
                                "@prefix test: <"
                                        + namespace
                                        + ">\n"
                                        + "[useMapping: (?class test:import ?class2) (?class rdf:type test:class) (?class2 rdf:type test:class) -> (?class test:use ?class2)]\n"
                                        + "[useMapping: (?class1 test:use ?class2) (?class2 test:use ?class3) -> (?class1 test:use ?class3)]\n"));
    }

    @Test
    public void
            givenCodeModelWithRulesAndMapping_whenMappingIsExecuted_thenMappedModelContainsAllOfCodeModel()
                    throws IOException {
        // given, when
        Model mappedModel =
                mapper.executeMapping(codeModel, architectureModel, nonTransitiveMapping);

        // then
        assertTrue(mappedModel.containsAll(codeModel));
    }

    @Test
    public void
            givenNonTransitiveMapping_whenMappingIsExecuted_thenNonTransitivePropertiesAreDeduced()
                    throws IOException {
        // given, when
        Model mappedModel =
                mapper.executeMapping(codeModel, architectureModel, nonTransitiveMapping);

        // then
        assertTrue(mappedModel.containsResource(uses));
        assertTrue(mappedModel.contains(classA, uses, classB));
        assertTrue(mappedModel.contains(classB, uses, classC));
        assertFalse(mappedModel.contains(classA, uses, classC));
        assertFalse(mappedModel.contains(classC, uses, classA));
        assertFalse(mappedModel.contains(classC, uses, classB));
        assertFalse(mappedModel.contains(classB, uses, classA));
    }

    @Test
    public void givenTransitiveMapping_whenMappingIsExecuted_thenTransitivePropertiesAreDeduced()
            throws IOException {
        // given, when
        Model mappedModel = mapper.executeMapping(codeModel, architectureModel, transitiveMapping);

        // then
        assertTrue(mappedModel.containsResource(uses));
        assertTrue(mappedModel.contains(classA, uses, classB));
        assertTrue(mappedModel.contains(classB, uses, classC));
        assertTrue(mappedModel.contains(classA, uses, classC));
        assertFalse(mappedModel.contains(classC, uses, classA));
        assertFalse(mappedModel.contains(classC, uses, classB));
        assertFalse(mappedModel.contains(classB, uses, classA));
    }

    @Test
    public void
            givenTransitiveMapping_whenMappingIsExecuted_then2HopTransitivePropertiesAreCorrectlyDeduced()
                    throws IOException {
        // given, when
        Model mappedModel = mapper.executeMapping(codeModel, architectureModel, transitiveMapping);

        // then
        assertTrue(mappedModel.containsResource(uses));
        assertTrue(mappedModel.contains(classA, uses, classB));
        assertTrue(mappedModel.contains(classB, uses, classC));
        assertTrue(mappedModel.contains(classC, uses, classD));
        assertTrue(mappedModel.contains(classA, uses, classC));
        assertTrue(mappedModel.contains(classA, uses, classD));
        assertTrue(mappedModel.contains(classB, uses, classD));
    }

    @Test
    public void
            givenNonTransitiveMapping_whenMappingIsExecuted_thenNonTransitivePropertyIsNotReflexive()
                    throws IOException {
        // given, when
        Model mappedModel =
                mapper.executeMapping(codeModel, architectureModel, nonTransitiveMapping);

        // then
        assertFalse(mappedModel.contains(classA, uses, classA));
        assertFalse(mappedModel.contains(classB, uses, classB));
        assertFalse(mappedModel.contains(classC, uses, classC));
    }

    @Test
    public void givenTransitiveMapping_whenMappingIsExecuted_thenTransitivePropertyIsNotReflexive()
            throws IOException {
        // given, when
        Model mappedModel = mapper.executeMapping(codeModel, architectureModel, transitiveMapping);

        // then
        assertFalse(mappedModel.contains(classA, uses, classA));
        assertFalse(mappedModel.contains(classB, uses, classB));
        assertFalse(mappedModel.contains(classC, uses, classC));
    }

    private void createCodeModel() {
        // Code model: The class classA imports the class classB.
        codeModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntClass clazz = codeModel.createClass(namespace + "class");
        classA = codeModel.createIndividual(namespace + "classA", clazz);
        classB = codeModel.createIndividual(namespace + "classB", clazz);
        classC = codeModel.createIndividual(namespace + "classC", clazz);
        classD = codeModel.createIndividual(namespace + "classD", clazz);
        ObjectProperty imports = codeModel.createObjectProperty(namespace + "import");
        ObjectProperty calls = codeModel.createObjectProperty(namespace + "call");

        codeModel.add(classA, imports, classB);
        codeModel.add(classB, imports, classC);
        codeModel.add(classC, imports, classD);
        codeModel.add(classA, calls, classC);
    }

    private void createRuleModel() {
        ruleModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

        ruleModel.createOntology(ontology);

        uses = ruleModel.createObjectProperty(namespace + "use");
        OntClass ruleClassA = ruleModel.createClass(namespace + "ClassA");
        OntClass ruleClassB = ruleModel.createClass(namespace + "ClassB");

        Restriction someValuesFrom =
                ruleModel.createSomeValuesFromRestriction(null, uses, ruleClassB);
        OntClass complement = ruleModel.createComplementClass(null, someValuesFrom);

        ruleClassA.setSuperClass(complement);
    }
}
