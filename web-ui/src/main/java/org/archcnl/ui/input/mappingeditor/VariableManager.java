package org.archcnl.ui.input.mappingeditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.Variable;

public class VariableManager {

    private List<Variable> variables;
    private PropertyChangeSupport propertyChangeSupport;

    public VariableManager() {
        variables = new LinkedList<>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addVariable(Variable variable) throws VariableAlreadyExistsException {
        if (!doesVariableExist(variable)) {
            propertyChangeSupport.firePropertyChange(
                    "variables",
                    variables,
                    Stream.concat(variables.stream(), Stream.of(variable))
                            .collect(Collectors.toList()));
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
