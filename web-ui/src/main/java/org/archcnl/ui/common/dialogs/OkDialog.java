package org.archcnl.ui.common.dialogs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OkDialog extends Dialog {

    private static final long serialVersionUID = -7499231869322025697L;

    public OkDialog(String message) {
        VerticalLayout vl = new VerticalLayout();
        vl.add(new Text(message));
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonRow.add(new Button("OK", click -> close()));
        vl.add(buttonRow);
        add(vl);
        vl.setMinHeight(7.0f, Unit.CM);
        vl.setMinWidth(12.0f, Unit.CM);
    }
}
