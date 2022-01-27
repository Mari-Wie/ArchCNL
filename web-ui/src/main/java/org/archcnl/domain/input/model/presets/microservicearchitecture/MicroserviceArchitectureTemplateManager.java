package org.archcnl.domain.input.model.presets.microservicearchitecture;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.common.TwoColumnGridEntry;

/**
 * Template Manager that creates CustomConcepts and CustomRelations for the Microservice
 * architectural Style.
 */
public class MicroserviceArchitectureTemplateManager implements ArchitecturalStyleTemplateManager {

    // variables
    Variable classVariable;
    Variable class2Variable;
    Variable classPackage;
    Variable class2Package;
    Variable nameVariable;
    Variable registryVariable;
    Variable registryImportVariable;
    Variable registryImportNameVariable;
    Variable methodVariable;

    public MicroserviceArchitectureTemplateManager() {
        super();
    }

    @Override
    public void initializeVariables() {
        try {
            classVariable = new Variable("class");
            class2Variable = new Variable("class2");
            classPackage = new Variable("classPackage");
            class2Package = new Variable("class2Package");
            nameVariable = new Variable("name");
            registryVariable = new Variable("registry");
            registryImportVariable = new Variable("registryImport");
            registryImportNameVariable = new Variable("registryImportName");
            methodVariable = new Variable("method");
        } catch (InvalidVariableNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'Microservice' and uses a regular expression to match all services that are provided to this
     * method.
     *
     * @param services the services to map as CustomConcept Microservice.
     */
    public CustomConcept createMicroservicesConceptAndMapping(Set<TwoColumnGridEntry> services) {

        StringBuilder regexExp = new StringBuilder();
        for (Iterator<TwoColumnGridEntry> iterator = services.iterator(); iterator.hasNext(); ) {
            TwoColumnGridEntry microserviceEntry = iterator.next();
            String regexInputPart = String.format("%s$", microserviceEntry.getInfo2());
            regexExp.append(regexInputPart);

            // if there's another element append "|" to the regExp
            if (iterator.hasNext()) {
                regexExp.append("|");
            }
        }

        String regexInput = regexExp.toString();

        // we're only having one concept for all microservices
        CustomConcept microservice = new CustomConcept("Microservice", "");

        List<AndTriplets> specificMicroserviceWhenTriplets = createMicroserviceTriplets(regexInput);

        try {
            ConceptMapping mapping =
                    new ConceptMapping(
                            classVariable, specificMicroserviceWhenTriplets, microservice);
            microservice.setMapping(mapping);

            return microservice;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Contains the body of the swrl rule for the microservice mapping. */
    private List<AndTriplets> createMicroserviceTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {
            List<Triplet> createdTriplets = new LinkedList<>();

            // (?class rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?class famix:hasName ?name)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            // regex(?name, 'NotificationServiceApplication$')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> microserviceWhenTriplets = new LinkedList<>();
            microserviceWhenTriplets.add(new AndTriplets(createdTriplets));

            return microserviceWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'ServiceRegistry' and uses a regular expression. The regular expression used will take the
     * provided String and produce an expression of "(\\w||\\W)*%CLASS_NAME$" where CLASS_NAME is
     * the provided param String of the method.
     *
     * @param serviceregistryClassName the name of the service registry class.
     */
    public CustomConcept createServiceRegistryConceptAndMapping(String serviceregistryClassName) {
        String regexInput = String.format("(\\\\w||\\\\W)*%s$", serviceregistryClassName);

        // registry name is fixed as their can only be one
        CustomConcept registry = new CustomConcept("ServiceRegistry", "");
        List<AndTriplets> whenTriplets = createServiceRegistryWhenTriplets(regexInput);

        try {
            ConceptMapping mapping = new ConceptMapping(classVariable, whenTriplets, registry);
            registry.setMapping(mapping);
            return registry;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the ServiceRegistry mapping. */
    private List<AndTriplets> createServiceRegistryWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
        try {
            List<Triplet> createdTriplets = new LinkedList<>();

            // (?class rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?class famix:hasName ?name)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            // e.g. regex(?name, '(\\w||\\W)*RegistryApplication$')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> serviceRegistryWhenTriplets = new LinkedList<>();
            serviceRegistryWhenTriplets.add(new AndTriplets(createdTriplets));

            return serviceRegistryWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomRelation 'registerin' including the mapping for the relation.
     *
     * @param registryImportName The name of the class that is imported when registering a
     *     Microservice.
     */
    public CustomRelation createRegisterinRelationAndMapping(String registryImportName) {
        String regexInput = registryImportName;

        CustomRelation registerinRelation =
                new CustomRelation("registerin", "", new LinkedList<>());

        List<AndTriplets> whenTriplets = createRegisterinWhenTriplets(regexInput);

        try {
            RelationMapping mapping =
                    new RelationMapping(
                            TripletFactory.createTriplet(
                                    classVariable, registerinRelation, registryVariable),
                            whenTriplets);
            registerinRelation.setMapping(mapping);
            return registerinRelation;
        } catch (UnsupportedObjectTypeInTriplet | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the registerin mapping. */
    private List<AndTriplets> createRegisterinWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        List<Triplet> createdTriplets = new LinkedList<>();

        // (?class rdf:type famix:FamixClass)
        try {
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            registryVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("ServiceRegistry").get()));

            // (?class rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?registryImport rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            registryImportVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?class famix:imports ?registryImport)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("imports").get(),
                            registryImportVariable));

            // (?registryImport famix:hasName ?registryImportName)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            registryImportVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            registryImportNameVariable));

            // e.g. regex(?name, '(\\w||\\W)*RegistryApplication$')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            registryImportNameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> registerinWhenTriplets = new LinkedList<>();
        registerinWhenTriplets.add(new AndTriplets(createdTriplets));
        return registerinWhenTriplets;
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'DatabaseAccessAbstraction' and uses a regular expression. The regular expression used will
     * take the provided String and produce an expression of "(\\w||\\W)*%CLASS_NAME_ENDING$" where
     * CLASS_NAME_ENDING is the class name nending for the DB-Access-Abstraction. <br>
     *
     * <p>If e.g. a Spring Repository is used the ending (depending on the naming convention) could
     * be 'Repository' which would then be used as CLASS_NAME_ENDING in the regular expression. If
     * multiple DB-Access-Abstractions are used they will be piped within the regex.
     *
     * @param serviceregistryClassName the name of the service registry class.
     */
    public CustomConcept createDbAccessAbstractionConceptAndMapping(Set<TwoColumnGridEntry> set) {

        StringBuilder regexExp = new StringBuilder();
        regexExp.append("(\\\\w||\\\\W)*");
        for (Iterator<TwoColumnGridEntry> iterator = set.iterator(); iterator.hasNext(); ) {
            TwoColumnGridEntry dbAccessAbstraction = iterator.next();
            String dbAccessAbstractionRegExpPart =
                    String.format("%s$", dbAccessAbstraction.getInfo2());
            regexExp.append(dbAccessAbstractionRegExpPart);

            // if there's another element append "|" to the regExp
            if (iterator.hasNext()) {
                regexExp.append("|");
            }
        }

        CustomConcept abstraction = new CustomConcept("DatabaseAccessAbstraction", "");

        List<AndTriplets> dbAccessAbstractionWhenTriplets =
                createDbAccessAbstractionTriplets(regexExp.toString());

        try {
            ConceptMapping mapping =
                    new ConceptMapping(classVariable, dbAccessAbstractionWhenTriplets, abstraction);
            abstraction.setMapping(mapping);

            return abstraction;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the DatabaseAccessAbstraction mapping */
    private List<AndTriplets> createDbAccessAbstractionTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        List<Triplet> createdTriplets = new LinkedList<>();

        try {
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("isExternal").get(),
                            new BooleanValue(false)));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> dbAccessAbstractionWhenTriplets = new LinkedList<>();
        dbAccessAbstractionWhenTriplets.add(new AndTriplets(createdTriplets));
        return dbAccessAbstractionWhenTriplets;
    }

    /**
     * Creates the CustomRelation 'haveown' and respective mappings.
     *
     * <p><b>NOTE</b><br>
     * The order of the triplets is important to avoid "out of memory"-exceptions when executing the
     * archcnl-toolchain.
     *
     * <p>
     */
    public CustomRelation createHaveownRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation haveown = new CustomRelation("haveown", "", new LinkedList<>());

        List<Triplet> createdTriplets = new LinkedList<>();

        try {
            // (?class1 rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?class2 rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Variable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?classPackage rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?classPackage famix:namespaceContains ?class1)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("namespaceContains").get(),
                            classVariable));

            // (?class2Package rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Package,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?classPackage famix:namespaceContains ?class2Package)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Package,
                            model.getRelationManager().getRelationByName("namespaceContains").get(),
                            class2Variable));

            // (?classPackage famix:namespaceContains ?class2Package)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("namespaceContains").get(),
                            class2Package));

        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> haveownWhenTriplets = new LinkedList<>();
        haveownWhenTriplets.add(new AndTriplets(createdTriplets));

        try {
            RelationMapping mapping =
                    new RelationMapping(
                            TripletFactory.createTriplet(classVariable, haveown, class2Variable),
                            haveownWhenTriplets);
            haveown.setMapping(mapping);
            return haveown;
        } catch (UnsupportedObjectTypeInTriplet | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'ApiGateway' and uses a regular expression. The regular expression used will take the
     * provided String and produce an expression of "(\\w||\\W)*%PACKAGE_NAME$" where PACKAGE_NAME
     * is the provided param String of the method.
     *
     * @param apiGatewayPackageName the name of the package that includes the api gateway.
     */
    public CustomConcept createApiGatewayConceptAndMapping(String apiGatewayPackageName) {
        String regexInput = String.format("(\\\\w||\\\\W)*%s$", apiGatewayPackageName);

        // gateway name is fixed as their can only be one
        CustomConcept gateway = new CustomConcept("ApiGateway", "");
        List<AndTriplets> whenTriplets = createApiGatewayWhenTriplets(regexInput);

        try {
            ConceptMapping mapping = new ConceptMapping(classPackage, whenTriplets, gateway);
            gateway.setMapping(mapping);
            return gateway;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the 'ApiGateway' mapping */
    private List<AndTriplets> createApiGatewayWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {
            List<Triplet> createdTriplets = new LinkedList<>();

            // (?package rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?package famix:hasName ?name)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            // e.g. regex(?name, '(\\w||\\W)*RegistryApplication$')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> apiGatewayWhenTriplets = new LinkedList<>();
            apiGatewayWhenTriplets.add(new AndTriplets(createdTriplets));

            return apiGatewayWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'MicroserviceApp' and uses a regular expression. The regular expression used will take the
     * provided String and produce an expression of "(\\w||\\W)*%MS_APP_NAME" where PACKAGE_NAME is
     * the provided param String of the method.
     *
     * @param msAppPackageStructure the start (e.g. com.example) of the package structure for the
     *     microserviec application.
     */
    public CustomConcept createMicroserviceAppConceptAndMapping(String msAppPackageStructure) {
        // escape dots in package structure
        msAppPackageStructure = msAppPackageStructure.replace(".", "\\\\.");

        String regexInput = String.format("%s$(\\\\w||\\\\W)*", msAppPackageStructure);

        // name of the concept is fixed
        CustomConcept app = new CustomConcept("MicroserviceApp", "");

        List<AndTriplets> whenTriplets = createMicroserviceAppWhenTriplets(regexInput);

        try {
            ConceptMapping mapping = new ConceptMapping(classPackage, whenTriplets, app);
            app.setMapping(mapping);
            return app;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the 'MicroserviceApp' concept */
    private List<AndTriplets> createMicroserviceAppWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {
            List<Triplet> createdTriplets = new LinkedList<>();

            // (?class rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?class famix:hasName ?name)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            // e.g. regex(?packageName, 'com\\.piggymetrics(\\w||\\W)*')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> microserviceAppWhenTriplets = new LinkedList<>();
            microserviceAppWhenTriplets.add(new AndTriplets(createdTriplets));

            return microserviceAppWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the CustomConcept and respective ConceptMaping for the architectural concept
     * 'CircuitBreaker' and uses a regular expression. The regular expression used will take the
     * provided String and produce an expression of "(\\w||\\W)*%CIRCUIT_BREAKER_IMPORT_NAME%" where
     * CIRCUIT_BREAKER_IMPORT_NAME is the provided param String of the method.
     *
     * @param circuitBreakerImportClassName the name of the class thats imported when a circuit
     *     breaker is used.
     */
    public CustomConcept createCircuitBreakerConceptAndMapping(
            String circuitBreakerImportClassName) {
        String regexInput = String.format("(\\\\w||\\\\W)*%s$", circuitBreakerImportClassName);

        CustomConcept circuitBreaker = new CustomConcept("CircuitBreaker", "");

        List<AndTriplets> whenTriplets = createCircuitBreakerWhenTriplets(regexInput);

        try {
            ConceptMapping mapping =
                    new ConceptMapping(classVariable, whenTriplets, circuitBreaker);
            circuitBreaker.setMapping(mapping);
            return circuitBreaker;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /** Creates the body of the swrl rule for the 'CircuitBreaker' concept */
    private List<AndTriplets> createCircuitBreakerWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {
            List<Triplet> createdTriplets = new LinkedList<>();

            // (?class rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?class famix:hasName ?name)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            // e.g. regex(?name, '(\\w||\\W)*RegistryApplication$')
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> circuitBreakerWhenTriplets = new LinkedList<>();
            circuitBreakerWhenTriplets.add(new AndTriplets(createdTriplets));

            return circuitBreakerWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public CustomConcept createApiMechanismsConceptAndMapping(Set<TwoColumnGridEntry> set) {

        StringBuilder regexExp = new StringBuilder();
        regexExp.append("(\\\\w||\\\\W)*");
        for (Iterator<TwoColumnGridEntry> iterator = set.iterator(); iterator.hasNext(); ) {
            TwoColumnGridEntry apiMechanismEntry = iterator.next();
            String apiMechanismRegExpPart = String.format("%s$", apiMechanismEntry.getInfo2());
            regexExp.append(apiMechanismRegExpPart);

            // if there's another element append "|" to the regExp
            if (iterator.hasNext()) {
                regexExp.append("|");
            }
        }

        CustomConcept apiMechanism = new CustomConcept("API", "");

        List<AndTriplets> whenTriplets = createApiMechanismWhenTriplets(regexExp.toString());

        try {
            ConceptMapping mapping = new ConceptMapping(classVariable, whenTriplets, apiMechanism);
            apiMechanism.setMapping(mapping);
            return apiMechanism;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private List<AndTriplets> createApiMechanismWhenTriplets(String regexInput) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {

            List<Triplet> createdTriplets = new LinkedList<>();

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue(regexInput)));

            List<AndTriplets> apiMechanismWhenTriplets = new LinkedList<>();
            apiMechanismWhenTriplets.add(new AndTriplets(createdTriplets));

            return apiMechanismWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public CustomConcept createRuntimeEnvironmentConceptAndMapping() {

        CustomConcept runtime = new CustomConcept("RuntimeEnvironment", "");

        List<AndTriplets> whenTriplets = createRuntimeEnvironmentWhenTriplets();

        try {
            ConceptMapping mapping = new ConceptMapping(methodVariable, whenTriplets, runtime);
            runtime.setMapping(mapping);
            return runtime;
        } catch (UnsupportedObjectTypeInTriplet
                | RelationDoesNotExistException
                | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private List<AndTriplets> createRuntimeEnvironmentWhenTriplets() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        try {

            List<Triplet> createdTriplets = new LinkedList<>();

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Microservice").get()));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("definesMethod").get(),
                            methodVariable));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            methodVariable,
                            model.getRelationManager().getRelationByName("hasName").get(),
                            nameVariable));

            createdTriplets.add(
                    TripletFactory.createTriplet(
                            nameVariable,
                            model.getRelationManager().getRelationByName("matches").get(),
                            new StringValue("main")));

            List<AndTriplets> runtimeEnvironmentWhenTriplets = new LinkedList<>();
            runtimeEnvironmentWhenTriplets.add(new AndTriplets(createdTriplets));

            return runtimeEnvironmentWhenTriplets;
        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public CustomRelation createUseownRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation useown = new CustomRelation("useown", "", new LinkedList<>());

        List<Triplet> createdTriplets = new LinkedList<>();

        try {
            // (?class rdf:type architecture:Microservice)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Microservice").get()));

            // (?class2 rdf:type famix:FamixClass)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Variable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager()
                                    .getConceptByName("RuntimeEnvironment")
                                    .get()));

            // (?classPackage rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("definesMethod").get(),
                            class2Variable));

        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> useownWhenTriplets = new LinkedList<>();
        useownWhenTriplets.add(new AndTriplets(createdTriplets));

        try {
            RelationMapping mapping =
                    new RelationMapping(
                            TripletFactory.createTriplet(classVariable, useown, class2Variable),
                            useownWhenTriplets);
            useown.setMapping(mapping);
            return useown;
        } catch (UnsupportedObjectTypeInTriplet | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public CustomRelation createResideInPackageRelationAndMapping() {

        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation resideInPackage =
                new CustomRelation("resideinpackage", "", new LinkedList<>());

        List<Triplet> createdTriplets = new LinkedList<>();

        try {
            // (?package rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?package2 rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Package,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("Namespace").get()));

            // (?package famix:namespaceContains ?package2)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classPackage,
                            model.getRelationManager().getRelationByName("namespaceContains").get(),
                            class2Package));

        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(createdTriplets));

        try {
            RelationMapping mapping =
                    new RelationMapping(
                            TripletFactory.createTriplet(
                                    class2Package, resideInPackage, classPackage),
                            resideInWhenTriplets);
            resideInPackage.setMapping(mapping);
            return resideInPackage;
        } catch (UnsupportedObjectTypeInTriplet | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public CustomRelation createUseRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation use = new CustomRelation("use", "", new LinkedList<>());

        List<Triplet> createdTriplets = new LinkedList<>();

        try {
            // (?package rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?package2 rdf:type famix:Namespace)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            class2Variable,
                            model.getRelationManager().getRelationByName("is-of-type").get(),
                            model.getConceptManager().getConceptByName("FamixClass").get()));

            // (?package famix:namespaceContains ?package2)
            createdTriplets.add(
                    TripletFactory.createTriplet(
                            classVariable,
                            model.getRelationManager().getRelationByName("imports").get(),
                            class2Variable));

        } catch (UnsupportedObjectTypeInTriplet e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<AndTriplets> useWhenTriplets = new LinkedList<>();
        useWhenTriplets.add(new AndTriplets(createdTriplets));

        try {
            RelationMapping mapping =
                    new RelationMapping(
                            TripletFactory.createTriplet(classVariable, use, class2Variable),
                            useWhenTriplets);
            use.setMapping(mapping);
            return use;
        } catch (UnsupportedObjectTypeInTriplet | UnrelatedMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
