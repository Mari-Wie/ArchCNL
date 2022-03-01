package org.archcnl.ui.common.andtriplets.triplet;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import java.util.Optional;

import java.util.List;
import com.vaadin.flow.component.dnd.DropTarget;
import org.archcnl.domain.common.HierarchyNode;
import com.vaadin.flow.data.provider.ListDataProvider;

public class SelectionComponent extends ComboBox<String> implements DropTarget<SelectionComponent>
{
    private String name;
    private String type;

    public SelectionComponent(String placeholder){
        name = placeholder;

        setPlaceholder(name);
        setActive(true);
        setClearButtonVisible(true);

        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }
    private void handleDropEvent(Object data){
        //TODO: find a way to only handle events where the type is Hierarchynode to get rid of the instanceof
        if(data instanceof List ){
            List<HierarchyNode> nodes = (List<HierarchyNode>) data;
            String droppedName = nodes.get(0).getName();
            ListDataProvider dataProvider = (ListDataProvider) getDataProvider();
            if( dataProvider.getItems().contains(droppedName)){
                setValue(droppedName);
            }
            else{
                showErrorMessage("Not a " + name.toLowerCase());
            }
        }
    }

    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    public String getName(){
        return new String(name);
    }
    public void highlightWhenEmpty() {
        if (getOptionalValue().isEmpty()) {
            showErrorMessage(name +" not set");
        }
    }
    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
            }
};
