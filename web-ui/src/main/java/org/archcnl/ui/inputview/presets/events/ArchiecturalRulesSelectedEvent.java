package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.tabs.Tabs;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitectureInformation;
import org.archcnl.domain.input.model.presets.ArchitectureRuleConfig;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleForm;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleRuleSelection;

public class ArchiecturalRulesSelectedEvent
        extends ComponentEvent<ArchitecturalStyleRuleSelection> {

    /** */
    private static final long serialVersionUID = -7316825880316303934L;

    private Set<String> selectedItems;
    private ArchitecturalStyleConfig config;
    private Component newContent;
    private Tabs tabs;

    public ArchiecturalRulesSelectedEvent(
            ArchitecturalStyleRuleSelection source,
            boolean fromClient,
            ArchitecturalStyleConfig config,
            Set<String> selectedRules,
            Tabs tabs) {
        super(source, fromClient);
        this.selectedItems = selectedRules;
        this.config = config;
        this.tabs = tabs;
    }

    public void handleEvent() {

        for (ArchitectureRuleConfig ruleConfig : config.getRules()) {
            String ruleToCheck = ruleConfig.getRule();
            if (selectedItems.contains(ruleToCheck)) { // if the rule is within the selected items

            } else { // if not selected set !active

                ruleConfig.setActive(false);
                List<Integer> idsToDeactiate = ruleConfig.getRequiredArchitectureInformationIds();
                List<ArchitectureInformation> variableArchitectureInfomation =
                        config.getVariableParts();

                // loop all Architecture Informations
                for (ArchitectureInformation architectureInformation :
                        variableArchitectureInfomation) {

                    // check if the id is within the ids that should be deactivated
                    if (idsToDeactiate.contains(architectureInformation.getId())) {
                        architectureInformation.setActive(false);
                    }
                }
            }
        }

        ArchitecturalStyleForm form = new ArchitecturalStyleForm(config);
        form.setSizeFull();
        getSource().removeAll();
        getSource().add(form);
        tabs.getSelectedTab().setEnabled(false);
        tabs.setSelectedIndex(2);

        getSource().setSizeFull();
    }
}
