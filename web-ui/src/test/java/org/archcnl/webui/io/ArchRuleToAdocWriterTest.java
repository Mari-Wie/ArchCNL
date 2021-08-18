package org.archcnl.webui.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.archcnl.webui.datatypes.RulesAndMappings;
import org.archcnl.webui.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.webui.datatypes.mappings.AndTriplets;
import org.archcnl.webui.datatypes.mappings.ConceptManager;
import org.archcnl.webui.datatypes.mappings.ConceptMapping;
import org.archcnl.webui.datatypes.mappings.RelationManager;
import org.archcnl.webui.datatypes.mappings.RelationMapping;
import org.archcnl.webui.datatypes.mappings.Triplet;
import org.archcnl.webui.datatypes.mappings.Variable;
import org.archcnl.webui.exceptions.ConceptAlreadyExistsException;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;
import org.archcnl.webui.exceptions.InvalidVariableNameException;
import org.archcnl.webui.exceptions.RecursiveRelationException;
import org.archcnl.webui.exceptions.RelationAlreadyExistsException;
import org.archcnl.webui.exceptions.RelationDoesNotExistException;
import org.archcnl.webui.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArchRuleToAdocWriterTest {

    private static RelationManager relationManager;
    private static ConceptManager conceptManager;

    @BeforeAll
    public static void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenRulesAndMappings_whenWritingAdocFile_thenExpectedResult()
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RecursiveRelationException,
                    InvalidVariableNameException, ConceptAlreadyExistsException,
                    VariableAlreadyExistsException, RelationAlreadyExistsException {
        // given
        RulesAndMappings rulesAndMappings = prepareModel();

        // when
        final File file = new File("src/test/resources/onionWriterTest.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        archRulesToAdocWriter.writeArchitectureRules(file, rulesAndMappings);

        // then
        String actualContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        final File expectedFile =
                new File("src/test/resources/architecture-documentation-onion.adoc");
        String expectedContent = FileUtils.readFileToString(expectedFile, StandardCharsets.UTF_8);

        assertEquals(
                numberOfMatches(ArchRulesFromAdocReader.getRulePattern(), expectedContent),
                numberOfMatches(ArchRulesFromAdocReader.getRulePattern(), actualContent));
        assertEquals(
                numberOfMatches(
                        ArchRulesFromAdocReader.getConceptMappingPattern(), expectedContent),
                numberOfMatches(ArchRulesFromAdocReader.getConceptMappingPattern(), actualContent));
        assertEquals(
                numberOfMatches(
                        ArchRulesFromAdocReader.getRelationMappingPattern(), expectedContent),
                numberOfMatches(
                        ArchRulesFromAdocReader.getRelationMappingPattern(), actualContent));

        assertTrue(
                doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getRulePattern(), expectedContent, actualContent));
        assertTrue(
                doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getConceptMappingPattern(),
                        expectedContent,
                        actualContent));
        assertTrue(
                doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getRelationMappingPattern(),
                        expectedContent,
                        actualContent));
    }

    private long numberOfMatches(Pattern regex, String text) {
        return regex.matcher(text).results().count();
    }

    private boolean doAllMatchesExistInSecondString(Pattern regex, String expected, String actual) {
        Matcher matcher = regex.matcher(expected);
        while (matcher.find()) {
            String match = matcher.group();
            if (!actual.contains(match)) {
                return false;
            }
        }
        return true;
    }

    private RulesAndMappings prepareModel()
            throws UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RecursiveRelationException,
                    InvalidVariableNameException, ConceptAlreadyExistsException,
                    VariableAlreadyExistsException, RelationAlreadyExistsException {

        // prepare model with rules and mappings from OnionArchitectureDemo example

        Variable classVariable = new Variable("class");
        Variable class2Variable = new Variable("class2");
        Variable nameVariable = new Variable("name");
        Variable packageVariable = new Variable("package");
        Variable attributeVariable = new Variable("f");

        // isAggregate Mapping
        List<Triplet> triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        classVariable, relationManager.getRelationByName("hasName"), nameVariable));
        triplets.add(
                new Triplet(
                        nameVariable,
                        relationManager.getRelationByName("matches"),
                        "(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate"));
        List<AndTriplets> aggregateWhenTriplets = new LinkedList<>();
        aggregateWhenTriplets.add(new AndTriplets(triplets));

        // isApplicationService Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(nameVariable, relationManager.getRelationByName("matches"), "api"));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> applicationServiceWhenTriplets = new LinkedList<>();
        applicationServiceWhenTriplets.add(new AndTriplets(triplets));

        // isDomainRing Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(nameVariable, relationManager.getRelationByName("matches"), "domain"));
        List<AndTriplets> domainRingWhenTriplets = new LinkedList<>();
        domainRingWhenTriplets.add(new AndTriplets(triplets));

        // resideIn Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(triplets));

        // use Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        class2Variable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Attribute")));
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("definesAttribute"),
                        attributeVariable));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        relationManager.getRelationByName("hasDeclaredType"),
                        class2Variable));
        List<Triplet> triplets2 = new LinkedList<>();
        triplets2.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        class2Variable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("imports"),
                        class2Variable));
        List<AndTriplets> useWhenTriplets = new LinkedList<>();
        useWhenTriplets.add(new AndTriplets(triplets));
        useWhenTriplets.add(new AndTriplets(triplets2));

        ConceptMapping aggregateMapping =
                new ConceptMapping(
                        "Aggregate",
                        classVariable,
                        aggregateWhenTriplets,
                        conceptManager,
                        relationManager);

        ConceptMapping applicationServiceMapping =
                new ConceptMapping(
                        "ApplicationService",
                        classVariable,
                        applicationServiceWhenTriplets,
                        conceptManager,
                        relationManager);

        ConceptMapping domainRingMapping =
                new ConceptMapping(
                        "DomainRing",
                        packageVariable,
                        domainRingWhenTriplets,
                        conceptManager,
                        relationManager);

        RelationMapping resideInMapping =
                new RelationMapping(
                        "resideIn",
                        classVariable,
                        packageVariable,
                        conceptManager.getConceptByName("Namespace"),
                        resideInWhenTriplets,
                        conceptManager,
                        relationManager);

        RelationMapping useMapping =
                new RelationMapping(
                        "use",
                        classVariable,
                        class2Variable,
                        conceptManager.getConceptByName("FamixClass"),
                        useWhenTriplets,
                        conceptManager,
                        relationManager);

        List<ArchitectureRule> archRules = new LinkedList<>();
        archRules.add(new ArchitectureRule("Every Aggregate must residein a DomainRing."));
        archRules.add(new ArchitectureRule("No Aggregate can use an ApplicationService."));
        List<ConceptMapping> conceptMappings = new LinkedList<>();
        conceptMappings.add(aggregateMapping);
        conceptMappings.add(applicationServiceMapping);
        conceptMappings.add(domainRingMapping);
        List<RelationMapping> relationMappings = new LinkedList<>();
        relationMappings.add(resideInMapping);
        relationMappings.add(useMapping);
        return new RulesAndMappings(archRules, conceptMappings, relationMappings);
    }
}
