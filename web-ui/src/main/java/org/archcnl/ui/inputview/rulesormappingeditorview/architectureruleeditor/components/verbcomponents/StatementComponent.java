package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.AddAndOrVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.RemoveAndOrVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.ShowAndOrBlockEvent;

public class StatementComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private ArrayList<RuleComponentInterface> verbComponentList = new ArrayList<>();
    private boolean showAndOrComponent = true;
    private String currentSubjectSelection = "If";

    /**
     * There are three possible verb component, depending on what descriptor is chosen in the
     * subject component. a) If ... b) Fact: ... c) Every/Only/No/Nothing ...
     */
    public StatementComponent() {
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);

        determineVerbComponent("");
    }

    /**
     * Called by DetermineVerbComponentEvent. Event is fired when a different descriptor is selected
     * in the subject component. Depending on the selection one of three verb components will be
     * shown.
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
                IfStatementComponent ifVerbComponent = new IfStatementComponent();
                ifVerbComponent.addListener(
                        RelationListUpdateRequestedEvent.class, this::fireEvent);
                ifVerbComponent.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
                verbComponentList.add(ifVerbComponent);
                add(ifVerbComponent);
                break;
            case "Fact:":
                FactStatementComponent factVerbComponent = new FactStatementComponent();
                factVerbComponent.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
                factVerbComponent.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
                verbComponentList.add(factVerbComponent);
                add(factVerbComponent);
                break;
            default:
                DefaultStatementComponent defaultVerbComponent = new DefaultStatementComponent(false);
                defaultVerbComponent.addListener(
                        RelationListUpdateRequestedEvent.class, this::fireEvent);
                defaultVerbComponent.addListener(
                        ConceptListUpdateRequestedEvent.class, this::fireEvent);
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

    private void addAndOrVerbComponent() {
        DefaultStatementComponent andOrVerbComponent = new DefaultStatementComponent(true);
        andOrVerbComponent.addListener(
                AddAndOrVerbComponentEvent.class, event -> addAndOrVerbComponent());
        andOrVerbComponent.addListener(
                RemoveAndOrVerbComponentEvent.class,
                event -> removeAndOrVerbComponent(event.getSource()));

        verbComponentList.add(andOrVerbComponent);
        add(andOrVerbComponent);
    }

    private void removeAndOrVerbComponent(DefaultStatementComponent source) {
        verbComponentList.remove(source);
        remove((Component) source);
        if (verbComponentList.size() == 1 && showAndOrComponent) {
            addAndOrVerbComponent();
        }
    }

    /**
     * Some paths of the defaultVerbcomponent don't allow and/or additions. The AndOrBlock must thus
     * be removed. Otherwise an AndOrBlock should always be shown, even if all previous AndOrBlocks
     * get deleted.
     */
    private void showAndOrComponent(boolean showAndOrBlock) {
        if (!showAndOrBlock) {
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
        showAndOrComponent = showAndOrBlock;
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
        return getEventBus().addListener(eventType, listener);
    }
}
