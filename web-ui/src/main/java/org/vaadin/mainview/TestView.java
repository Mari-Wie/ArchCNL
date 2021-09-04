package org.vaadin.mainview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TestView extends VerticalLayout {

    private MainUIPresenter presenter;

    public TestView(MainUIPresenter presenter) {
        this.presenter = presenter;
        initView();
    }

    private void initView() {
        Label testText = new Label("This is the first view");
        Button switchViewButton = new Button("Next", e -> presenter.setResultView());
        add(testText, switchViewButton);
        setHeightFull();
    }
}
