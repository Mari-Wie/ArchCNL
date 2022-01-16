package org.archcnl.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static long numberOfMatches(final Pattern regex, final String text) {
        return regex.matcher(text).results().count();
    }

    public static boolean doAllMatchesExistInSecondString(
            final Pattern regex, final String expected, final String actual) {
        final Matcher matcher = regex.matcher(expected);
        while (matcher.find()) {
            final String match = matcher.group();
            final String replaced = match.replaceAll("\r\n?", "\n");
            if (!actual.contains(replaced)) {
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
        final RulesConceptsAndRelations result = new RulesConceptsAndRelations();

        // prepare model with rules and mappings from OnionArchitectureDemo example

        final Variable classVariable = new Variable("class");
        final Variable class2Variable = new Variable("class2");
        final Variable nameVariable = new Variable("name");
        final Variable packageVariable = new Variable("package");
        final Variable attributeVariable = new Variable("f");
        final Variable varVariable = new Variable("var");

        // isAggregate Mapping
        List<Triplet> triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("hasName").get(),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches").get(),
                        new StringValue("(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate")));
        final List<AndTriplets> aggregateWhenTriplets = new LinkedList<>();
        aggregateWhenTriplets.add(new AndTriplets(triplets));

        // isApplicationService Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("Namespace").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName").get(),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches").get(),
                        new StringValue("api")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains").get(),
                        classVariable));
        final List<AndTriplets> applicationServiceWhenTriplets = new LinkedList<>();
        applicationServiceWhenTriplets.add(new AndTriplets(triplets));

        // isDomainRing Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("Namespace").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName").get(),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches").get(),
                        new StringValue("domain")));
        final List<AndTriplets> domainRingWhenTriplets = new LinkedList<>();
        domainRingWhenTriplets.add(new AndTriplets(triplets));

        // resideIn Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("Namespace").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains").get(),
                        classVariable));
        final List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(triplets));

        // use Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("Attribute").get()));
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("definesAttribute").get(),
                        attributeVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("hasDeclaredType").get(),
                        class2Variable));
        final List<Triplet> triplets2 = new LinkedList<>();
        triplets2.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets2.add(
                TripletFactory.createTriplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type").get(),
                        result.getConceptManager().getConceptByName("FamixClass").get()));
        triplets2.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("imports").get(),
                        class2Variable));
        final List<AndTriplets> useWhenTriplets = new LinkedList<>();
        useWhenTriplets.add(new AndTriplets(triplets));
        useWhenTriplets.add(new AndTriplets(triplets2));

        final CustomConcept aggregate =
                new CustomConcept(
                        "Aggregate", "Every class whose name ends with Aggregate is an Aggregate.");
        final ConceptMapping aggregateMapping =
                new ConceptMapping(classVariable, aggregateWhenTriplets, aggregate);
        aggregate.setMapping(aggregateMapping);

        final CustomConcept applicationService = new CustomConcept("ApplicationService", "");
        final ConceptMapping applicationServiceMapping =
                new ConceptMapping(
                        classVariable, applicationServiceWhenTriplets, applicationService);
        applicationService.setMapping(applicationServiceMapping);

        final CustomConcept domainRing = new CustomConcept("DomainRing", "");
        final ConceptMapping domainRingMapping =
                new ConceptMapping(packageVariable, domainRingWhenTriplets, domainRing);
        domainRing.setMapping(domainRingMapping);

        final CustomRelation resideIn = new CustomRelation("resideIn", "", new LinkedList<>());
        final RelationMapping resideInMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(classVariable, resideIn, packageVariable),
                        resideInWhenTriplets);
        resideIn.setMapping(resideInMapping);

        final CustomRelation use =
                new CustomRelation(
                        "use",
                        "A class uses another class if it has a field of it or if it imports it.",
                        new LinkedList<>());
        final RelationMapping useMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(classVariable, use, class2Variable),
                        useWhenTriplets);
        use.setMapping(useMapping);

        final CustomConcept emptyWhenConcept = new CustomConcept("EmptyWhenConcept", "");
        final ConceptMapping emptyWhenConceptMapping =
                new ConceptMapping(varVariable, new LinkedList<>(), emptyWhenConcept);
        emptyWhenConcept.setMapping(emptyWhenConceptMapping);

        final CustomRelation emptyWhenRelationString =
                new CustomRelation(
                        "emptyWhenRelationString",
                        "",
                        new LinkedList<>(Arrays.asList(new StringValue(""))));
        final RelationMapping emptyWhenRelationStringMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                varVariable,
                                emptyWhenRelationString,
                                new StringValue("test string")),
                        new LinkedList<>());
        emptyWhenRelationString.setMapping(emptyWhenRelationStringMapping);

        final CustomRelation emptyWhenRelationBoolean =
                new CustomRelation(
                        "emptyWhenRelationBoolean",
                        "",
                        new LinkedList<>(Arrays.asList(new BooleanValue(false))));
        final RelationMapping emptyWhenRelationBooleanMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                varVariable, emptyWhenRelationBoolean, new BooleanValue(false)),
                        new LinkedList<>());
        emptyWhenRelationBoolean.setMapping(emptyWhenRelationBooleanMapping);

        final CustomRelation emptyWhenRelationVariable =
                new CustomRelation("emptyWhenRelationVariable", "", new LinkedList<>());
        final RelationMapping emptyWhenRelationVariableMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                varVariable, emptyWhenRelationVariable, new Variable("test")),
                        new LinkedList<>());
        emptyWhenRelationVariable.setMapping(emptyWhenRelationVariableMapping);

        result.getArchitectureRuleManager()
                .addArchitectureRule(
                        new ArchitectureRule("Every Aggregate must resideIn a DomainRing."));
        result.getArchitectureRuleManager()
                .addArchitectureRule(
                        new ArchitectureRule("No Aggregate can use an ApplicationService."));
        result.getConceptManager().addConcept(aggregate);
        result.getConceptManager().addConcept(applicationService);
        result.getConceptManager().addConcept(domainRing);
        result.getConceptManager().addConcept(emptyWhenConcept);
        result.getRelationManager().addRelation(resideIn);
        result.getRelationManager().addRelation(use);
        result.getRelationManager().addRelation(emptyWhenRelationVariable);
        result.getRelationManager().addRelation(emptyWhenRelationString);
        result.getRelationManager().addRelation(emptyWhenRelationBoolean);
        return result;
    }

    public static List<CustomConcept> prepareCustomConcepts()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        return TestUtils.prepareModel().getConceptManager().getCustomConcepts();
    }

    public static List<CustomRelation> prepareCustomRelations()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        return TestUtils.prepareModel().getRelationManager().getCustomRelations();
    }
}
