package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.archcnl.ui.input.RulesOrMappingEditorView;
import org.archcnl.ui.input.ruleeditor.components.SubjectComponent;
import org.archcnl.ui.input.ruleeditor.components.verbcomponents.VerbComponent;
import org.archcnl.ui.input.ruleeditor.events.DetermineVerbComponentEvent;

public class NewArchitectureRuleView extends RulesOrMappingEditorView
        implements ArchitectureRulesContract.View {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;
    private NewArchitectureRulePresenter presenter;
    private SubjectComponent subject;
    private VerbComponent verb;
    private HorizontalLayout buttonsLayout;

    /**
     * Architecture rules are made up out of a subject component (Every/no/... concept) describing
     * the topic of the rule and a verb component (Can/must/...) describing the rule the subject
     * must follow. Refer to the architecture rule tree in the wiki for a visualization.
     */
    public NewArchitectureRuleView(NewArchitectureRulePresenter presenter) {
        this.presenter = presenter;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        initializeLayout();
        verb.determineVerbComponent(subject.getFirstModifier());
    }

    private void initializeLayout() {
        subject = new SubjectComponent();
        verb = new VerbComponent();
        buttonsLayout = new HorizontalLayout();

        subject.addListener(
                DetermineVerbComponentEvent.class,
                event -> verb.determineVerbComponent(event.getSource().getFirstModifier()));

        saveButton = new Button("Save Rule", e -> saveRule());
        Checkbox activateExpertmode = new Checkbox("Activate Expertmode");
        activateExpertmode.addClickListener(e -> activateExpertMode(activateExpertmode.getValue()));
        buttonsLayout.setVerticalComponentAlignment(Alignment.CENTER, activateExpertmode);
        archRuleTextArea =
                new TextArea("Freetext mode", "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();
        buttonsLayout.setPadding(true);
        buttonsLayout.add(saveButton, activateExpertmode);

        add(subject, verb, buttonsLayout);
    }

    private void activateExpertMode(Boolean show) {
        if (show) {
            add(archRuleTextArea);
        } else {
            remove(archRuleTextArea);
        }
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            presenter.saveArchitectureRule(archRuleTextArea.getValue());
        } else {
            presenter.saveArchitectureRule(subject.getString() + verb.getString());
        }
        presenter.returnToRulesView();
    }

    // TODO Use Events to save architecture rule once merged into the master
    //    private void saveRule() {
    //        if (!archRuleTextArea.isEmpty()) {
    //            fireEvent(new SaveArchitectureRuleEvent(this, true, archRuleTextArea.getValue()));
    //        } else {
    //            fireEvent(new SaveArchitectureRuleEvent(this, true, subject.getString() +
    // verb.getString()));
    //        }
    //    }
    //
    //    @Override
    //    public <T extends ComponentEvent<?>> Registration addListener(
    //            final Class<T> eventType, final ComponentEventListener<T> listener) {
    //        return getEventBus().addListener(eventType, listener);
    //    }
}
