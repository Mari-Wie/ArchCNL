package org.archcnl.domain.input.model.presets.microservicearchitecture;

/**
 * Interface to define ArchitecturalStyles with Builder Pattern. That way architectural styles can
 * be created by passing only the information that's relevant to the user.
 */
public interface ArchitecturalStyleBuilder {

    ArchitecturalStyle build();
}
