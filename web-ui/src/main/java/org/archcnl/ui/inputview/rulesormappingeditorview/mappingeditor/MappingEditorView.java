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
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.variablelistview.VariableListView;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCancelButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCloseButtonClicked;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDoneButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingNameFieldChangedEvent;

public abstract class MappingEditorView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = 156879235315976468L;
    private VerticalLayout content;
    private VariableListView variableListView;
    protected TextField mappingNameField;
    protected TextField descriptionField;

    protected MappingEditorView(
            final String mappingType, final AndTripletsEditorView emptyAndTripletsView) {
        setHeightFull();
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        setClassName("architecture-rules");

        add(createTitleBar(mappingType));

        initMappingNameField();
        add(mappingNameField);

        initDescriptionField(mappingType);
        add(descriptionField);

        // TODO: add "used in" functionality

        variableListView = new VariableListView();
        add(variableListView);

        final Label labelWhen = new Label("When");
        final Label labelThen = new Label("Then");
        labelWhen.setClassName("label-title");
        labelThen.setClassName("label-title");
        add(labelWhen);
        content = new VerticalLayout(emptyAndTripletsView);
        add(content);
        add(labelThen);
        addThenTripletView();

        add(createButtonRow());
    }

    public void deleteAndTripletsView(final AndTripletsEditorView andTripletsView) {
        content.remove((Component) andTripletsView);
    }

    public void updateNameField(final String newName) {
        mappingNameField.setValue(newName);
    }

    public void showNameFieldErrorMessage(final String message) {
        mappingNameField.setErrorMessage(message);
        mappingNameField.setInvalid(true);
    }

    public void addNewAndTripletsView(final AndTripletsEditorView andTripletsView) {
        content.add(andTripletsView);
    }

    public void addNewAndTripletsViewAfter(
            final AndTripletsEditorView oldAndTripletsView,
            final AndTripletsEditorView newAndTripletsView) {
        final int previousIndex = content.indexOf(oldAndTripletsView);
        content.addComponentAtIndex(previousIndex + 1, newAndTripletsView);
    }

    public void clearContent() {
        content.removeAll();
    }

    public void updateDescription(final String newDescription) {
        descriptionField.setValue(newDescription);
    }

    public String getDescription() {
        return descriptionField.getValue();
    }

    public VariableListView getVariableListView() {
        return variableListView;
    }

    private HorizontalLayout createTitleBar(String mappingType) {
        final Label title = new Label("Create or edit a " + mappingType);
        title.setClassName("card-title-box--title");
        final Button closeButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> fireEvent(new MappingCloseButtonClicked(this, true)));
        final HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);
        titleBar.setWidthFull();
        title.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return titleBar;
    }

    private void initMappingNameField() {
        mappingNameField = new TextField("Name");
        mappingNameField.setPreventInvalidInput(true);
        mappingNameField.addValueChangeListener(
                event -> {
                    mappingNameField.setInvalid(false);
                    fireEvent(new MappingNameFieldChangedEvent(this, true, event.getValue()));
                });
    }

    private void initDescriptionField(String mappingType) {
        descriptionField = new TextField("Description");
        descriptionField.setWidthFull();
        descriptionField.setPlaceholder("What does this " + mappingType + " represent?");
        descriptionField.addValueChangeListener(
                event ->
                        fireEvent(
                                new MappingDescriptionFieldChangedEvent(
                                        this, true, event.getValue())));
    }

    private HorizontalLayout createButtonRow() {
        final HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(
                new Button(
                        "Done", click -> fireEvent(new MappingDoneButtonClickedEvent(this, true))));
        buttonRow.add(
                new Button(
                        "Cancel",
                        click -> fireEvent(new MappingCancelButtonClickedEvent(this, true))));
        return buttonRow;
    }

    protected abstract void addThenTripletView();

    protected abstract void updateNameFieldInThenTriplet(String newName);

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
