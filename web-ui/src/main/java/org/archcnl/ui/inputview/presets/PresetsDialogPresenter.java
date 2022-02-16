package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfigManager;
import org.archcnl.domain.input.model.presets.ArchitectureInformation;
import org.archcnl.domain.input.model.presets.ArchitectureRuleConfig;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitecture;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitectureBuilder;
import org.archcnl.ui.common.TwoColumnGridEntry;
import org.archcnl.ui.inputview.presets.events.ArchitecturalStyleSelectedEvent;
import org.archcnl.ui.inputview.presets.events.ArchitectureRulesSelectedEvent;
import org.archcnl.ui.inputview.presets.events.RuleSelectionTabRequestedEvent;
import org.archcnl.ui.inputview.presets.events.UpdateRulesConceptsAndRelationsRequestedEvent;
import org.archcnl.ui.inputview.presets.events.ValidateArchitecturalStyleFormEvent;

@Tag("PresetsPresenter")
public class PresetsDialogPresenter extends Dialog {

    public enum TabOptions {
        STYLE_SELECTION,
        RULE_SELECTION,
        ARCHITECTURE_INFORMATION
    }

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(PresetsDialogPresenter.class);

    private final PresetsDialogView view;

    private Tabs tabs = new Tabs();
    private Map<Tab, Component> tabsToComponent = new LinkedHashMap<Tab, Component>();
    private Tab styleSelectionTab;
    private Tab ruleSelectionTab;
    private Tab architectureInformationTab;

    private ArchitecturalStyle selectedStyle;
    private ArchitecturalStyleConfig architectureConfig;

    private ConceptManager conceptManager;
    private RelationManager relationManager;
    private ArchitectureRuleManager ruleManager;

    public PresetsDialogPresenter(
            ConceptManager conceptManager,
            RelationManager relationManager,
            ArchitectureRuleManager ruleManager) {

        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        this.ruleManager = ruleManager;

        view = new PresetsDialogView();

        // handle inside this presenter
        view.addListener(RuleSelectionTabRequestedEvent.class, this::handleEvent);
        view.addListener(ArchitectureRulesSelectedEvent.class, this::handleEvent);
        view.addListener(ValidateArchitecturalStyleFormEvent.class, this::handleEvent);

        initializeTabs();
        initializeStyleSelectionTabAndContent();

        view.showTab(tabs.getSelectedTab());
    }

    private void initializeTabs() {
        styleSelectionTab = new Tab("Architectural Style");
        ruleSelectionTab = new Tab("Architecture Rules");
        ruleSelectionTab.setEnabled(false);
        architectureInformationTab = new Tab("Architecture Information");
        architectureInformationTab.setEnabled(false);

        tabs.add(styleSelectionTab, ruleSelectionTab, architectureInformationTab);

        tabs.setSelectedTab(styleSelectionTab);
        tabs.setWidthFull();
        view.updateTabs(tabs);
    }

    private void initializeStyleSelectionTabAndContent() {
        ArchitectureStyleSelection styleSelection = new ArchitectureStyleSelection();
        styleSelection.addListener(ArchitecturalStyleSelectedEvent.class, this::handleEvent);
        tabsToComponent.put(styleSelectionTab, styleSelection);

        view.updateFooter(TabOptions.STYLE_SELECTION);

        tabs.setSelectedTab(styleSelectionTab);

        view.updateTabsToComponent(tabsToComponent);
        view.showTab(tabs.getSelectedTab());
    }

    private void handleEvent(RuleSelectionTabRequestedEvent event) {
        architectureConfig = new ArchitecturalStyleConfigManager().getConfig(selectedStyle);
        ArchitectureRuleSelection ruleSelection;

        if (tabsToComponent.get(ruleSelectionTab) == null) {
            ruleSelection = new ArchitectureRuleSelection(architectureConfig);
            tabsToComponent.put(ruleSelectionTab, ruleSelection);
        } else {
            ruleSelection = (ArchitectureRuleSelection) tabsToComponent.get(ruleSelectionTab);
        }

        enableTab(ruleSelectionTab);
        tabs.setSelectedTab(ruleSelectionTab);
        view.updateTabsToComponent(tabsToComponent);
        view.updateFooter(TabOptions.RULE_SELECTION);
        view.showTab(tabs.getSelectedTab());
    }

