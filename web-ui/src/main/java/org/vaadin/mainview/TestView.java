package org.vaadin.mainview;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TestView extends VerticalLayout{

    public TestView()
    {
        initView();
    }

    private void initView() 
    {
       Label testText = new Label("This is a view");    
       add(testText);
       setHeightFull();
    }
}
