package org.archcnl.domain.common;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class VariableManager {

    private Set<Variable> variables;

    public VariableManager() {
        variables = new LinkedHashSet<>();
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public Optional<Variable> getVariableByName(String name) {
        return variables.stream().filter(variable -> name.equals(variable.getName())).findAny();
    }

    public boolean doesVariableExist(Variable variable) {
        return variables.stream()
                .anyMatch(
                        existingVariable -> variable.getName().equals(existingVariable.getName()));
    }

    public Set<Variable> getVariables() {
        return variables;
    }
}
