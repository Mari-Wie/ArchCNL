package org.archcnl.ui.inputview.presets;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitectureRuleConfig;
import org.archcnl.ui.inputview.presets.events.ArchiecturalRulesSelectedEvent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;

@Tag("ArchitecturalStyleRuleSelection")
public class ArchitecturalStyleRuleSelection extends Component
		implements HasComponents, HasSize, PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1462530206809511554L;

	private CheckboxGroup<String> architectureRulesSelect;
	private ArchitecturalStyleConfig config;
	private Tabs tabs;

	public ArchitecturalStyleRuleSelection(ArchitecturalStyleConfig architectureConfig, Tabs tabs) {
		this.config = architectureConfig;
		this.tabs = tabs;

		addListeners();
		addRulesToUi(config.getRules());
		addFooterToUi();
	}

	private void addFooterToUi() {
		Button cancelBtn = new Button("Cancel", e -> remove(this));
		Button saveBtn = new Button("Next", e -> {
			fireEvent(
					new ArchiecturalRulesSelectedEvent(this, true, config, architectureRulesSelect.getSelectedItems(), tabs));
		});
		HorizontalLayout footer = new HorizontalLayout();
		footer.add(cancelBtn, saveBtn);

		add(footer);
	}

	private void addRulesToUi(List<ArchitectureRuleConfig> rulesFromConfig) {
		// TODO Auto-generated method stub
		architectureRulesSelect = new CheckboxGroup<String>();
		architectureRulesSelect.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
		Set<String> knwonRules = new HashSet<String>();
		for (ArchitectureRuleConfig architectureRuleString : config.getRules()) {
			knwonRules.add(architectureRuleString.getRule());
		}
		architectureRulesSelect.setItems(knwonRules);

		VerticalLayout box = new VerticalLayout();

		box.add(new Text("Please choose the architectural rules you want to apply."));

		architectureRulesSelect.select(knwonRules);
		box.setWidthFull();
		box.add(architectureRulesSelect);
		add(box);
	}

	private void addListeners() {
		this.addListener(ArchiecturalRulesSelectedEvent.class, ArchiecturalRulesSelectedEvent::handleEvent);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
