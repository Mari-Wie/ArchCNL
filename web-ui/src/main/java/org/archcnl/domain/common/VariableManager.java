package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;

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

    public Variable getVariableByName(String name) throws VariableDoesNotExistException {
        return variables.stream()
                .filter(variable -> name.equals(variable.getName()))
                .findAny()
                .orElseThrow(() -> new VariableDoesNotExistException(name));
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
