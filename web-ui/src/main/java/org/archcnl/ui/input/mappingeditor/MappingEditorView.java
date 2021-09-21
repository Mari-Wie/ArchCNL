package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;

public abstract class MappingEditorView extends RulesOrMappingEditorView
        implements MappingEditorContract.View {

    private static final long serialVersionUID = 156879235315976468L;

    protected MappingEditorContract.Presenter<View> presenter;

    protected TextField mappingName;

    protected MappingEditorView(
            MappingEditorContract.Presenter<View> presenter, InputView parent, String mappingType) {
        this.presenter = presenter;
        this.presenter.setView(this);

        Label title = new Label("Create or edit a " + mappingType);
        Button closeButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> parent.switchToArchitectureRulesView());
        HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);
        titleBar.setWidthFull();
        title.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(titleBar);

        mappingName = new TextField("Name");
        mappingName.setPlaceholder("Unique name");
        add(mappingName);

        // TODO: add used in and description functionality

        Label mappingHeadline = new Label("Architecture to Code Mapping");
        mappingHeadline
                .getElement()
                .setProperty(
                        "title",
                        "Is necessary to find the code elements that correspond to this"
                                + mappingType);
        add(mappingHeadline);

        AndTripletsEditorPresenter andTripletsEditorPresenter =
                new AndTripletsEditorPresenter(presenter.getVariableManager());
        add(new AndTripletsEditorView(andTripletsEditorPresenter));
    }
}
