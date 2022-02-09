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
    private ArrayList<RuleComponentInterface> verbComponentList;
    private boolean showAndOrComponent = true;
    private String currentSubjectSelection;

    /**
     * There are three possible verb component, depending on what descriptor is chosen in the
     * subject component. a) If ... b) Fact: ... c) Every/Only/No/Nothing ...
     */
    public VerbComponent() {
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);
        verbComponentList = new ArrayList<>();
        currentSubjectSelection = "";
    }

    /**
     * Called by DetermineVerbComponentEvent. Event is fired when a different descriptor is selected
     * in the subject component.
     *
     * @param selectedDescriptor the value of the first ComboBox of the subject component.
     */
    public void determineVerbComponent(String selectedDescriptor) {
        // TODO Quality-of-Life: also return when the new descriptor doesn't change the verb
        // component
        if (currentSubjectSelection.equals(selectedDescriptor)) {
            return;
        }

        removeAll();
        verbComponentList.clear();
        add(new Label("Rule Statement"));

        if (selectedDescriptor.equals("If")
                || selectedDescriptor.equals("If a")
                || selectedDescriptor.equals("If an")) {
            IfVerbComponent verbComponent = new IfVerbComponent();
            verbComponentList.add(verbComponent);
            add(verbComponent);
        } else if (selectedDescriptor.equals("Fact:")) {
            FactVerbComponent verbComponent = new FactVerbComponent();
            verbComponentList.add(verbComponent);
            add(verbComponent);
        } else {
            EveryOnlyNoVerbComponent verbComponent = new EveryOnlyNoVerbComponent(false);
            verbComponent.addListener(
                    ShowAndOrBlockEvent.class,
                    event -> showAndOrComponent(event.getShowAndOrBlock()));
            verbComponentList.add(verbComponent);
            add(verbComponent);
            addAndOrVerbComponent();
        }
        currentSubjectSelection = selectedDescriptor;
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
