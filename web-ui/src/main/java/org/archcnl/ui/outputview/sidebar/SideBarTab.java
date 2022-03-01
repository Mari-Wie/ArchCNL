package org.archcnl.ui.outputview.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;

public class SideBarTab extends Tab {

    private static final long serialVersionUID = 8246935861159358702L;
    private final Span label;
    private Component linkedComponent;

    public SideBarTab(final String text, final VaadinIcon icon, final Component linkedComponent) {
        super(new Icon(icon));
        this.label = createLabel(text);
        this.linkedComponent = linkedComponent;
        setClassName("side-bar-tab");
        add(label);
    }

    public Component getLinkedComponent() {
        return linkedComponent;
    }

    public void setLinkedComponent(final Component component) {
        this.linkedComponent = component;
    }

    public void updateLabel(String text) {
        label.setText(text);
    }

    private Span createLabel(String text) {
        Span newlabel = new Span(text);
        newlabel.setClassName("side-bar-text");
        return newlabel;
    }
}
