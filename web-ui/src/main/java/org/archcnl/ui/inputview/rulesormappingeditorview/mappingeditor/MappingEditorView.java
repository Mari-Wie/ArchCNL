package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
<<<<<<< master:web-ui/src/main/java/org/archcnl/ui/inputview/rulesormappingeditorview/mappingeditor/MappingEditorView.java
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCancelButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCloseButtonClicked;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDoneButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingNameFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.variablelist.VariableListView;
=======
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.input.RulesOrMappingEditorView;
import org.archcnl.ui.input.mappingeditor.events.MappingCancelButtonClickedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingCloseButtonClicked;
import org.archcnl.ui.input.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingDoneButtonClickedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingNameFieldChangedEvent;
>>>>>>> AndTripletsEditor moved to common package:web-ui/src/main/java/org/archcnl/ui/input/mappingeditor/MappingEditorView.java

public abstract class MappingEditorView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = 156879235315976468L;
    private VerticalLayout content = new VerticalLayout();
    private VariableListView variableListView;
    protected TextField mappingName;
    protected TextField description;

    protected MappingEditorView(String mappingType, AndTripletsEditorView emptyAndTripletsView) {
        setHeightFull();
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        content.add(emptyAndTripletsView);

        Label title = new Label("Create or edit a " + mappingType);
        Button closeButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> fireEvent(new MappingCloseButtonClicked(this, true)));
        HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);
        titleBar.setWidthFull();
        title.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(titleBar);

        mappingName = new TextField("Name");
        mappingName.setPlaceholder("Unique name");
        mappingName.addValueChangeListener(
                event -> {
                    mappingName.setInvalid(false);
                    fireEvent(new MappingNameFieldChangedEvent(this, true, event.getValue()));
                });
        add(mappingName);

        description = new TextField("Description");
        description.setWidthFull();
        description.setPlaceholder("What does this " + mappingType + " represent?");
        description.addValueChangeListener(
                event ->
                        fireEvent(
                                new MappingDescriptionFieldChangedEvent(
                                        this, true, event.getValue())));
        add(description);

        // TODO: add "used in" functionality
        variableListView = new VariableListView();
        add(variableListView);

        add(new Label("When"));
        add(content);
        add(new Label("Then"));
        addThenTripletView();

        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(
                new Button(
                        "Done", click -> fireEvent(new MappingDoneButtonClickedEvent(this, true))));
        buttonRow.add(
                new Button(
                        "Cancel",
                        click -> fireEvent(new MappingCancelButtonClickedEvent(this, true))));
        add(buttonRow);
    }

    public void deleteAndTripletsView(AndTripletsEditorView andTripletsView) {
        content.remove((Component) andTripletsView);
    }

    public void updateNameField(String newName) {
        mappingName.setValue(newName);
    }

    public void showNameFieldErrorMessage(String message) {
        mappingName.setErrorMessage(message);
        mappingName.setInvalid(true);
    }

    public void addNewAndTripletsView(AndTripletsEditorView andTripletsView) {
        content.add(andTripletsView);
    }

    public void addNewAndTripletsViewAfter(
            AndTripletsEditorView oldAndTripletsView, AndTripletsEditorView newAndTripletsView) {
        int previousIndex = content.indexOf((Component) oldAndTripletsView);
        content.addComponentAtIndex(previousIndex + 1, newAndTripletsView);
    }

    public void clearContent() {
        content.removeAll();
    }

    public void updateDescription(String newDescription) {
        description.setValue(newDescription);
    }

    public String getDescription() {
        return description.getValue();
    }

    public VariableListView getVariableListView() {
        return variableListView;
    }

    protected abstract void addThenTripletView();

    protected abstract void updateNameFieldInThenTriplet(String newName);

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
