package org.archcnl.domain.input.model.presets.microservicearchitecture;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.common.TwoColumnGridEntry;

/**
 * @author Hendrik Zevenhuizen
 *     <p>Model class for the microservice architectural style.
 */
public class MicroserviceArchitecture {

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

    /** Adds all the rules for the microservice architectural style. */
    public void createRulesAndMappings() {
        initializeRulesAndMappings();
        RulesConceptsAndRelations model = RulesConceptsAndRelations.getInstance();

//        ArchitectureRule serviceRegistryRule = new ArchitectureRule(SERVICE_REGISTRY_RULE);
//        ArchitectureRule decentralizedPersistenceRule =
//                new ArchitectureRule(DECENTRALIZED_PERSISTENCE_RULE);
//        ArchitectureRule apiGatewayRule = new ArchitectureRule(APIGATEWAY_RULE);
//        ArchitectureRule circuitBreakerRule = new ArchitectureRule(CIRCUITBREAKER_RULE);
//        ArchitectureRule apiAccessRule = new ArchitectureRule(API_ACCESS_RULE);
//        ArchitectureRule runtimeEnvironmentRule = new ArchitectureRule(RUNTIME_ENV_RULE);
//
//        architectureRules.add(serviceRegistryRule);
//        architectureRules.add(decentralizedPersistenceRule);
//        architectureRules.add(apiGatewayRule);
//        architectureRules.add(circuitBreakerRule);
//        architectureRules.add(apiAccessRule);
//        architectureRules.add(runtimeEnvironmentRule);

        // add all rules
        model.getArchitectureRuleManager().addAllArchitectureRules(this.architectureRules);
    }

    private void initializeRulesAndMappings() {

        // create Template manager
        templates = new MicroserviceArchitectureTemplateManager();

        // microservices
        createMicroservices(this.microservices);

        // ServiceRegistry
        createServiceRegistry(this.serviceRegistryClassName);
        createRegisterinRelation(this.registryImportName);

        // Centralized persistence
        createHaveownRelationAndMapping();
        createDataBaseAbstractions(this.dbAccessAbstractions);

        // API Gateway
        createResideInPackageRelationAndMapping();
        createApiGateway(this.apiGatewayPackageName);
        createMicroserviceApp(this.msAppPackageStructure);

        // Circuit Breaker
        createUseRelationAndMapping();
        createCircuitBreaker(this.circuitBreakerImportClassName);

        // API Mechanisms
        createApiMechanisms(this.apiMechanisms);

        // RuntimeEnvironment
        createRuntimeEnviornmentConceptAndMapping();
        createUseOwnRelationAndMapping();
    }

    private void createCircuitBreaker(String circuitBreakerImportClassName) {
        CustomConcept circuitBreaker =
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
        CustomConcept app = templates.createMicroserviceAppConceptAndMapping(msAppPackageStructure);

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
