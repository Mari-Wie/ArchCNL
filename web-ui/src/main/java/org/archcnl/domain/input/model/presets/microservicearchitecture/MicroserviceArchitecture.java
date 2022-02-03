package org.archcnl.domain.input.model.presets.microservicearchitecture;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.common.TwoColumnGridEntry;

/**
 * Model class of the microservice architectural style. The properties represent the variable parts
 * of the architecture. Objects can be created by passing an instance of {@link
 * MicroserviceArchitectureBuilder} to the constructor which will create the
 * MicroserviceArchitecture with only the properties the builder contains.
 */
public class MicroserviceArchitecture implements ArchitecturalStyle {

    private MicroserviceArchitectureTemplateManager templates;

    // rules
    private List<ArchitectureRule> architectureRules;

    private String serviceRegistryClassName;
    private String registryImportName;
    private String apiGatewayPackageName;
    private String msAppPackageStructure;
    private String circuitBreakerImportClassName;

    private Set<TwoColumnGridEntry> microservices;
    private Set<TwoColumnGridEntry> apiMechanisms;
    private Set<TwoColumnGridEntry> dbAccessAbstractions;

    public MicroserviceArchitecture(MicroserviceArchitectureBuilder builder) {
        this.serviceRegistryClassName = builder.getServiceRegistryClassName();
        this.registryImportName = builder.getRegistryImportName();
        this.apiGatewayPackageName = builder.getApiGatewayPackageName();
        this.msAppPackageStructure = builder.getMsAppPackageStructure();
        this.circuitBreakerImportClassName = builder.getCircuitBreakerImportClassName();

        this.microservices = builder.getMicroservices();
        this.apiMechanisms = builder.getApiMechanisms();
        this.dbAccessAbstractions = builder.getDbAccessAbstractions();

        architectureRules = new LinkedList<>();
    }

    /** Adds all the mappings and rules for the microservice architectural style. */
    @Override
    public void createRulesAndMappings() {
        createMappings();
        createArchitecturalRules();
    }

