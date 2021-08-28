package org.archcnl.ui.io;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.input.datatypes.RulesAndMappings;
import org.archcnl.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.input.datatypes.mappings.AndTriplets;
import org.archcnl.input.datatypes.mappings.ConceptManager;
import org.archcnl.input.datatypes.mappings.ConceptMapping;
import org.archcnl.input.datatypes.mappings.RelationManager;
import org.archcnl.input.datatypes.mappings.RelationMapping;
import org.archcnl.input.datatypes.mappings.Triplet;
import org.archcnl.input.datatypes.mappings.Variable;
import org.archcnl.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.input.exceptions.InvalidVariableNameException;
import org.archcnl.input.exceptions.RecursiveRelationException;
import org.archcnl.input.exceptions.RelationDoesNotExistException;
import org.archcnl.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.input.exceptions.VariableAlreadyExistsException;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static long numberOfMatches(Pattern regex, String text) {
        return regex.matcher(text).results().count();
    }

    public static boolean doAllMatchesExistInSecondString(
            Pattern regex, String expected, String actual) {
        Matcher matcher = regex.matcher(expected);
        while (matcher.find()) {
            String match = matcher.group();
            if (!actual.contains(match)) {
                return false;
            }
        }
        return true;
    }

    public static RulesAndMappings prepareModel(
            ConceptManager conceptManager, RelationManager relationManager)
            throws UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RecursiveRelationException,
                    InvalidVariableNameException, VariableAlreadyExistsException {

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
                        relationManager.getRelationByName("is-of-type"));

        ConceptMapping applicationServiceMapping =
                new ConceptMapping(
                        "ApplicationService",
                        classVariable,
                        applicationServiceWhenTriplets,
                        relationManager.getRelationByName("is-of-type"));

        ConceptMapping domainRingMapping =
                new ConceptMapping(
                        "DomainRing",
                        packageVariable,
                        domainRingWhenTriplets,
                        relationManager.getRelationByName("is-of-type"));

        RelationMapping resideInMapping =
                new RelationMapping(
                        "resideIn",
                        classVariable,
                        packageVariable,
                        conceptManager.getConceptByName("Namespace"),
                        resideInWhenTriplets);

        RelationMapping useMapping =
                new RelationMapping(
                        "use",
                        classVariable,
                        class2Variable,
                        conceptManager.getConceptByName("FamixClass"),
                        useWhenTriplets);

        List<ArchitectureRule> archRules = new LinkedList<>();
        archRules.add(new ArchitectureRule("Every Aggregate must resideIn a DomainRing."));
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
