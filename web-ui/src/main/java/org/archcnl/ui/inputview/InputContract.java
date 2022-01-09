package org.archcnl.ui.inputview;

import java.io.Serializable;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.ui.inputview.conceptandrelationlistview.ConceptAndRelationView;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.ArchitectureRulesLayout;

public interface InputContract {

    public interface View extends Serializable {

        public void changeCurrentlyShownView(RulesOrMappingEditorView newView);
    }

    public interface Presenter extends Serializable {

        public void setView(InputContract.View view);

        public ConceptAndRelationView createConceptAndRelationView();

        public ArchitectureRulesLayout createArchitectureRulesLayout();
    }

    public interface Remote extends Serializable {

        public void switchToConceptEditorView();

        public void switchToConceptEditorView(CustomConcept concept);

        public void switchToRelationEditorView();

        public void switchToRelationEditorView(CustomRelation relation);

        public void switchToArchitectureRulesView();

        public void switchToNewArchitectureRuleView();
    }
}
