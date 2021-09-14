package org.archcnl.ui.input.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.ui.input.InputView;

public abstract class MappingCreationView extends RulesOrMappingCreationLayout {

    private static final long serialVersionUID = 156879235315976468L;

    protected TextField mappingName;

    protected MappingCreationView(InputView parent, String headline) {
        Label title = new Label(headline);
        Button closeButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> parent.switchToArchitectureRulesLayout());
        HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);
        titleBar.setWidthFull();
        title.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(titleBar);

        mappingName = new TextField("Name");
        mappingName.setPlaceholder("Choose a unique name");
        add(mappingName);
    }
}