    private void handleEvent(ArchitecturalStyleSelectedEvent event) {
        this.selectedStyle = event.getStyle();
    }

    private void handleEvent(ArchitectureRulesSelectedEvent event) {
        ArchitectureRuleSelection ruleSelection =
                (ArchitectureRuleSelection) tabsToComponent.get(ruleSelectionTab);
        Set<String> selectedRules = ruleSelection.getSelectedRules();

        // deactivate the variable architecture informations that are not selected
        for (ArchitectureRuleConfig ruleConfig : architectureConfig.getRules()) {
            String ruleToCheck = ruleConfig.getRule();
            if (!selectedRules.contains(ruleToCheck)) { // if not selected set active to false

                ruleConfig.setActive(false);
                List<Integer> idsToDeactiate = ruleConfig.getRequiredArchitectureInformationIds();
                List<ArchitectureInformation> variableArchitectureInfomation =
                        architectureConfig.getVariableParts();

                // loop all Architecture Information
                for (ArchitectureInformation architectureInformation :
                        variableArchitectureInfomation) {

                    // check if the id is within the ids that should be deactivated
                    if (idsToDeactiate.contains(architectureInformation.getId())) {
                        architectureInformation.setActive(false);
                    }
                }
            }
        }

        ArchitecturalStyleForm form = new ArchitecturalStyleForm(architectureConfig);
        tabsToComponent.put(architectureInformationTab, form);
        enableTab(architectureInformationTab);
        tabs.setSelectedTab(architectureInformationTab);
        view.updateTabsToComponent(tabsToComponent);
        view.updateFooter(TabOptions.ARCHITECTURE_INFORMATION);
        view.showTab(tabs.getSelectedTab());
    }

    private void handleEvent(ValidateArchitecturalStyleFormEvent event) {
        ArchitecturalStyleForm form =
                (ArchitecturalStyleForm) tabsToComponent.get(architectureInformationTab);

        if (form.validateForm()) {
            createRulesAndMappingsFromUiComponents(
                    selectedStyle, ruleManager, conceptManager, relationManager);
            view.close();
        }
    }

    /**
     * Creates Rules and Mappings for the selected Architectural Sytle. This is also the Method that
     * needs to be extended in case new architectural styles are added in the future.
     *
     * @param relationManager
     * @param conceptManager
     * @param ruleManager
     */
    private void createRulesAndMappingsFromUiComponents(
            ArchitecturalStyle selectedStyle,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager) {
        switch (selectedStyle) {
            case MICROSERVICE_ARCHITECTURE:
                createRulesAndMappingsForMicroserviceArchitecture(
                        ruleManager, conceptManager, relationManager);
                break;
            default:
                break;
        }
    }

    private void createRulesAndMappingsForMicroserviceArchitecture(
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager) {
        MicroserviceArchitectureBuilder microserviceArchitectureBuilder =
                new MicroserviceArchitectureBuilder();

        ArchitecturalStyleForm form =
                (ArchitecturalStyleForm) tabsToComponent.get(architectureInformationTab);

        Map<String, Binder<ArchitectureInformation>> architectureInfoBinders =
                form.getUngroupedArchitectureInformationBinders();

        Map<Integer, Set<TwoColumnGridEntry>> architectureInformationGroupsAndEntries =
                form.getArchitectureInformationGroupsAndEntries();

        // map ungrouped architecture information
        mapUiInputToArchitectureInformation(
                microserviceArchitectureBuilder, architectureInfoBinders);

        // map grouped architecture information
        mapGroupedUIInputsToArchitectureInformation(
                microserviceArchitectureBuilder, architectureInformationGroupsAndEntries);

        // create the rules
        MicroserviceArchitecture microserviceArchitecture = microserviceArchitectureBuilder.build();

        // only rules that are selected
        Set<String> rulesToCreate = new HashSet<String>();
        for (ArchitectureRuleConfig ruleConfig : architectureConfig.getRules()) {
            if (ruleConfig.isActive()) {
                rulesToCreate.add(ruleConfig.getRule());
            }
        }

        microserviceArchitecture.setArchitectureRules(rulesToCreate);

        microserviceArchitecture.createRulesAndMappings(
                ruleManager, conceptManager, relationManager);

        // this updates Concepts/Rules/Relations in the MainView
        fireEvent(new UpdateRulesConceptsAndRelationsRequestedEvent(this, false));
    }

