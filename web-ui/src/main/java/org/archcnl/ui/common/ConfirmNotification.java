package org.archcnl.ui.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmNotification extends Notification {

    private static final long serialVersionUID = -7499231869322025697L;

    public ConfirmNotification(String message) {
        VerticalLayout vl = new VerticalLayout();
        vl.add(new Text(message));
        vl.add(new Button("OK", click -> close()));
        add(vl);
    }
}
