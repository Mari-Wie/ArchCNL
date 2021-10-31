package org.archcnl.ui.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmNotification extends Notification {

    private static final long serialVersionUID = -7499231869322025697L;

    public ConfirmNotification(String message) {
        VerticalLayout vl = new VerticalLayout();
        vl.add(new Text(message));
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(new Button("OK", click -> close()));
        vl.add(buttonRow);
        add(vl);
    }
}
