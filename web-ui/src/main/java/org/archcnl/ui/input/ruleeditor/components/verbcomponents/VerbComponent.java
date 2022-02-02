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

    public VerbComponent() {
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);
        verbComponentList = new ArrayList<>();
    }

    public void determineVerbComponent(String selectedVerb) {
        System.out.println("Event Received");
        
        removeAll();
        add(new Label("Rule Statement"));
        if (selectedVerb.equals("If")) {
            verbComponent = new IfVerbComponent();      
            verbComponentList.add(verbComponent);
        } else if (selectedVerb.equals("Fact:")) {
            verbComponent = new FactVerbComponent();
            verbComponentList.add(verbComponent);
        } else {
            verbComponent = new EveryOnlyNoVerbComponent(false);
            RuleComponentInterface andVerbComponent = new EveryOnlyNoVerbComponent(true);
            verbComponentList.add(verbComponent);
            verbComponentList.add(andVerbComponent);
        }
        
        for (RuleComponentInterface ruleComponent : verbComponentList) {
            add((Component)ruleComponent);
        }
    }
    
    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        for (RuleComponentInterface ruleComponent : verbComponentList) {
            sBuilder.append(ruleComponent.getString());
        }
        return null;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        System.out.println("Listener Added");
        return getEventBus().addListener(eventType, listener);
    }

    //    public Registration addChangeListener(
    //            ComponentEventListener<DetermineVerbComponentEvent> listener) {
    //           return addListener(DetermineVerbComponentEvent.class, listener);
    //       }
}
