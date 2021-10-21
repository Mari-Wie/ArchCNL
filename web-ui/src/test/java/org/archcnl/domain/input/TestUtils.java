package org.archcnl.domain.input;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.domain.input.model.mappings.BooleanValue;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.model.mappings.StringValue;
import org.archcnl.domain.input.model.mappings.Triplet;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.domain.input.model.mappings.TripletFactory;


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
        Variable varVariable = new Variable("var");

        // isAggregate Mapping
        List<Triplet> triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate")));
        List<AndTriplets> aggregateWhenTriplets = new LinkedList<>();
        aggregateWhenTriplets.add(new AndTriplets(triplets));

        // isApplicationService Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("api")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> applicationServiceWhenTriplets = new LinkedList<>();
        applicationServiceWhenTriplets.add(new AndTriplets(triplets));

        // isDomainRing Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        nameVariable,
                        result.getRelationManager().getRelationByName("matches"),
                        new StringValue("domain")));
        List<AndTriplets> domainRingWhenTriplets = new LinkedList<>();
        domainRingWhenTriplets.add(new AndTriplets(triplets));

        // resideIn Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Namespace")));
        triplets.add(
                TripletFactory.createTriplet(
                        packageVariable,
                        result.getRelationManager().getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(triplets));

        // use Mapping
        triplets = new LinkedList<>();
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                TripletFactory.createTriplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets.add(
                TripletFactory.createTriplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("Attribute")));
        triplets.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("definesAttribute"),
                        attributeVariable));
        triplets.add(
                TripletFactory.createTriplet(
                        attributeVariable,
                        result.getRelationManager().getRelationByName("hasDeclaredType"),
                        class2Variable));
        List<Triplet> triplets2 = new LinkedList<>();
        triplets2.add(
                TripletFactory.createTriplet(
                        classVariable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets2.add(
                TripletFactory.createTriplet(
                        class2Variable,
                        result.getRelationManager().getRelationByName("is-of-type"),
                        result.getConceptManager().getConceptByName("FamixClass")));
        triplets2.add(
                TripletFactory.createTriplet(
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

        CustomRelation resideIn = new CustomRelation("resideIn", new LinkedList<>());
        RelationMapping resideInMapping =
                new RelationMapping(TripletFactory.createTriplet(classVariable, resideIn, packageVariable), resideInWhenTriplets);
        resideIn.setMapping(resideInMapping);

        CustomRelation use = new CustomRelation("use", new LinkedList<>());
        RelationMapping useMapping =
                new RelationMapping(TripletFactory.createTriplet(classVariable,use, class2Variable), useWhenTriplets);
        use.setMapping(useMapping);

        CustomConcept emptyWhenConcept = new CustomConcept("EmptyWhenConcept");
        ConceptMapping emptyWhenConceptMapping =
                new ConceptMapping(varVariable, new LinkedList<>(), emptyWhenConcept);
        emptyWhenConcept.setMapping(emptyWhenConceptMapping);

        List<ObjectType> relatableObjectTypes = new LinkedList<>();
        relatableObjectTypes.add(new StringValue(""));
        CustomRelation emptyWhenRelationString =
                new CustomRelation("emptyWhenRelationString", relatableObjectTypes);
        RelationMapping emptyWhenRelationStringMapping =
                new RelationMapping(
                        TripletFactory.createTriplet(
                        varVariable,
                        emptyWhenRelationString,
                        new StringValue("test string")),
                        new LinkedList<>());
        emptyWhenRelationString.setMapping(emptyWhenRelationStringMapping);

        relatableObjectTypes = new LinkedList<>();
        relatableObjectTypes.add(new BooleanValue(false));
        CustomRelation emptyWhenRelationBoolean =
                new CustomRelation("emptyWhenRelationBoolean", relatableObjectTypes);
        RelationMapping emptyWhenRelationBooleanMapping =
                new RelationMapping(TripletFactory.createTriplet(
                        varVariable,
                        emptyWhenRelationBoolean,
                        new BooleanValue(false)),
                        new LinkedList<>());
        emptyWhenRelationBoolean.setMapping(emptyWhenRelationBooleanMapping);

        CustomRelation emptyWhenRelationVariable =
                new CustomRelation("emptyWhenRelationVariable", new LinkedList<>());
        RelationMapping emptyWhenRelationVariableMapping =
                new RelationMapping(TripletFactory.createTriplet(
                        varVariable,
                        emptyWhenRelationVariable,
                        new Variable("test")),
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
