package org.archcnl.ui.output.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.output.events.InputViewRequestedEvent;

public class SideBarLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    QueryView parent;
    Button gotoQueryViewButton = new Button("General Information", e -> parent.switchToQueryView());
    Button gotoCustomQueryViewButton =
            new Button("Custom Queries", e -> parent.switchToCustomQueryView());
    Button gotoFreeTextQueryViewButton =
            new Button("Free Text Queries", e -> parent.switchToFreeTextQueryView());

    public SideBarLayout(final QueryView parent) {
        this.parent = parent;
        gotoQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoCustomQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoFreeTextQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        VerticalLayout queryOptions =
                new VerticalLayout(
                        gotoQueryViewButton,
                        gotoCustomQueryViewButton,
                        gotoFreeTextQueryViewButton);

        Button returnToRuleEditorButton =
                new Button(
                        "Return to rule editor",
                        click -> fireEvent(new InputViewRequestedEvent(this, true)));
        add(queryOptions, returnToRuleEditorButton);
        setHeightFull();
        queryOptions.setSizeUndefined();
        returnToRuleEditorButton.setSizeUndefined();
        setFlexGrow(1, queryOptions);
        setFlexGrow(0, returnToRuleEditorButton);
        getStyle().set("background-color", "#3458eb");
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
