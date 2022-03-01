package org.archcnl.ui.common.andtriplets.triplet;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import java.util.Optional;
import com.vaadin.flow.component.dnd.DropTarget;
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
        System.out.println(data.toString());
        String n = data.toString();
        setValue(n.substring(1, n.length() - 1));
        //fireEvent(fireEvent(new SelectionComponentUpdateRequested(this, true));
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
