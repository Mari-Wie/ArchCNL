package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class MappingEditorView extends VerticalLayout {

    /** */
    private static final long serialVersionUID = 1L;

    TextField mappingName;
    TextField description;
    WhenBlock whenBlockComponent;
    VariableList variableList;

    public MappingEditorView() {

        setHeightFull();
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        createTitleBar();
        createName();
        createDescription();
        createVariableList();
        createWhenBlock();
        createThenBlock();
        createControlRow();
    }
    private void createVariableList() {
    	variableList = new VariableList();
    	add(variableList);
    }

    private void createTitleBar() {
        Label title = new Label("Create or edit a Mappingtype");
        title.setWidthFull();

        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE), click -> {});
        HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);

        titleBar.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        titleBar.add(title, closeButton);

        add(titleBar);
    }

    private void createWhenBlock() {
        whenBlockComponent = new WhenBlock();
        whenBlockComponent.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
        whenBlockComponent.addListener(VariableSelectionEvent.class, e -> fireEvent(new VariableUpdateRequest(e.getSource(), e.isFromClient(), variableList, e.getSelectedVariableString())));

        add(whenBlockComponent);
    }

    private void createName() {
        mappingName = new TextField("Name");
        mappingName.setPlaceholder("Unique name");
        mappingName.addValueChangeListener(
                event -> {
                    mappingName.setInvalid(false);
                });
        add(mappingName);
    }

    private void createDescription() {

        description = new TextField("Description");
        description.setWidthFull();
        description.setPlaceholder("Describtion of the Concept/Relation");
        description.addValueChangeListener(event -> {});
        add(description);
    }

    public void createThenBlock() {}

    public void createControlRow() {}

    public void addNameChangedListener() {}

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
