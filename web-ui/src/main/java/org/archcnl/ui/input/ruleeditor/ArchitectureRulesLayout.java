package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.input.ButtonClickResponder;
import org.archcnl.ui.input.CreateNewLayout;
import org.archcnl.ui.input.RulesOrMappingEditorView;

public class ArchitectureRulesLayout extends RulesOrMappingEditorView
        implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    CreateNewLayout createNewRuleLayout =
            new CreateNewLayout(
                    "Architecture Rules",
                    "Create new Arch Rule",
                    new ButtonClickResponder() {
                        @Override
                        public void onButtonClick() {}
                    });
    VerticalLayout rulesLayout = new VerticalLayout();

    public ArchitectureRulesLayout() {
        RulesConceptsAndRelations.getInstance()
                .getArchitectureRuleManager()
                .addPropertyChangeListener(this);
        // Remove style property to makes no sense in this layout
        // TODO: Separate ArchitectureRulesLayout from CreateNewLayout
        createNewRuleLayout.getStyle().remove("border");
        add(createNewRuleLayout);
        add(rulesLayout);
        getStyle().set("border", "1px solid black");
        updateRules();
    }

    private void updateRules() {
        rulesLayout.removeAll();
        List<ArchitectureRule> rules =
                RulesConceptsAndRelations.getInstance()
                        .getArchitectureRuleManager()
                        .getArchitectureRules();
        for (int i = 0; i < rules.size(); i++) {
            rulesLayout.add(new RuleView(rules.get(i), i + 1));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateRules();
    }
}
