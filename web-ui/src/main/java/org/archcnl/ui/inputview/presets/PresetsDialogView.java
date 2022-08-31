package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.archcnl.ui.inputview.presets.PresetsDialogPresenter.TabOptions;
import org.archcnl.ui.inputview.presets.events.ArchitectureRulesSelectedEvent;
import org.archcnl.ui.inputview.presets.events.RuleSelectionTabRequestedEvent;
import org.archcnl.ui.inputview.presets.events.ValidateArchitecturalStyleFormEvent;

public class PresetsDialogView extends Dialog implements HasComponents, FlexComponent<Component> {

    private static final long serialVersionUID = 1L;

    private Tabs tabs = new Tabs();
    private Map<Tab, Component> tabsToComponent;

    private Component currentContent = new Div();
    HorizontalLayout footer;
    private VerticalLayout dialogLayout;

    public PresetsDialogView() {
        tabsToComponent = new LinkedHashMap<>();
        setWidth("60%");
        dialogLayout = new VerticalLayout();
        dialogLayout.setWidthFull();

        tabs.setWidthFull();

        // default/first Tab is Style Selection
        footer = new HorizontalLayout();

        dialogLayout.add(tabs, currentContent, footer);
        dialogLayout.setWidthFull();
        add(dialogLayout);
    }

    public void updateFooter(TabOptions tab) {
        HorizontalLayout newFooter = new HorizontalLayout();

        Button cancel = new Button("Cancel", e -> close());

        Button confirm = new Button("Next");
        switch (tab) {
            case STYLE_SELECTION:
                confirm.addClickListener(
                        e -> fireEvent(new RuleSelectionTabRequestedEvent(this, false)));
                break;
            case RULE_SELECTION:
                confirm.addClickListener(
                        e -> fireEvent(new ArchitectureRulesSelectedEvent(this, false)));
                break;
            case ARCHITECTURE_INFORMATION:
                confirm = new Button("Create Rules & Mappings");

                confirm.addClickListener(
                        e -> fireEvent(new ValidateArchitecturalStyleFormEvent(this, false)));
                break;
            default:
                break;
        }

        newFooter.add(cancel, confirm);
        dialogLayout.replace(footer, newFooter);
        footer = newFooter;
    }

    public void showTab(Tab tab) {
        Component newContent = tabsToComponent.get(tab);
        dialogLayout.replace(currentContent, newContent);
        currentContent = newContent;
    }

    public void updateTabsToComponent(Map<Tab, Component> tabsToComponent) {
        this.tabsToComponent = tabsToComponent;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // replace old tabs and add new tabs to top
    public void updateTabs(Tabs tabs) {
        dialogLayout.remove(this.tabs);
        dialogLayout.addComponentAsFirst(tabs);
    }
}
