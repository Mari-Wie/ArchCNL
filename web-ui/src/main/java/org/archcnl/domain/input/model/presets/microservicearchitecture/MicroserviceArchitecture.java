package org.archcnl.domain.input.model.presets.microservicearchitecture;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
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

    private ArchitectureRuleManager ruleManager;
    private ConceptManager conceptManager;
    private RelationManager relationManager;

    private Set<CustomConcept> architecturalStyleConcepts;
    private Set<CustomRelation> architecturalStyleRelations;

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

    @Override
    public void createRulesAndMappings(
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager) {
        this.ruleManager = ruleManager;
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;

        templates =
                new MicroserviceArchitectureTemplateManager(
                        ruleManager, conceptManager, relationManager);

        createMappings();
        createArchitecturalRules();
    }

    /** Add the architectural rules that are known. */
    @Override
    public void createArchitecturalRules() {
        ruleManager.addAllArchitectureRules(architectureRules);
    }

    /** Create the mappings for the concepts that are not null. */
    @Override
    public void createMappings() {

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
        if (apiMechanisms != null) {
            createApiMechanisms(apiMechanisms);
        }

        // RuntimeEnvironment
        createRuntimeEnviornmentConceptAndMapping();
        createUseOwnRelationAndMapping();
    }

    private void createCircuitBreaker(String circuitBreakerImportClassName) {
        CustomConcept circuitBreaker =
                templates.createCircuitBreakerConceptAndMapping(circuitBreakerImportClassName);
        if (circuitBreaker != null) {
            try {
                addConcept(circuitBreaker);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMicroserviceApp(String msAppPackageStructure) {
        CustomConcept app = templates.createMicroserviceAppConceptAndMapping(msAppPackageStructure);
        if (app != null) {
            try {
                addConcept(app);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createApiGateway(String apiGatewayPackageName) {
        CustomConcept gateway = templates.createApiGatewayConceptAndMapping(apiGatewayPackageName);
        if (gateway != null) {
            // add to known concepts
            try {
                addConcept(gateway);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createResideInPackageRelationAndMapping() {
        CustomRelation resideInPackage = templates.createResideInPackageRelationAndMapping();
        if (resideInPackage != null) {
            try {
                addRelation(resideInPackage);
            } catch (RelationAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createUseRelationAndMapping() {
        CustomRelation use = templates.createUseRelationAndMapping();
        if (use != null) {
            try {
                addRelation(use);
            } catch (RelationAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRuntimeEnviornmentConceptAndMapping() {
        CustomConcept runtimeEnvironment = templates.createRuntimeEnvironmentConceptAndMapping();
        if (runtimeEnvironment != null) {
            try {
                addConcept(runtimeEnvironment);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createUseOwnRelationAndMapping() {
        CustomRelation useown = templates.createUseownRelationAndMapping();
        try {
            addRelation(useown);
        } catch (RelationAlreadyExistsException | UnrelatedMappingException e) {
            e.printStackTrace();
        }
    }

    private void createDataBaseAbstractions(Set<TwoColumnGridEntry> set) {
        CustomConcept abstractions = templates.createDbAccessAbstractionConceptAndMapping(set);
        if (abstractions != null) {
            try {
                addConcept(abstractions);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createHaveownRelationAndMapping() {
        CustomRelation haveown = templates.createHaveownRelationAndMapping();
        try {
            addRelation(haveown);
        } catch (RelationAlreadyExistsException | UnrelatedMappingException e) {
            e.printStackTrace();
        }
    }

    private void createMicroservices(Set<TwoColumnGridEntry> services) {
        CustomConcept microservices = templates.createMicroservicesConceptAndMapping(services);
        if (microservices != null) {
            try {
                addConcept(microservices);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createApiMechanisms(Set<TwoColumnGridEntry> set) {
        CustomConcept apiMechanisms = templates.createApiMechanismsConceptAndMapping(set);
        if (apiMechanisms != null) {
            try {
                addConcept(apiMechanisms);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createServiceRegistry(String serviceRegistryClassName) {
        CustomConcept registry =
                templates.createServiceRegistryConceptAndMapping(serviceRegistryClassName);
        if (registry != null) {
            // add to known concepts
            try {
                addConcept(registry);
            } catch (ConceptAlreadyExistsException | UnrelatedMappingException e) {
                e.printStackTrace();
            }
        }
    }

    private void createRegisterinRelation(String registryImportName) {
        CustomRelation registerin =
                templates.createRegisterinRelationAndMapping(registryImportName);
        if (registerin != null) {
            try {
                addRelation(registerin);
            } catch (RelationAlreadyExistsException | UnrelatedMappingException e) {
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

    /**
     * Adds the CustomConcept conceptManager so that they are known.
     *
     * @throws UnrelatedMappingException
     * @throws ConceptAlreadyExistsException
     */
    private void addConcept(CustomConcept concept)
            throws ConceptAlreadyExistsException, UnrelatedMappingException {
        conceptManager.addOrAppend(concept);
    }

    /**
     * Adds the CustomRelation to the relationManager so that they are known.
     *
     * @throws UnrelatedMappingException
     * @throws RelationAlreadyExistsException
     */
    private void addRelation(CustomRelation relation)
            throws RelationAlreadyExistsException, UnrelatedMappingException {
        relationManager.addToParent(relation, "Custom Relations");
    }

    // getter for concepts
    public Set<CustomConcept> getArchitecturalStyleConcepts() {
        return architecturalStyleConcepts;
    }

    public Set<CustomRelation> getArchitecturalStyleRelations() {
        return architecturalStyleRelations;
    }

    // getter & setter for variable parts
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
