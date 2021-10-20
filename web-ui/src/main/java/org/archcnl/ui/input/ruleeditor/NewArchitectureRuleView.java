package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.RulesOrMappingEditorView;

public class NewArchitectureRuleView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;
    private InputView parent;

    public NewArchitectureRuleView(InputView Parent) {
        parent = Parent;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        saveButton = new Button("Save Rule", e -> saveRule());
        archRuleTextArea = new TextArea("", "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();

        add(new Label("Write new Adoc Rule"));
        add(archRuleTextArea);
        add(saveButton);
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            try {
                ArchitectureRule newRule = parseArchitectureRule(archRuleTextArea.getValue());
                RulesConceptsAndRelations.getInstance()
                        .getArchitectureRuleManager()
                        .addArchitectureRule(newRule);
            } catch (NoArchitectureRuleException e) {
                e.printStackTrace();
            }
        }
        parent.switchToArchitectureRulesView();
    }

    private ArchitectureRule parseArchitectureRule(String potentialRule)
            throws NoArchitectureRuleException {
        if (false) {
            // TODO implement actual parsing
            throw new NoArchitectureRuleException(potentialRule);
        }
        return new ArchitectureRule(potentialRule);
    }
}
