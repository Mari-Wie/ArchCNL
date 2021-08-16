package org.archcnl.webui.mappings;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;
import org.archcnl.webui.exceptions.VariableDoesNotExistException;

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
        for (Variable variable : variables) {
            if (name.equals(variable.getName())) {
                return variable;
            }
        }
        throw new VariableDoesNotExistException(name);
    }

    public boolean doesVariableExist(Variable variable) {
        for (Variable existingVariable : variables) {
            if (variable.getName().equals(existingVariable.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
