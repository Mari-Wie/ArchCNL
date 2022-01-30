package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfigManager;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleRuleSelection;
import org.archcnl.ui.menudialog.OpenRulePresetsDialog;

public class ArchitecturalStyleSelectedEvent extends ComponentEvent<OpenRulePresetsDialog> {

    /** */
    private static final long serialVersionUID = -3410874386784534179L;

    private ArchitecturalStyle style;
    private Component currentContent;
    private Component newContent;
    private Tabs tabs;
    private Tab nextTab;

    public ArchitecturalStyleSelectedEvent(
            OpenRulePresetsDialog source,
            boolean fromClient,
            ArchitecturalStyle architecturalStyle,
            Component currentContent,
            Tabs tabs,
            Tab nextTab) {
        super(source, fromClient);
        this.style = architecturalStyle;
        this.currentContent = currentContent;
        this.tabs = tabs;
        this.nextTab = nextTab;
        // TODO Auto-generated constructor stub
    }

    public void handleEvent() {
        ArchitecturalStyleConfig architectureConfig =
                new ArchitecturalStyleConfigManager().build(style);

        // if style has been build update UI
        if (architectureConfig != null) {

            ArchitecturalStyleRuleSelection ruleSelection =
                    new ArchitecturalStyleRuleSelection(architectureConfig, tabs);

            newContent = ruleSelection;

            getSource().remove(currentContent);
            getSource().add(newContent);
            tabs.setSelectedTab(nextTab);

        } else { // show notification
            getSource()
                    .add(
                            Notification.show(
                                    style + " is not yet implemented",
                                    5000,
                                    Position.BOTTOM_START));
        }
    }

    public void handleRuleSelection() {}

    public void showTab(Tab tab) {}
}
