package org.vaadin.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TestView2 extends VerticalLayout {

    private MainUIPresenter presenter;

    public TestView2(MainUIPresenter presenter) {
        this.presenter = presenter;
        initView();
    }

    private void initView() {
        Label testText = new Label("This is the second view");
        Button switchViewButton = new Button("Back", e -> presenter.setArchitectureRuleView());
        add(testText, switchViewButton);
        setHeightFull();
    }
}
