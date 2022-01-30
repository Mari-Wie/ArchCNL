package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.dom.Element;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitectureInformation;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitecture;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitectureBuilder;
import org.archcnl.ui.common.TwoColumnGridAndInputTextFieldsComponent;
import org.archcnl.ui.common.TwoColumnGridEntry;
import org.archcnl.ui.inputview.presets.events.ArchitecturalStyleSaveEvent;

/***
 * This UI-Component creates input possibilities for the user of ArchCNL based
 * on the ArchitecturalStyle that is passed into the constructor. It will
 * generate input-components based on the properties of the style. <br>
 * <p>
 * It also holds the logic on "what to do" with the information. As this is
 * specific for every architectural style, new styles require an extension of
 * the current logic. This is can be done in the method
 * {@link #mapUiComponentsToArchitecturalStyle(ArchitecturalStyle)}.
 */
@Tag("ArchitecturalStyleInputContainer")
public class ArchitecturalStyleForm extends Component
        implements HasComponents, HasSize, PropertyChangeListener {

    /** */
    private static final long serialVersionUID = -880067698454481506L;

    private Map<Integer, List<ArchitectureInformation>> knownArchitectureInformationGroups;
    private List<TextField> ungroupedArchitectureInformation;
    private Map<String, Binder<ArchitectureInformation>> ungroupedArchitectureInformationBinders;
    private Map<Integer, Set<TwoColumnGridEntry>> architectureInformationGroupsAndEntries;
    private ArchitecturalStyleConfig styleConfig;
    private CheckboxGroup<String> architectureRulesSelect;

    public ArchitecturalStyleForm(ArchitecturalStyleConfig architecturalStyleConfig) {

        this.styleConfig = architecturalStyleConfig;

        knownArchitectureInformationGroups = new HashMap<Integer, List<ArchitectureInformation>>();

        ungroupedArchitectureInformation = new ArrayList<TextField>();

        // map information of the architectural style to components in the ui
        for (ArchitectureInformation archInfo : architecturalStyleConfig.getVariableParts()) {
            // not grouped items
            if (archInfo.getGroupId() == -1) {
                addToKnownTextField(archInfo);
            } else { // grouped items
                knownArchitectureInformationGroups
                        .computeIfAbsent(
                                archInfo.getGroupId(),
                                k -> new ArrayList<ArchitectureInformation>())
                        .add(archInfo);
            }
        }

        // add event listeners
        addListeners();

        addKnownGroupsToUi();
        addKnownTextFieldsToUi();
        addFooter();
    }

    private void addListeners() {
        this.addListener(
                ArchitecturalStyleSaveEvent.class, ArchitecturalStyleSaveEvent::handleEvent);
    }

    // adds an architecture information as textfield including a binder
    private void addToKnownTextField(ArchitectureInformation info) {

        // create map if null
        if (ungroupedArchitectureInformationBinders == null) {
            ungroupedArchitectureInformationBinders =
                    new HashMap<String, Binder<ArchitectureInformation>>();
        } // map not null
        TextField textField = new TextField(info.getName());

        Binder<ArchitectureInformation> infoBinder =
                new Binder<>(ArchitectureInformation.class, false);
        infoBinder
                .forField(textField)
                .withNullRepresentation("")
                .asRequired()
                .bind(ArchitectureInformation::getValue, ArchitectureInformation::setValue);
        ungroupedArchitectureInformationBinders.put(info.getName(), infoBinder);
        ungroupedArchitectureInformation.add(textField);
    }

    private void addKnownTextFieldsToUi() {
        HorizontalLayout layout = new HorizontalLayout();
        for (TextField textField : ungroupedArchitectureInformation) {
            if (layout.getComponentCount() <= 1) { // two textfield per row
                layout.setWidthFull();
                layout.add(textField);
                textField.setWidth("50%");
            } else { // if two textfields
                layout.setWidthFull();
                add(layout);
                layout = new HorizontalLayout();
                layout.add(textField);
                textField.setWidth("50%");
            }
        }

        // add last element
        if (layout.getComponentCount() != 0) {
            layout.setWidthFull();
            add(layout);
        }
    }

    // add the input groups specified in the config
    private void addKnownGroupsToUi() {

        // initialize Map
        if (architectureInformationGroupsAndEntries == null) {
            architectureInformationGroupsAndEntries =
                    new HashMap<Integer, Set<TwoColumnGridEntry>>();
        }

        // loop through known groups from config and create UI components for these
        // groups
        for (Entry<Integer, List<ArchitectureInformation>> entry :
                knownArchitectureInformationGroups.entrySet()) {
            Integer key = entry.getKey();

            List<ArchitectureInformation> information = entry.getValue();

            System.out.println("Size" + information.size());

            if (information.size() == 2) {
                Binder<TwoColumnGridEntry> gridEntriesBinder =
                        new Binder<>(TwoColumnGridEntry.class);
                Set<TwoColumnGridEntry> gridItems = new HashSet<TwoColumnGridEntry>();
                TwoColumnGridAndInputTextFieldsComponent comp =
                        new TwoColumnGridAndInputTextFieldsComponent(
                                // list index starts at 0
                                information.get(0).getName(),
                                information.get(1).getName(),
                                gridItems,
                                gridEntriesBinder);

                // this collection is used to save the grid entries as nested binding with
                // Vaadin did not work out
                // TODO Check for better solution with nested binding & vaadin instead
                architectureInformationGroupsAndEntries.put(key, gridItems);

                add(comp);
            }
        }
    }

    private void addFooter() {
        Button cancelBtn = new Button("Cancel", e -> remove(this));
        Button saveBtn =
                new Button(
                        "Create Rules & Mappings",
                        e ->
                                fireEvent(
                                        new ArchitecturalStyleSaveEvent(
                                                this, true, styleConfig.getStyleName())));
        HorizontalLayout footer = new HorizontalLayout();
        footer.add(cancelBtn, saveBtn);

        add(footer);
    }

    public void addComponents(List<Component> components) {
        for (Component component : components) {
            HorizontalLayout row = new HorizontalLayout(component);
            add(row);
        }
    }

    public void add(Component child) {
        getElement().appendChild(child.getElement());
    }

    public void remove(Component child) {
        Element wrapper = child.getElement().getParent();
        wrapper.removeFromParent();
    }

    /**
     * This method maps the values from the UI Components to the information they hold for the
     * respective architectural style.
     */
    public void mapUiComponentsToArchitecturalStyle(ArchitecturalStyle styleName) {
        switch (styleName) {
            case MICROSERVICE_ARCHITECTURE:
                MicroserviceArchitectureBuilder microserviceArchitectureBuilder =
                        new MicroserviceArchitectureBuilder();

                // loop through known textfields (not grouped architecture information from
                // config)
                for (Map.Entry<String, Binder<ArchitectureInformation>> entry :
                        ungroupedArchitectureInformationBinders.entrySet()) {
                    String key = entry.getKey();

                    // loop through variable Parts of the architectural style
                    for (int i = 0; i < styleConfig.getVariableParts().size(); i++) {

                        // get the architecture information
                        ArchitectureInformation info = styleConfig.getVariableParts().get(i);

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
                                    case "Service Registry Class Name":
                                        microserviceArchitectureBuilder
                                                .withServiceRegistryClassName(info.getValue());
                                    case "Service Registry Import Class Name":
                                        microserviceArchitectureBuilder
                                                .withServiceRegistryClassName(info.getValue());
                                    case "Circuit Breaker Import Class Name":
                                        microserviceArchitectureBuilder
                                                .withCircuitBreakerImportClassName(info.getValue());
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

                // loop through the sets that hold the data for the groups specified in the
                // config
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
                        case 3: // api-mechanisms
                            microserviceArchitectureBuilder.withApiMechanisms(entries);
                        default:
                            break;
                    }
                }

                // create the rules
                MicroserviceArchitecture microserviceArchitecture =
                        microserviceArchitectureBuilder.build();

                // only rules that are selected
                microserviceArchitecture.setArchitectureRules(
                        architectureRulesSelect.getSelectedItems());

                microserviceArchitecture.createRulesAndMappings();
                break;

                // Other cases for other styles required
            default:
                break;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }
}
