package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.AddAndOrVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.RemoveAndOrVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.ShowAndOrBlockEvent;

public class VerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private ArrayList<RuleComponentInterface> verbComponentList = new ArrayList<>();
    private boolean showAndOrComponent = true;
    private String currentSubjectSelection = "If";

    /**
     * There are three possible verb component, depending on what descriptor is chosen in the
     * subject component. a) If ... b) Fact: ... c) Every/Only/No/Nothing ...
     */
    public VerbComponent() {
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);

        determineVerbComponent("");
    }

    /**
     * Called by DetermineVerbComponentEvent. Event is fired when a different descriptor is selected
     * in the subject component.
     */
    public void determineVerbComponent(String selectedDescriptor) {
        if (determineState(selectedDescriptor) == determineState(currentSubjectSelection)) {
            return;
        }

        removeAll();
        verbComponentList.clear();
        add(new Label("Rule Statement"));

        switch (selectedDescriptor) {
            case "If":
            case "If a":
            case "If an":
                IfVerbComponent ifVerbComponent = new IfVerbComponent();
                verbComponentList.add(ifVerbComponent);
                add(ifVerbComponent);
                break;
            case "Fact:":
                FactVerbComponent factVerbComponent = new FactVerbComponent();
                verbComponentList.add(factVerbComponent);
                add(factVerbComponent);
                break;
            default:
                EveryOnlyNoVerbComponent defaultVerbComponent = new EveryOnlyNoVerbComponent(false);
                defaultVerbComponent.addListener(
                        ShowAndOrBlockEvent.class,
                        event -> showAndOrComponent(event.getShowAndOrBlock()));
                verbComponentList.add(defaultVerbComponent);
                add(defaultVerbComponent);
                addAndOrVerbComponent();
                break;
        }
        currentSubjectSelection = selectedDescriptor;
    }

    private int determineState(String subjectDescriptor) {
        int currentState = 0;
        switch (subjectDescriptor) {
            case "If":
            case "If a":
            case "If an":
                currentState = 1;
                break;
            case "Fact:":
                currentState = 2;
                break;
            default:
                currentState = 3;
                break;
        }
        return currentState;
    }

    private void showAndOrComponent(boolean showComponent) {
        if (!showComponent) {
            ArrayList<RuleComponentInterface> verbComponentsToRemoveList = new ArrayList<>();
            verbComponentList.stream()
                    .skip(1)
                    .forEach(
                            item -> {
                                verbComponentsToRemoveList.add(item);
                                remove((Component) item);
                            });
            verbComponentList.removeAll(verbComponentsToRemoveList);
        } else if (verbComponentList.size() < 2) {
            addAndOrVerbComponent();
        }
        showAndOrComponent = showComponent;
    }

    private void addAndOrVerbComponent() {
        EveryOnlyNoVerbComponent andOrVerbComponent = new EveryOnlyNoVerbComponent(true);
        andOrVerbComponent.addListener(
                AddAndOrVerbComponentEvent.class, event -> addAndOrVerbComponent());
        andOrVerbComponent.addListener(
                RemoveAndOrVerbComponentEvent.class,
                event -> removeAndOrVerbComponent(event.getSource()));
        add(andOrVerbComponent);
        verbComponentList.add(andOrVerbComponent);
    }

    private void removeAndOrVerbComponent(EveryOnlyNoVerbComponent source) {
        verbComponentList.remove(source);
        remove((Component) source);
        if (verbComponentList.size() == 1 && showAndOrComponent) {
            addAndOrVerbComponent();
        }
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        for (RuleComponentInterface ruleComponent : verbComponentList) {
            sBuilder.append(ruleComponent.getRuleString());
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
