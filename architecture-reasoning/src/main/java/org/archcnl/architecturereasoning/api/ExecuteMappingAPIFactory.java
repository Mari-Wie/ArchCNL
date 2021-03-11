package org.archcnl.architecturereasoning.api;

import org.archcnl.architecturereasoning.impl.ExecuteMappingAPIImpl;

/** Factory for the interface ExecuteMappingAPI. */
public class ExecuteMappingAPIFactory {

    /** Returns a freshly created object of a class implementing the interface ExecuteMappingAPI. */
    public static ExecuteMappingAPI get() {
        return new ExecuteMappingAPIImpl();
    }
}
