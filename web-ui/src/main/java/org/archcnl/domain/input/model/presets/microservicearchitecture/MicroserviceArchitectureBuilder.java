package org.archcnl.domain.input.model.presets.microservicearchitecture;

import java.util.Set;
import org.archcnl.ui.common.TwoColumnGridEntry;

public class MicroserviceArchitectureBuilder {
    private String serviceRegistryClassName;
    private String registryImportName;
    private String apiGatewayPackageName;
    private String msAppPackageStructure;
    private String circuitBreakerImportClassName;

    private Set<TwoColumnGridEntry> microservices;
    private Set<TwoColumnGridEntry> apiMechanisms;
    private Set<TwoColumnGridEntry> dbAccessAbstractions;

    public MicroserviceArchitecture build() {
        return new MicroserviceArchitecture(this);
    }

    public MicroserviceArchitectureBuilder withServiceRegistryClassName(String name) {
        this.serviceRegistryClassName = name;
        return this;
    }

    public MicroserviceArchitectureBuilder withRegistryImportName(String name) {
        this.registryImportName = name;
        return this;
    }

    public MicroserviceArchitectureBuilder withApiGatewayPackageName(String name) {
        this.apiGatewayPackageName = name;
        return this;
    }

    public MicroserviceArchitectureBuilder withMsAppPackageStructure(String name) {
        this.msAppPackageStructure = name;
        return this;
    }

    public MicroserviceArchitectureBuilder withCircuitBreakerImportClassName(String name) {
        this.circuitBreakerImportClassName = name;
        return this;
    }

    public MicroserviceArchitectureBuilder withMicroservices(Set<TwoColumnGridEntry> services) {
        this.microservices = services;
        return this;
    }

    public MicroserviceArchitectureBuilder withApiMechanisms(
            Set<TwoColumnGridEntry> apiMechanisms) {
        this.apiMechanisms = apiMechanisms;
        return this;
    }

    public MicroserviceArchitectureBuilder withDbAccessAbstractions(
            Set<TwoColumnGridEntry> dbAccessAbstractions) {
        this.dbAccessAbstractions = dbAccessAbstractions;
        return this;
    }

    public String getServiceRegistryClassName() {
        return serviceRegistryClassName;
    }

    public String getRegistryImportName() {
        return registryImportName;
    }

    public String getApiGatewayPackageName() {
        return apiGatewayPackageName;
    }

    public String getMsAppPackageStructure() {
        return msAppPackageStructure;
    }

    public String getCircuitBreakerImportClassName() {
        return circuitBreakerImportClassName;
    }

    public Set<TwoColumnGridEntry> getMicroservices() {
        return microservices;
    }

    public Set<TwoColumnGridEntry> getApiMechanisms() {
        return apiMechanisms;
    }

    public Set<TwoColumnGridEntry> getDbAccessAbstractions() {
        return dbAccessAbstractions;
    }
}
