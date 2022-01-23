package org.archcnl.ui.outputview.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;

public class SideBarTab extends Tab {

    private static final long serialVersionUID = 8246935861159358702L;
    private Component linkedComponent;

    public SideBarTab(String text, VaadinIcon icon, Component linkedComponent) {
        super(new Icon(icon), new Span(text));
        this.linkedComponent = linkedComponent;
    }

    public Component getLinkedComponent() {
        return linkedComponent;
    }

    public void setLinkedComponent(Component component) {
        this.linkedComponent = component;
    }
}
