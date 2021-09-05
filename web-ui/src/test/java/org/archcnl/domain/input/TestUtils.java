package org.archcnl.domain.input;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.datatypes.mappings.AndTriplets;
import org.archcnl.domain.input.datatypes.mappings.ConceptMapping;
import org.archcnl.domain.input.datatypes.mappings.CustomConcept;
import org.archcnl.domain.input.datatypes.mappings.CustomRelation;
import org.archcnl.domain.input.datatypes.mappings.RelationMapping;
import org.archcnl.domain.input.datatypes.mappings.StringValue;
import org.archcnl.domain.input.datatypes.mappings.Triplet;
import org.archcnl.domain.input.datatypes.mappings.Variable;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;

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

    public static RulesConceptsAndRelations prepareModel()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        RulesConceptsAndRelations result = new RulesConceptsAndRelations();

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
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate")));
        List<AndTriplets> aggregateWhenTriplets = new LinkedList<>();
        aggregateWhenTriplets.add(new AndTriplets(triplets));

        // isApplicationService Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("api")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> applicationServiceWhenTriplets = new LinkedList<>();
        applicationServiceWhenTriplets.add(new AndTriplets(triplets));

        // isDomainRing Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("domain")));
        List<AndTriplets> domainRingWhenTriplets = new LinkedList<>();
        domainRingWhenTriplets.add(new AndTriplets(triplets));

        // resideIn Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(triplets));

        // use Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Attribute")));
        triplets.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("definesAttribute"),
                        attributeVariable));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("hasDeclaredType"),
                        class2Variable));
        List<Triplet> triplets2 = new LinkedList<>();
        triplets2.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("imports"),
                        class2Variable));
        List<AndTriplets> useWhenTriplets = new LinkedList<>();
        useWhenTriplets.add(new AndTriplets(triplets));
        useWhenTriplets.add(new AndTriplets(triplets2));

        CustomConcept aggregate = new CustomConcept("Aggregate");
        ConceptMapping aggregateMapping =
                new ConceptMapping(classVariable, aggregateWhenTriplets, aggregate);
        aggregate.setMapping(aggregateMapping);

        CustomConcept applicationService = new CustomConcept("ApplicationService");
        ConceptMapping applicationServiceMapping =
                new ConceptMapping(
                        classVariable, applicationServiceWhenTriplets, applicationService);
        applicationService.setMapping(applicationServiceMapping);

        CustomConcept domainRing = new CustomConcept("DomainRing");
        ConceptMapping domainRingMapping =
                new ConceptMapping(packageVariable, domainRingWhenTriplets, domainRing);
        domainRing.setMapping(domainRingMapping);

        CustomRelation resideIn = new CustomRelation("resideIn");
        RelationMapping resideInMapping =
                new RelationMapping(classVariable, packageVariable, resideInWhenTriplets, resideIn);
        resideIn.setMapping(resideInMapping);

        CustomRelation use = new CustomRelation("use");
        RelationMapping useMapping =
                new RelationMapping(classVariable, class2Variable, useWhenTriplets, use);
        use.setMapping(useMapping);

        result.getArchitectureRuleManager()
                .addArchitectureRule(
                        new ArchitectureRule("Every Aggregate must resideIn a DomainRing."));
        result.getArchitectureRuleManager()
                .addArchitectureRule(
                        new ArchitectureRule("No Aggregate can use an ApplicationService."));
        result.getConceptManager().addConcept(aggregate);
        result.getConceptManager().addConcept(applicationService);
        result.getConceptManager().addConcept(domainRing);
        result.getRelationManager().addRelation(resideIn);
        result.getRelationManager().addRelation(use);
        return result;
    }

    public static List<CustomConcept> prepareCustomConcepts()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        return prepareModel().getConceptManager().getCustomConcepts();
    }

    public static List<CustomRelation> prepareCustomRelations()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        return prepareModel().getRelationManager().getCustomRelations();
    }
}
