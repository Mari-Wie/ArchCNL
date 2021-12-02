package org.archcnl.ui.common;

import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class AddRemoveButtonLayout  extends HorizontalLayout {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Button addButton = new Button(new Icon(VaadinIcon.PLUS));
    private Button minusButton = new Button(new Icon(VaadinIcon.MINUS));

    public AddRemoveButtonLayout(){
        addButton.addClickListener(
                e -> {
                    fireEvent(new AddWhereLayoutRequestEvent(this, false));
                });
        minusButton.addClickListener(
                e -> {
                    fireEvent(new RemoveWhereLayoutRequestEvent<AddRemoveButtonLayout>(this, false));
                });
        add(addButton);
        add(minusButton);
    }
    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);

}
}