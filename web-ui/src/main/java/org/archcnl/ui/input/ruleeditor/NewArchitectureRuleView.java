package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.archcnl.ui.input.RulesOrMappingEditorView;
import org.archcnl.ui.input.ruleeditor.components.SubjectComponent;
import org.archcnl.ui.input.ruleeditor.components.VerbComponent;

public class NewArchitectureRuleView extends RulesOrMappingEditorView
        implements ArchitectureRulesContract.View {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;
    private NewArchitectureRulePresenter presenter;
    private SubjectComponent subject;
    private VerbComponent verb;

    public NewArchitectureRuleView(NewArchitectureRulePresenter presenter) {
        this.presenter = presenter;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        subject = new SubjectComponent();
        verb = new VerbComponent();

        HorizontalLayout footer = new HorizontalLayout();
        saveButton = new Button("Save Rule", e -> saveRule());
        Checkbox activateExpertmode = new Checkbox("Activate Expertmode");
        activateExpertmode.addClickListener(e -> activateExpertMode(activateExpertmode.getValue()));
        footer.setVerticalComponentAlignment(Alignment.CENTER, activateExpertmode);
        archRuleTextArea =
                new TextArea("Freetext mode", "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();
        footer.add(saveButton, activateExpertmode);

        add(subject, verb, footer);
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
}
