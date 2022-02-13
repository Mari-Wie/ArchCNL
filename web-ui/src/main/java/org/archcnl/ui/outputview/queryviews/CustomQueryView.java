package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.ConceptAndRelationView;
import org.archcnl.ui.common.variablelistview.VariableListView;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.outputview.queryviews.components.GridView;
import org.archcnl.ui.outputview.queryviews.events.DeleteButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinQueryButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.QueryNameUpdateRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.RunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.UpdateQueryTextButtonPressedEvent;

public class CustomQueryView extends HorizontalLayout {

    private static final long serialVersionUID = 8233138508556215108L;
    private static final float CONTENT_RATIO = 70.0f;
    private VerticalLayout content;
    private HorizontalLayout select;
    private ConceptAndRelationView conceptAndRelationView;
    private VariableListView variableListView;
    private GridView gridView;
    private TextArea queryTextArea;
    private TextField queryName;
    private Button pinButton;
    private Button deleteButton;
    private HorizontalLayout topRow;

    public CustomQueryView(AndTripletsEditorView andTripletsEditorView) {
        super();
        initConceptAndRelationView();
        content = new VerticalLayout();
        select = new HorizontalLayout();
        variableListView = new VariableListView();
        gridView = new GridView();
        queryTextArea = new TextArea("SPARQL Query");
        queryTextArea.setWidth(100, Unit.PERCENTAGE);
        queryName = new TextField("Name");
        queryName.setPlaceholder("Name of this query");
        queryName.addValueChangeListener(event -> fireNameUpdateEventIfNameNotEmpty());
        pinButton =
                new Button(
                        new Icon(VaadinIcon.PIN),
                        click -> fireEvent(new PinQueryButtonPressedEvent(this, true)));
        deleteButton =
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new DeleteButtonPressedEvent(this, true)));

        getStyle().set("overflow", "visible");
        setWidthFull();
        setHeightFull();
        queryTextArea.setReadOnly(true);
        queryTextArea.setVisible(false);
        addVariableSelectionComponent();
        Label caption = new Label("Create a custom query");
        topRow = new HorizontalLayout(caption, pinButton);
        topRow.setWidthFull();
        caption.setWidthFull();
        Button runButton = new Button("Run", e -> fireEvent(new RunButtonPressedEvent(this, true)));
        Button showQueryTextButton =
                new Button(
                        "Update query text",
                        e -> fireEvent(new UpdateQueryTextButtonPressedEvent(this, true)));
        HorizontalLayout buttonRow = new HorizontalLayout(runButton, showQueryTextButton);

        content.getStyle().set("overflow", "auto");
        content.setWidth(CONTENT_RATIO, Unit.PERCENTAGE);
        content.add(topRow);
        content.add(queryName);
        content.add(variableListView);
        content.add(new Label("Select"));
        content.add(select);
        content.add(new Label("Where"));
        content.add(andTripletsEditorView);
        content.add(buttonRow);
        content.add(gridView);
        content.add(queryTextArea);

        addAndExpand(content, conceptAndRelationView);
    }

    public void setQueryName(String name) {
        queryName.setValue(name);
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
        conceptAndRelationView.setWidth(100.0f - CustomQueryView.CONTENT_RATIO, Unit.PERCENTAGE);
        conceptAndRelationView.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(RelationGridUpdateRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(
                ConceptHierarchySwapRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(
                RelationHierarchySwapRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.update();
    }

    public void addVariableSelectionComponent() {
        final VariableSelectionComponent variableSelectionComponent =
                new VariableSelectionComponent();
        addVariableSelectionComponentsListeners(variableSelectionComponent);
        select.add(variableSelectionComponent);
    }

    public void showVariableSelectionComponents(List<VariableSelectionComponent> components) {
        select.removeAll();
        components.forEach(component -> select.add(component));
        components.forEach(component -> addVariableSelectionComponentsListeners(component));
        VariableSelectionComponent variableSelectionComponent = new VariableSelectionComponent();
        addVariableSelectionComponentsListeners(variableSelectionComponent);
        select.add(variableSelectionComponent);
    }

    private void addVariableSelectionComponentsListeners(
            VariableSelectionComponent variableSelectionComponent) {
        variableSelectionComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(VariableSelectedEvent.class, this::fireEvent);
    }

    public void removeVariableSelectionComponent(
            final VariableSelectionComponent variableSelectionComponent) {
        select.remove(variableSelectionComponent);
    }

    public void setQueryTextArea(final String queryText) {
        queryTextArea.setVisible(true);
        queryTextArea.setValue(queryText);
    }

    public boolean isAnyVariableSelectionComponentEmpty() {
        return select.getChildren()
                .filter(VariableSelectionComponent.class::isInstance)
                .map(VariableSelectionComponent.class::cast)
                .anyMatch(VariableSelectionComponent::isEmpty);
    }

    public boolean areAtleastTwoVariableSelectionComponentsEmpty() {
        final List<VariableSelectionComponent> components =
                select.getChildren()
                        .filter(VariableSelectionComponent.class::isInstance)
                        .map(VariableSelectionComponent.class::cast)
                        .collect(Collectors.toList());
        final long componentsCount = components.stream().count();
        final long nonEmptyComponentsCount =
                components.stream()
                        .filter(component -> component.getOptionalValue().isPresent())
                        .count();
        return componentsCount - nonEmptyComponentsCount >= 2;
    }

    public void replacePinButtonWithDeleteButton() {
        topRow.replace(pinButton, deleteButton);
    }

    private void fireNameUpdateEventIfNameNotEmpty() {
        if (queryName.getOptionalValue().isPresent()) {
            fireEvent(new QueryNameUpdateRequestedEvent(this, true, queryName.getValue()));
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
