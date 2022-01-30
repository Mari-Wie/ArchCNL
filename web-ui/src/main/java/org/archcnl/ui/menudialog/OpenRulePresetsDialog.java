package org.archcnl.ui.menudialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfigManager;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleRuleSelection;
import org.archcnl.ui.inputview.presets.events.PresetsDialogTabRequestedEvent;

public class OpenRulePresetsDialog extends Dialog implements HasComponents, PropertyChangeListener {

    /** */
    private static final long serialVersionUID = -8177194179089306020L;

    private RadioButtonGroup<ArchitecturalStyle> select;
    private Text title;
    private Component currentContent;

    private Map<Tab, Component> tabsToComponent = new LinkedHashMap<>();
    private Tabs tabs;

    public OpenRulePresetsDialog() {

        addListeners();

        setWidth("60%");

        title = new Text("Select archictectural style to open presets for");

        select = new RadioButtonGroup<ArchitecturalStyle>();

        select.setItems(ArchitecturalStyle.values());
        select.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        VerticalLayout selectLayout = new VerticalLayout();
        selectLayout.add(title, select);

        Tab rulesTab = new Tab("Architectural Rules");

        Tab architectureInformationTab = new Tab("Architecture Information");

        Button cancelBtn = new Button("Cancel", e -> remove(this));
        Button saveBtn =
                new Button(
                        "Next",
                        e -> {
                            ArchitecturalStyleConfig architectureConfig =
                                    new ArchitecturalStyleConfigManager().build(select.getValue());
                            ArchitecturalStyleRuleSelection ruleSelection =
                                    new ArchitecturalStyleRuleSelection(architectureConfig, tabs);

                            Component newContent = ruleSelection;
                            fireEvent(
                                    new PresetsDialogTabRequestedEvent(
                                            this, false, currentContent, newContent, tabs));
                        });
        HorizontalLayout footer = new HorizontalLayout();
        footer.add(cancelBtn, saveBtn);

        selectLayout.add(footer);

        tabsToComponent.put(new Tab("Architectural Style"), new VerticalLayout(selectLayout));
        tabsToComponent.put(rulesTab, new VerticalLayout());
        tabsToComponent.put(architectureInformationTab, new VerticalLayout());

        tabs = new Tabs();
        tabsToComponent.keySet().forEach(tabs::add);

        currentContent = selectLayout;
        add(tabs, currentContent);

        //		select.addValueChangeListener(
        //				event -> fireEvent(new ArchitecturalStyleSelectedEvent(this, false, select.getValue(),
        // currentContent)));

    }

    private void addListeners() {
        this.addListener(
                PresetsDialogTabRequestedEvent.class, PresetsDialogTabRequestedEvent::handleEvent);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

    public Tabs getTabs() {
        return this.tabs;
    }
}
