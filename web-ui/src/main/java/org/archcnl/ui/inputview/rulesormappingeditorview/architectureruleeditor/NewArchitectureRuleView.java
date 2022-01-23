package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextArea;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;

public class NewArchitectureRuleView extends RulesOrMappingEditorView
        implements ArchitectureRulesContract.View {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;
    private NewArchitectureRulePresenter presenter;

    public NewArchitectureRuleView(final NewArchitectureRulePresenter presenter) {
        this.presenter = presenter;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        setClassName("architecture-rules");

        saveButton = new Button("Save Rule", e -> saveRule());
        archRuleTextArea =
                new TextArea(
                        "Create new architecture rule ",
                        "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();

        add(archRuleTextArea);
        add(saveButton);
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            presenter.saveArchitectureRule(archRuleTextArea.getValue());
        }
        presenter.returnToRulesView();
    }
}
