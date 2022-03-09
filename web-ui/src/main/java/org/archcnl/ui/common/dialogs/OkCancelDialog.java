package org.archcnl.ui.common.dialogs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OkCancelDialog extends Dialog {

    private static final long serialVersionUID = 8174115191925312064L;

    public OkCancelDialog(
            String headline,
            String body,
            ButtonClickResponder okResponse,
            ButtonClickResponder cancelResponse) {

        setDraggable(true);
        setMinHeight(8.0f, Unit.CM);
        setWidth(12.0f, Unit.CM);

        Text headlineText = new Text(headline);
        VerticalLayout bodyText = new VerticalLayout(new Text(body));
        bodyText.setWidthFull();
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

        VerticalLayout layout = new VerticalLayout(headlineText, bodyText, buttonRow);
        layout.setHeightFull();
        layout.setFlexGrow(1, bodyText);
        add(layout);
    }
}
