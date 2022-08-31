package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.domain.common.HierarchyNode;

public class SelectionComponent extends ComboBox<String> implements DropTarget<SelectionComponent> {

    private static final long serialVersionUID = 5467026470229011239L;
    private String name;

    public SelectionComponent(String placeholder) {
        name = placeholder;

        setPlaceholder(name);
        setActive(true);
        setClearButtonVisible(true);

        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    protected void handleDropEvent(Object data) {
        String droppedName = "";
        if (data instanceof List) {
            List<HierarchyNode> nodes = (List<HierarchyNode>) data;
            droppedName = nodes.get(0).getName();
        }
        checkedSetValue(droppedName);
    }

    protected void checkedSetValue(String val) {
        ListDataProvider<String> dataProvider = (ListDataProvider) getDataProvider();
        if (dataProvider.getItems().contains(val)) {
            setValue(val);
        } else {
            showErrorMessage("Not a " + name.toLowerCase());
        }
    }

    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    public String getName() {
        return name;
    }

    public void highlightWhenEmpty() {
        if (getOptionalValue().isEmpty()) {
            showErrorMessage(name + " not set");
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
;
