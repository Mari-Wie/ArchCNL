package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitectureInformation;
import org.archcnl.ui.common.TwoColumnGridAndInputTextFieldsComponent;
import org.archcnl.ui.common.TwoColumnGridEntry;

/***
 * This UI-Component creates input possibilities for the user of ArchCNL based
 * on the ArchitecturalStyleConfig that is passed into the constructor. It will
 * generate input-components based on the properties of the style. <br>
 * <p>
 */
@Tag("ArchitecturalStyleForm")
public class ArchitecturalStyleForm extends Component
        implements HasComponents, HasSize, PropertyChangeListener {

    private static final long serialVersionUID = -880067698454481506L;

    private Map<Integer, List<ArchitectureInformation>> knownArchitectureInformationGroups =
            new HashMap<Integer, List<ArchitectureInformation>>();
    ;
    private List<TextField> ungroupedArchitectureInformation = new ArrayList<TextField>();

    private Map<String, Binder<ArchitectureInformation>> ungroupedArchitectureInformationBinders =
            new HashMap<String, Binder<ArchitectureInformation>>();

    private Map<Integer, Set<TwoColumnGridEntry>> architectureInformationGroupsAndEntries;

    public ArchitecturalStyleForm(ArchitecturalStyleConfig architecturalStyleConfig) {
        createUIComponentsFromArchitectureConfig(architecturalStyleConfig);
        addUiComponents();
        setWidthFull();
    }

    private void addUiComponents() {
        addKnownGroupsToUi();
        addKnownTextFieldsToUi();
    }

    /**
     * Creates UI Components from and ArchitecturalStyleConfig. If an ArchitectureInformation within
     * that configuration is belonging to a group (meaning it has a group id that is not -1) it will
     * be added to the group. If the ArchitectureInformation does not have a group, it will be added
     * as TextField.
     */
    private void createUIComponentsFromArchitectureConfig(
            ArchitecturalStyleConfig architecturalStyleConfig) {
        for (ArchitectureInformation archInfo : architecturalStyleConfig.getVariableParts()) {

            if (archInfo.isActive()) {
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
        }
    }

    /**
     * Creates a TextField for an ArchitectureInformation including a Binder that holds the value.
     * The Binder is added to the Map of known Binders so that the values can be retrieved from
     * there.
     */
    private void addToKnownTextField(ArchitectureInformation info) {
        TextField textField = new TextField(info.getName());

        Binder<ArchitectureInformation> infoBinder =
                new Binder<>(ArchitectureInformation.class, false);
        infoBinder
                .forField(textField)
                .asRequired()
                .bind(ArchitectureInformation::getValue, ArchitectureInformation::setValue);

        ungroupedArchitectureInformationBinders.put(info.getName(), infoBinder);
        ungroupedArchitectureInformation.add(textField);
    }

    /** Adds not-grouped architecture information to the UI as TextFields. */
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

    /** Adds known groups of architecture information to the UI. */
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

            // list of ArchitectureInformation belonging to the same group
            List<ArchitectureInformation> information = entry.getValue();

            // currently only 2 ArchitectureInformations can be grouped together
            // if needed UI Components to group more ArchitectureInformation need to be created
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

    /** Validates the ArchitectureStyleForm-Inputs and returns true if validation is successful. */
    public boolean validateForm() {
        boolean isFormCorrect = true;
        for (Entry<String, Binder<ArchitectureInformation>> entry :
                ungroupedArchitectureInformationBinders.entrySet()) {
            if (!entry.getValue().isValid()) {
                isFormCorrect = false;
            }
        }

        if (!isFormCorrect) {
            new Notification();
            add(Notification.show("Please fill out all architecture information!"));
        }
        return isFormCorrect;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

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

    public List<TextField> getUngroupedArchitectureInformation() {
        return ungroupedArchitectureInformation;
    }

    public Map<String, Binder<ArchitectureInformation>>
            getUngroupedArchitectureInformationBinders() {
        return ungroupedArchitectureInformationBinders;
    }

    public Map<Integer, Set<TwoColumnGridEntry>> getArchitectureInformationGroupsAndEntries() {
        return architectureInformationGroupsAndEntries;
    }
}
