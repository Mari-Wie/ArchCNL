package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.dom.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitectureInformation;
import org.archcnl.domain.input.model.presets.ArchitectureRuleString;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitecture;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitectureBuilder;
import org.archcnl.ui.common.TwoColumnGridAndInputTextFieldsComponent;
import org.archcnl.ui.common.TwoColumnGridEntry;

/***
 * This Container creates input possibilities for the user of ArchCNL based
 * on the ArchitecturalStyle that is passed into the constructor.<br>
 * It will generate input-components based on the properties of the style. <br>
 *
 * New architectural styles can easily be added in a way that only the "mapping"
 * from some input information (e.g. a TextField) to some architectural
 * information (e.g. UI-Layer Package Name) needs to be implemented. Therefore
 * the EventListener of the save button needs to be extended.
 */
@Tag("ArchitecturalStyleInputContainer")
public class ArchitecturalStyleInputContainer extends Component implements HasComponents {

    /** */
    private static final long serialVersionUID = -880067698454481506L;

    private Map<Integer, List<ArchitectureInformation>> groups;
    private List<TextField> knownTextfields;
    private Map<String, Binder<ArchitectureInformation>> textFieldBinders;
    private Map<Integer, Set<TwoColumnGridEntry>> groupIdgridEntriesMap;
    private ArchitecturalStyle style;
    private MultiSelectListBox<String> rulesSelectList;

    public ArchitecturalStyleInputContainer(ArchitecturalStyle architecturalStyle) {
        this.style = architecturalStyle;

        groups = new HashMap<Integer, List<ArchitectureInformation>>();

        knownTextfields = new ArrayList<TextField>();

        // map information of the architectural style to components in the ui
        for (ArchitectureInformation archInfo : architecturalStyle.getVariableParts()) {
            // not grouped items
            if (archInfo.getGroupId() == -1) {
                addToKnownTextField(archInfo);
            } else { // grouped items
                groups.computeIfAbsent(
                                archInfo.getGroupId(),
                                k -> new ArrayList<ArchitectureInformation>())
                        .add(archInfo);
            }
        }

        // add components to ui
        addRuleBoxToUi(architecturalStyle.getRules());
        addGroups();
        addKnownTextFieldsToUi();
        addFooter();
    }

    private void addRuleBoxToUi(List<ArchitectureRuleString> rules) {
        rulesSelectList = new MultiSelectListBox<String>();
        Set<String> knwonRules = new HashSet<String>();
        for (ArchitectureRuleString architectureRuleString : rules) {
            knwonRules.add(architectureRuleString.getRule());
        }
        rulesSelectList.setItems(knwonRules);
        
        VerticalLayout box = new VerticalLayout();
        box.add(new Text("Please choose the architectural rules you want to apply."));
        
        rulesSelectList.select(knwonRules);
        box.add(rulesSelectList);
        add(box);
    }

    private void addToKnownTextField(ArchitectureInformation info) {
        // create map if null
        if (textFieldBinders == null) {
            textFieldBinders = new HashMap<String, Binder<ArchitectureInformation>>();
        } // map not null
        TextField textField = new TextField(info.getName());
        Binder<ArchitectureInformation> infoBinder =
                new Binder<>(ArchitectureInformation.class, false);
        infoBinder
                .forField(textField)
                .withNullRepresentation("")
                .asRequired()
                .bind(ArchitectureInformation::getValue, ArchitectureInformation::setValue);
        textFieldBinders.put(info.getName(), infoBinder);
        knownTextfields.add(textField);
    }

    private void addKnownTextFieldsToUi() {
        for (TextField textField : knownTextfields) {
            add(textField);
        }
    }

    private void addGroups() {
        if (groupIdgridEntriesMap == null) {
            groupIdgridEntriesMap = new HashMap<Integer, Set<TwoColumnGridEntry>>();
        }

        for (Entry<Integer, List<ArchitectureInformation>> entry : groups.entrySet()) {
            Integer key = entry.getKey();
            System.out.println("Key" + key);
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
                groupIdgridEntriesMap.put(key, gridItems);
                add(comp);
            }
        }
    }

    private void addFooter() {
        Button cancelBtn = new Button("Cancel", e -> remove(this));
        Button saveBtn =
                new Button(
                        "Save",
                        e -> {
                            switch (style.getStyle()) {
                                case MICROSERVICE_ARCHITECTURE:
                                    MicroserviceArchitectureBuilder
                                            microserviceArchitectureBuilder =
                                                    new MicroserviceArchitectureBuilder();

                                    for (Map.Entry<String, Binder<ArchitectureInformation>> entry :
                                            textFieldBinders.entrySet()) {
                                        String key = entry.getKey();
                                        Binder<ArchitectureInformation> val = entry.getValue();

                                        // for the textfields we need to map which input they
                                        // represent
                                        for (int i = 0; i < style.getVariableParts().size(); i++) {
                                            ArchitectureInformation info =
                                                    style.getVariableParts().get(i);
                                            if (key.equals(info.getName())) {
                                                try {
                                                    val.writeBean(info);

                                                    // map TextField value to architecture
                                                    // information
                                                    switch (info.getName()) {
                                                        case "API Gateway Package":
                                                            microserviceArchitectureBuilder
                                                                    .withApiGatewayPackageName(
                                                                            info.getValue());
                                                            break;
                                                        case "MS-App Package Structure":
                                                            microserviceArchitectureBuilder
                                                                    .withMsAppPackageStructure(
                                                                            info.getValue());
                                                        case "Service Registry Class Name":
                                                            microserviceArchitectureBuilder
                                                                    .withServiceRegistryClassName(
                                                                            info.getValue());
                                                        case "Service Registry Import Class Name":
                                                            microserviceArchitectureBuilder
                                                                    .withServiceRegistryClassName(
                                                                            info.getValue());
                                                        case "Circuit Breaker Import Class Name":
                                                            microserviceArchitectureBuilder
                                                                    .withCircuitBreakerImportClassName(
                                                                            info.getValue());
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

                                    // map group data to input objects that are used to create the
                                    // MicroserviceArchitecture object
                                    for (Entry<Integer, Set<TwoColumnGridEntry>> entry :
                                            groupIdgridEntriesMap.entrySet()) {
                                        Integer groupId = entry.getKey();
                                        Set<TwoColumnGridEntry> entries = entry.getValue();

                                        switch (groupId) {
                                            case 1: // microservices
                                                microserviceArchitectureBuilder.withMicroservices(
                                                        entries);
                                                break;
                                            case 2: // db-access-abstractions
                                                microserviceArchitectureBuilder
                                                        .withDbAccessAbstractions(entries);
                                            case 3: // api-mechanisms
                                                microserviceArchitectureBuilder.withApiMechanisms(
                                                        entries);
                                            default:
                                                break;
                                        }
                                    }

                                    // create the rules
                                    MicroserviceArchitecture microserviceArchitecture =
                                            microserviceArchitectureBuilder.build();
                                    microserviceArchitecture.setArchitectureRules(rulesSelectList.getSelectedItems());
                                    microserviceArchitecture.createRulesAndMappings();
                                    break;

                                    // Other cases for other styles required
                                default:
                                    Notification notification =
                                            new Notification(
                                                    "Automatic Creation of Rules & Mappings not yet implemented");
                                    notification.open();
                                    break;
                            }
                        });

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
}
