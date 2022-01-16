package org.archcnl.ui.output.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.ui.common.VariableListView;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.input.ConceptAndRelationView;
import org.archcnl.ui.input.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.input.events.OutputViewRequestedEvent;
import org.archcnl.ui.input.events.RelationEditorRequestedEvent;
import org.archcnl.ui.output.events.ResultUpdateEvent;

public class CustomQueryView extends HorizontalLayout {

    private static final long serialVersionUID = 8233138508556215108L;
    private static final float CONTENT_RATIO = 70.0f;
    private VerticalLayout content;
    private HorizontalLayout select;
    private ConceptAndRelationView conceptAndRelationView;
    private VariableListView variableListView;
    private GridView gridView;
    private TextArea queryTextArea;

    public CustomQueryView(AndTripletsEditorView andTripletsEditorView)
            throws PropertyNotFoundException {
        super();
        initConceptAndRelationView();
        content = new VerticalLayout();
        select = new HorizontalLayout();
        variableListView = new VariableListView();
        gridView = new GridView();
        queryTextArea = new TextArea("SPARQL Query");
        queryTextArea.setWidth(100, Unit.PERCENTAGE);

        getStyle().set("overflow", "visible");
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        queryTextArea.setReadOnly(true);
        addVariableSelectionComponent();

        content.getStyle().set("overflow", "auto");
        content.setWidth(CONTENT_RATIO, Unit.PERCENTAGE);
        content.add(new Label("Create a custom query"));
        content.add(variableListView);
        content.add(new Label("Select"));
        content.add(select);
        content.add(new Label("Where"));
        content.add(andTripletsEditorView);
        content.add(new Button("Run", e -> fireEvent(new ResultUpdateEvent(this, false))));
        content.add(gridView);
        content.add(queryTextArea);

        addAndExpand(content, conceptAndRelationView);
    }

    public VariableListView getVariableListView() {
        return variableListView;
    }

    public GridView getGridView() {
        return gridView;
    }

    public List<VariableSelectionComponent> getSelectComponents() {
        return select.getChildren()
                .filter(VariableSelectionComponent.class::isInstance)
                .map(VariableSelectionComponent.class::cast)
                .collect(Collectors.toList());
    }

    private void initConceptAndRelationView() {
        conceptAndRelationView = new ConceptAndRelationView(false);
        conceptAndRelationView.setWidth(100.0f - CONTENT_RATIO, Unit.PERCENTAGE);
        conceptAndRelationView.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(RelationEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(OutputViewRequestedEvent.class, this::fireEvent);
    }

    public void addVariableSelectionComponent() {
        VariableSelectionComponent variableSelectionComponent = new VariableSelectionComponent();
        variableSelectionComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(VariableSelectedEvent.class, this::fireEvent);
        select.add(variableSelectionComponent);
    }

    public void removeVariableSelectionComponent(
            VariableSelectionComponent variableSelectionComponent) {
        select.remove(variableSelectionComponent);
    }

    public boolean isAnyVariableSelectionComponentEmpty() {
        return select.getChildren()
                .filter(VariableSelectionComponent.class::isInstance)
                .map(VariableSelectionComponent.class::cast)
                .anyMatch(VariableSelectionComponent::isEmpty);
    }

    public boolean areAtleastTwoVariableSelectionComponentsEmpty() {
        List<VariableSelectionComponent> components =
                select.getChildren()
                        .filter(VariableSelectionComponent.class::isInstance)
                        .map(VariableSelectionComponent.class::cast)
                        .collect(Collectors.toList());
        long componentsCount = components.stream().count();
        long nonEmptyComponentsCount =
                components.stream()
                        .filter(component -> component.getOptionalValue().isPresent())
                        .count();
        return componentsCount - nonEmptyComponentsCount >= 2;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
