package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DeleteRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.EditRuleButtonPressedEvent;

public class RuleComponent extends HorizontalLayout {

    private static final long serialVersionUID = 4720863166018087691L;

    public RuleComponent(ArchitectureRule rule, int ruleIndex) {
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
        Label validUntilLabel = new Label("");
        Label validFromLabel = new Label("");
        if (rule.getValidFrom() != null) {
            validFromLabel.setText(
                    "Valid from: " + produceValidityLayout(rule.getValidFrom(), validFromLabel));
        }
        if (rule.getValidUntil() != null) {
            validUntilLabel.setText(
                    "Valid until: " + produceValidityLayout(rule.getValidUntil(), validUntilLabel));
        }
        VerticalLayout validity = new VerticalLayout(validFromLabel, validUntilLabel);
        validity.setWidth(30, Unit.PERCENTAGE);
        HorizontalLayout content = new HorizontalLayout(number, vDivider, ruleText);
        content.setWidthFull();

        add(content, validity, buttons);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private String produceValidityLayout(LocalDate validityDate, Label validFromLabel) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(validityDate);
    }
}
