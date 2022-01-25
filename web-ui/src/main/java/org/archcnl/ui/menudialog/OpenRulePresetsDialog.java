package org.archcnl.ui.menudialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyles;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleInputBuilder;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleInputContainer;

public class OpenRulePresetsDialog extends Dialog {

    /** */
    private static final long serialVersionUID = -8177194179089306020L;

    private VerticalLayout dialogLayout;

    private Select<ArchitecturalStyles> select;
    private Text title;

    public OpenRulePresetsDialog() {
        this.dialogLayout = new VerticalLayout();
        super.add(dialogLayout);
        setDraggable(true);

        title = new Text("Select archictectural style to open presets for");

        select = new Select<ArchitecturalStyles>();

        select.setItems(ArchitecturalStyles.values());

        dialogLayout.addComponentAtIndex(0, title);
        dialogLayout.addComponentAtIndex(1, select);

        select.addValueChangeListener(
                event -> {
                    // update dialog if event value changed
                    updateDialog(select.getValue());
                });
    }

    private void updateDialog(ArchitecturalStyles architecturalStyle) {

        ArchitecturalStyle architecture =
                new ArchitecturalStyleInputBuilder().build(architecturalStyle);

        // if style has been build update UI
        if (architecture != null) {
            dialogLayout.remove(select);
            title.setText("Enter " + architecturalStyle.toString() + " Information");
            ArchitecturalStyleInputContainer container =
                    new ArchitecturalStyleInputContainer(architecture);
            setWidth("70%");
            dialogLayout.add(container);

        } else { // show notification
            dialogLayout.add(
                    Notification.show(
                            architecturalStyle.toString() + " is not yet implemented",
                            5000,
                            Position.BOTTOM_START));
        }
    }
}
