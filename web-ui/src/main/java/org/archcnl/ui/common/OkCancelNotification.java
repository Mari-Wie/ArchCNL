package org.archcnl.ui.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OkCancelNotification extends Notification {

    private static final long serialVersionUID = 8174115191925312064L;

    public OkCancelNotification(
            String headline,
            String body,
            ButtonClickResponder okResponse,
            ButtonClickResponder cancelResponse) {
        VerticalLayout vl = new VerticalLayout();
        VerticalLayout textLayout = new VerticalLayout(new H3(headline), new Text(body));
        vl.add(textLayout);
        vl.setFlexGrow(1, textLayout);
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(
                new Button(
                        "OK",
                        click -> {
                            okResponse.onButtonClick();
                            close();
                        }));
        buttonRow.add(
                new Button(
                        "Cancel",
                        click -> {
                            cancelResponse.onButtonClick();
                            close();
                        }));
        vl.add(buttonRow);
        add(vl);
        vl.setMinHeight(7.0f, Unit.CM);
        vl.setMinWidth(12.0f, Unit.CM);
        setPosition(Position.MIDDLE);
    }
}
