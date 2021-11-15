package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.RulesOrMappingEditorView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListView;

public abstract class MappingEditorView extends RulesOrMappingEditorView
        implements MappingEditorContract.View {

    private static final long serialVersionUID = 156879235315976468L;
    private VerticalLayout content = new VerticalLayout();
    protected MappingEditorContract.Presenter<View> presenter;
    protected TextField mappingName;
    protected TextField description;

    protected MappingEditorView(
            MappingEditorContract.Presenter<View> presenter,
            InputContract.Remote inputRemote,
            String mappingType) {
        this.presenter = presenter;
        setHeightFull();
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        AndTripletsEditorPresenter andTripletsEditorPresenter =
                new AndTripletsEditorPresenter(
                        presenter.getVariableManager(), presenter, new AndTriplets());
        content.add(new AndTripletsEditorView(andTripletsEditorPresenter));

        Label title = new Label("Create or edit a " + mappingType);
        Button closeButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> inputRemote.switchToArchitectureRulesView());
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
                    presenter.nameHasChanged(event.getValue());
                });
        add(mappingName);

        description = new TextField("Description");
        description.setWidthFull();
        description.setPlaceholder("What does this " + mappingType + " represent?");
        description.addValueChangeListener(
                event -> {
                    presenter.descriptionHasChanged(event.getValue());
                });
        add(description);

        // TODO: add used in functionality
        VariableListPresenter variableListPresenter =
                new VariableListPresenter(presenter.getVariableManager());
        add(new VariableListView(variableListPresenter));

        add(new Label("When"));
        add(content);
        add(new Label("Then"));
        addThenTripletView();

        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(new Button("Done", click -> presenter.doneButtonClicked(inputRemote)));
        buttonRow.add(new Button("Cancel", click -> inputRemote.switchToArchitectureRulesView()));
        add(buttonRow);
        this.presenter.setView(this);
    }

    @Override
    public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView) {
        content.remove((Component) andTripletsView);
        if (content.getComponentCount() == 0) {
            presenter.lastAndTripletsDeleted();
        }
    }

    @Override
    public List<AndTripletsEditorPresenter> getAndTripletsPresenters() {
        return content.getChildren()
                .filter(AndTripletsEditorView.class::isInstance)
                .map(AndTripletsEditorView.class::cast)
                .map(AndTripletsEditorView::getPresenter)
                .filter(AndTripletsEditorPresenter.class::isInstance)
                .map(AndTripletsEditorPresenter.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public void updateNameField(String newName) {
        mappingName.setValue(newName);
    }

    @Override
    public void showNameFieldErrorMessage(String message) {
        mappingName.setErrorMessage(message);
        mappingName.setInvalid(true);
    }

    @Override
    public int getIndexOf(AndTripletsEditorContract.View andTripletsView) {
        return content.indexOf((Component) andTripletsView);
    }

    @Override
    public void addAndTripletsView(AndTripletsEditorView andTripletsView) {
        content.add(andTripletsView);
    }

    @Override
    public void addAndTripletsViewAtIndex(int index, AndTripletsEditorView andTripletsView) {
        content.addComponentAtIndex(index, andTripletsView);
    }

    @Override
    public void clearContent() {
        content.removeAll();
    }

    @Override
    public void updateDescription(String newDescription) {
        description.setValue(newDescription);
    }

    @Override
    public String getDescription() {
        return description.getValue();
    }

    protected abstract void addThenTripletView();
}