    // loop through the sets that hold the data for the groups specified in the
    private void mapGroupedUIInputsToArchitectureInformation(
            MicroserviceArchitectureBuilder microserviceArchitectureBuilder,
            Map<Integer, Set<TwoColumnGridEntry>> architectureInformationGroupsAndEntries) {
        for (Entry<Integer, Set<TwoColumnGridEntry>> entry :
                architectureInformationGroupsAndEntries.entrySet()) {
            Integer groupId = entry.getKey();
            Set<TwoColumnGridEntry> entries = entry.getValue();

            // map groups to the specific architectural style information they contain
            switch (groupId) {
                case 1: // microservices
                    microserviceArchitectureBuilder.withMicroservices(entries);
                    break;
                case 2: // db-access-abstractions
                    microserviceArchitectureBuilder.withDbAccessAbstractions(entries);
                    break;
                case 3: // api-mechanisms
                    microserviceArchitectureBuilder.withApiMechanisms(entries);
                    break;
                default:
                    break;
            }
        }
    }

    private void mapUiInputToArchitectureInformation(
            MicroserviceArchitectureBuilder microserviceArchitectureBuilder,
            Map<String, Binder<ArchitectureInformation>> architectureInfoBinders) {
        for (Map.Entry<String, Binder<ArchitectureInformation>> entry :
                architectureInfoBinders.entrySet()) {
            String key = entry.getKey();

            // loop through variable Parts of the architectural style
            for (int i = 0; i < architectureConfig.getVariableParts().size(); i++) {

                // get the architecture information
                ArchitectureInformation info = architectureConfig.getVariableParts().get(i);

                // check if it has been created
                if (key.equals(info.getName())) {
                    try {

                        // write the bound value from the ui to the architecture information
                        entry.getValue().writeBean(info);

                        // map the architecture information to the architectural style
                        // information
                        switch (info.getName()) {
                            case "API Gateway Package":
                                microserviceArchitectureBuilder.withApiGatewayPackageName(
                                        info.getValue());
                                break;
                            case "MS-App Package Structure":
                                microserviceArchitectureBuilder.withMsAppPackageStructure(
                                        info.getValue());
                                break;
                            case "Service Registry Class Name":
                                microserviceArchitectureBuilder.withServiceRegistryClassName(
                                        info.getValue());
                                break;
                            case "Service Registry Import Class Name":
                                microserviceArchitectureBuilder.withRegistryImportName(
                                        info.getValue());
                                break;
                            case "Circuit Breaker Import Class Name":
                                microserviceArchitectureBuilder.withCircuitBreakerImportClassName(
                                        info.getValue());
                                break;
                            default:
                                break;
                        }
                    } catch (ValidationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Enables the tab and disables all others.
     *
     * @param tab the tab to enable.
     */
    public void enableTab(Tab tab) {
        tabs.getChildren()
                .forEach(
                        (component) -> {
                            Tab knownTab = (Tab) component;
                            if (knownTab.getLabel().equals(tab.getLabel())) {
                                tab.setEnabled(true);
                            } else {
                                knownTab.setEnabled(false);
                            }
                        });
        view.updateTabs(tabs);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public PresetsDialogView getView() {
        return view;
    }
}
