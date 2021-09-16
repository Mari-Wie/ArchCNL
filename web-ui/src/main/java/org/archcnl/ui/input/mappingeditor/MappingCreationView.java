package org.archcnl.ui.input.mappingeditor;

import org.archcnl.ui.input.InputView;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class MappingCreationView extends RulesOrMappingCreationView
        implements MappingCreationContract.View<MappingCreationContract.Presenter> {

    private static final long serialVersionUID = 156879235315976468L;

    private MappingCreationContract.Presenter presenter;

    protected TextField mappingName;

    protected MappingCreationView(InputView parent, String headline) {
        Label title = new Label(headline);
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
        mappingName.setPlaceholder("Choose a unique name");
        add(mappingName);

        // TODO: add used in and description functionality

        Label mappingHeadline = new Label("Architecture to Code Mapping");
        mappingHeadline
                .getElement()
                .setProperty(
                        "title",
                        "Is necessary to find the code elements that correspond to this Concept/Relation");
        add(mappingHeadline);
        
        //add(new TripletView());
    }

    @Override
    public void setPresenter(MappingCreationContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