    /** Add the architectural rules that are known. */
    @Override
    public void createArchitecturalRules() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
        model.getArchitectureRuleManager().addAllArchitectureRules(architectureRules);
    }

    /** Create the mappings by using the template manager */
    @Override
    public void createMappings() {

        // create Template manager
        templates = new MicroserviceArchitectureTemplateManager();

        // microservices
        if (microservices != null) {
            createMicroservices(microservices);
        }

        // ServiceRegistry
        if (serviceRegistryClassName != null && registryImportName != null) {
            createServiceRegistry(serviceRegistryClassName);
            createRegisterinRelation(registryImportName);
        }

        // Centralized persistence
        if (dbAccessAbstractions != null) {
            createHaveownRelationAndMapping();
            createDataBaseAbstractions(dbAccessAbstractions);
        }

        // API Gateway
        if (apiGatewayPackageName != null && msAppPackageStructure != null) {
            createResideInPackageRelationAndMapping();
            createApiGateway(apiGatewayPackageName);
            createMicroserviceApp(msAppPackageStructure);
        }

        // Circuit Breaker
        if (circuitBreakerImportClassName != null) {
            createUseRelationAndMapping();
            createCircuitBreaker(circuitBreakerImportClassName);
        }

        // API Mechanisms
        if (circuitBreakerImportClassName != null) {
            createApiMechanisms(apiMechanisms);
        }

        // RuntimeEnvironment
        createRuntimeEnviornmentConceptAndMapping();
        createUseOwnRelationAndMapping();
    }

    private void createCircuitBreaker(String circuitBreakerImportClassName) {
        Concept circuitBreaker =
                templates.createCircuitBreakerConceptAndMapping(circuitBreakerImportClassName);

        if (circuitBreaker != null) {
            RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
            try {
                // add to known concepts
                model.getConceptManager().addConcept(circuitBreaker);
            } catch (ConceptAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMicroserviceApp(String msAppPackageStructure) {
        Concept app = templates.createMicroserviceAppConceptAndMapping(msAppPackageStructure);

        if (app != null) {
            RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
            try {
                // add to known concepts
                model.getConceptManager().addConcept(app);
            } catch (ConceptAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    private void createApiGateway(String apiGatewayPackageName) {
        CustomConcept gateway = templates.createApiGatewayConceptAndMapping(apiGatewayPackageName);

        if (gateway != null) {
            RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
            try {
                // add to known concepts
                model.getConceptManager().addConcept(gateway);
            } catch (ConceptAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    private void createResideInPackageRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation resideInPackage = templates.createResideInPackageRelationAndMapping();

        if (resideInPackage != null) {
            try {
                model.getRelationManager().addRelation(resideInPackage);
            } catch (RelationAlreadyExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void createUseRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation use = templates.createUseRelationAndMapping();

        if (use != null) {
            try {
                model.getRelationManager().addRelation(use);
            } catch (RelationAlreadyExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void createRuntimeEnviornmentConceptAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomConcept runtimeEnvironment = templates.createRuntimeEnvironmentConceptAndMapping();

        if (runtimeEnvironment != null) {
            try {
                model.getConceptManager().addConcept(runtimeEnvironment);
            } catch (ConceptAlreadyExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void createUseOwnRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation useown = templates.createUseownRelationAndMapping();

        try {
            model.getRelationManager().addRelation(useown);
        } catch (RelationAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    private void createDataBaseAbstractions(Set<TwoColumnGridEntry> set) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
        CustomConcept abstractions = templates.createDbAccessAbstractionConceptAndMapping(set);
        if (abstractions != null) {
            try {
                model.getConceptManager().addConcept(abstractions);
            } catch (ConceptAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    private void createHaveownRelationAndMapping() {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

        CustomRelation haveown = templates.createHaveownRelationAndMapping();

        try {
            model.getRelationManager().addRelation(haveown);
        } catch (RelationAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    /** Creates the Microservice architecture concept and the respective mapping. */
    private void createMicroservices(Set<TwoColumnGridEntry> services) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
        CustomConcept microservices = templates.createMicroservicesConceptAndMapping(services);
        if (microservices != null) {
            try {
                model.getConceptManager().addConcept(microservices);
            } catch (ConceptAlreadyExistsException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void createApiMechanisms(Set<TwoColumnGridEntry> set) {
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
        CustomConcept apiMechanisms = templates.createApiMechanismsConceptAndMapping(set);
        if (apiMechanisms != null) {
            try {
                model.getConceptManager().addConcept(apiMechanisms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createServiceRegistry(String serviceRegistryClassName) {
        CustomConcept registry =
                templates.createServiceRegistryConceptAndMapping(serviceRegistryClassName);
        if (registry != null) {
            RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
            try {
                // add to known concepts
                model.getConceptManager().addConcept(registry);
            } catch (ConceptAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRegisterinRelation(String registryImportName) {
        CustomRelation registerin =
                templates.createRegisterinRelationAndMapping(registryImportName);
        if (registerin != null) {
            RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();
            try {
                model.getRelationManager().addRelation(registerin);
            } catch (RelationAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ArchitectureRule> getArchitectureRules() {
        return architectureRules;
    }

    public void setArchitectureRules(Set<String> set) {
        for (String string : set) {
            architectureRules.add(new ArchitectureRule(string));
        }
    }

    // getter & setter
    public String getServiceRegistryClassName() {
        return serviceRegistryClassName;
    }

    public void setServiceRegistryClassName(String serviceRegistryClassName) {
        this.serviceRegistryClassName = serviceRegistryClassName;
    }

    public String getRegistryImportName() {
        return registryImportName;
    }

    public void setRegistryImportName(String registryImportName) {
        this.registryImportName = registryImportName;
    }

    public String getApiGatewayPackageName() {
        return apiGatewayPackageName;
    }

    public void setApiGatewayPackageName(String apiGatewayPackageName) {
        this.apiGatewayPackageName = apiGatewayPackageName;
    }

    public String getMsAppPackageStructure() {
        return msAppPackageStructure;
    }

    public void setMsAppPackageStructure(String msAppPackageStructure) {
        this.msAppPackageStructure = msAppPackageStructure;
    }

    public String getCircuitBreakerImportClassName() {
        return circuitBreakerImportClassName;
    }

    public void setCircuitBreakerImportClassName(String circuitBreakerImportClassName) {
        this.circuitBreakerImportClassName = circuitBreakerImportClassName;
    }

    public Set<TwoColumnGridEntry> getMicroservices() {
        return microservices;
    }

    public void setMicroservices(Set<TwoColumnGridEntry> microservices) {
        this.microservices = microservices;
    }

    public Set<TwoColumnGridEntry> getApiMechanisms() {
        return apiMechanisms;
    }

    public void setApiMechanisms(Set<TwoColumnGridEntry> apiMechanisms) {
        this.apiMechanisms = apiMechanisms;
    }

    public Set<TwoColumnGridEntry> getDbAccessAbstractions() {
        return dbAccessAbstractions;
    }

    public void setDbAccessAbstractions(Set<TwoColumnGridEntry> dbAccessAbstractions) {
        this.dbAccessAbstractions = dbAccessAbstractions;
    }
}
