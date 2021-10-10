package org.archcnl.domain.input.model.architecturerules;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

public class ArchitectureRuleManager {

    private List<ArchitectureRule> architectureRules;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ArchitectureRuleManager() {
        architectureRules = new LinkedList<>();
    }

    public void addArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.add(architectureRule);
        propertyChangeSupport.firePropertyChange("newArchRule", null, architectureRule);
    }

    public void addAllArchitectureRules(List<ArchitectureRule> architectureRules) {
        architectureRules.stream().forEach(this::addArchitectureRule);
    }

    public void deleteArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.remove(architectureRule);
    }

    public List<ArchitectureRule> getArchitectureRules() {
        return architectureRules;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
