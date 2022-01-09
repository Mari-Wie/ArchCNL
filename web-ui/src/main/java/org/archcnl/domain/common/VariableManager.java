package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;

public class VariableManager {

    private List<Variable> variables;

    public VariableManager() {
        variables = new LinkedList<>();
    }

    public void addVariable(Variable variable) throws VariableAlreadyExistsException {
        if (!doesVariableExist(variable)) {
            variables.add(variable);
        } else {
            throw new VariableAlreadyExistsException(variable.getName());
        }
    }

    public Optional<Variable> getVariableByName(String name) {
        return variables.stream().filter(variable -> name.equals(variable.getName())).findAny();
    }

    public boolean doesVariableExist(Variable variable) {
        return variables.stream()
                .anyMatch(
                        existingVariable -> variable.getName().equals(existingVariable.getName()));
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
