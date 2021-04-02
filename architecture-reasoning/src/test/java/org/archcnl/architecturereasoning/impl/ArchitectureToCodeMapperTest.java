package org.archcnl.architecturereasoning.impl;

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
    private OntModel ruleModel;
    private ObjectProperty uses;
    private final String ontology = "http://test.owl";
    private final String namespace = ontology + "#";

    @Before
    public void setUp() {
        mapper = new ArchitectureToCodeMapper();
        createCodeModel();
        createRuleModel();
    }

    @Test
    public void testMapping() throws IOException {
        // Architecture model: classA must not use classB.
        List<ArchitectureRule> architectureModel =
                Arrays.asList(
                        new ArchitectureRule(
                                0, "No ClassA can use ClassB.", RuleType.NEGATION, ruleModel));

        // Mapping: When some class imports another one, the former uses the latter.
        BufferedReader reader =
                new BufferedReader(
                        new StringReader(
                                "@prefix test: <"
                                        + namespace
                                        + ">\n"
                                        + "[useMapping: (?class test:import ?class2) (?class rdf:type test:class) (?class2 rdf:type test:class) -> (?class test:use ?class2)]\n"));

        Model mappedModel = mapper.executeMapping(codeModel, architectureModel, reader);

        assertTrue(mappedModel.containsAll(codeModel));
        assertTrue(mappedModel.containsResource(uses));
        assertTrue(mappedModel.contains(classA, uses, classB));
    }

    private void createCodeModel() {
        // Code model: The class classA imports the class classB.
        codeModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        OntClass clazz = codeModel.createClass(namespace + "class");
        classA = codeModel.createIndividual(namespace + "classA", clazz);
        classB = codeModel.createIndividual(namespace + "classB", clazz);
        ObjectProperty imports = codeModel.createObjectProperty(namespace + "import");

        codeModel.add(classA, imports, classB);
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
