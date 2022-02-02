package org.archcnl.ui.input.ruleeditor.components.verbcomponents;

import org.archcnl.ui.input.ruleeditor.components.RuleComponentInterface;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;

public class VerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private ArrayList<RuleComponentInterface> verbComponentList;
    private RuleComponentInterface verbComponent;

    /**
     * There are three possible verb component, depending on what descriptor is chosen in the
     * subject component. a) If ... b) Fact: ... c) Every/Only/No/Nothing ...
     */
    public VerbComponent() {
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);
        verbComponentList = new ArrayList<>();
    }

    /**
     * Called by DetermineVerbComponentEvent. Event is fired when a different descriptor is selected
     * in the subject component.
     * @param selectedDescriptor the value of the first ComboBox of the subject component.
     */
    public void determineVerbComponent(String selectedDescriptor) {
        removeAll();
        verbComponentList.clear();
        add(new Label("Rule Statement"));

        if (selectedDescriptor.equals("If")) {
            verbComponent = new IfVerbComponent();
        } else if (selectedDescriptor.equals("Fact:")) {
            verbComponent = new FactVerbComponent();
        } else {
            verbComponent = new EveryOnlyNoVerbComponent(false);     
        }
        verbComponentList.add(verbComponent);
        add((Component)verbComponent);
    }
    
    private void addAndOrVerbComponent() {
        EveryOnlyNoVerbComponent andOrVerbComponent = new EveryOnlyNoVerbComponent(true);
        add(andOrVerbComponent);
        verbComponentList.add(andOrVerbComponent);
    }

    private void removeAndOrVerbComponent() {
        RuleComponentInterface vc = verbComponentList.get(verbComponentList.size() - 1);
        verbComponentList.remove(vc);
        remove((Component) vc);
    }

    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        for (RuleComponentInterface ruleComponent : verbComponentList) {
            sBuilder.append(ruleComponent.getString());
        }
        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        System.out.println("Listener Added");
        return getEventBus().addListener(eventType, listener);
    }
}
