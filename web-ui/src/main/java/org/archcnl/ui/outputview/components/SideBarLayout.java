package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.outputview.OutputView;
import org.archcnl.ui.outputview.events.InputViewRequestedEvent;

public class SideBarLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private OutputView parent;
    private Button gotoCustomQueryViewButton;
    private VerticalLayout queryOptions;

    public SideBarLayout(final OutputView parent) {
        this.parent = parent;
        Button gotoQueryViewButton =
                prepareButton("General Information", e -> parent.switchToQueryView());
        gotoCustomQueryViewButton =
                prepareButton("Custom Queries", e -> parent.switchToCustomQueryView());
        Button gotoFreeTextQueryViewButton =
                prepareButton("Free Text Queries", e -> parent.switchToFreeTextQueryView());
        queryOptions =
                new VerticalLayout(
                        gotoQueryViewButton,
                        gotoCustomQueryViewButton,
                        gotoFreeTextQueryViewButton);

        final Button returnToRuleEditorButton =
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

    private Button prepareButton(
            String text, ComponentEventListener<ClickEvent<Button>> clickListener) {
        Button newButton = new Button(text, clickListener);
        newButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return newButton;
    }

    public void addPinnedCustomQueryButton(CustomQueryPresenter customQueryPresenter) {
        Button newButton =
                prepareButton(
                        customQueryPresenter.getQueryName(),
                        e -> parent.switchToCustomQueryView(customQueryPresenter.getView()));
        queryOptions.addComponentAtIndex(2, newButton); // Add after standard custom query option
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
