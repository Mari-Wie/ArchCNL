package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DeleteRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.EditRuleButtonPressedEvent;

public class RuleView extends HorizontalLayout {

    private static final long serialVersionUID = 4720863166018087691L;

    public RuleView(ArchitectureRule rule, int ruleIndex) {
        setWidthFull();
        Button editButton =
                new Button(
                        new Icon(VaadinIcon.EDIT),
                        click -> fireEvent(new EditRuleButtonPressedEvent(this, true, rule)));
        Button deleteButton =
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new DeleteRuleButtonPressedEvent(this, true, rule)));
        HorizontalLayout buttons = new HorizontalLayout(editButton, deleteButton);

        Text number = new Text(String.valueOf(ruleIndex) + ".");
        VerticalLayout vDivider = new VerticalLayout();
        vDivider.setWidth(20, Unit.PIXELS);
        vDivider.setHeight(100, Unit.PERCENTAGE);
        vDivider.setSpacing(true);
        vDivider.setMargin(true);
        Text ruleText = new Text(rule.transformToGui());
        HorizontalLayout content = new HorizontalLayout(number, vDivider, ruleText);
        content.setWidthFull();

        add(content, buttons);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
