package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitectureRuleConfig;

@Tag("ArchitecturalStyleRuleSelection")
public class ArchitectureRuleSelection extends Component
        implements HasComponents, HasSize, PropertyChangeListener {

    /** */
    private static final long serialVersionUID = 1462530206809511554L;

    private CheckboxGroup<String> architectureRulesSelect;
    private ArchitecturalStyleConfig config;

    public ArchitectureRuleSelection(ArchitecturalStyleConfig architectureConfig) {
        this.config = architectureConfig;
        addRulesToUi(config.getRules());
    }

    private void addRulesToUi(List<ArchitectureRuleConfig> rulesFromConfig) {
        // TODO Auto-generated method stub
        architectureRulesSelect = new CheckboxGroup<String>();
        architectureRulesSelect.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        Set<String> knownRules = new HashSet<String>();
        for (ArchitectureRuleConfig architectureRuleString : rulesFromConfig) {
            knownRules.add(architectureRuleString.getRule());
        }
        architectureRulesSelect.setItems(knownRules);

        VerticalLayout layout = new VerticalLayout();

        layout.add(new Text("Please choose the architecture rules you want to apply."));

        architectureRulesSelect.select(knownRules);
        layout.setWidthFull();
        layout.add(architectureRulesSelect);
        add(layout);
    }

    public Set<String> getSelectedRules() {
        return architectureRulesSelect.getSelectedItems();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
