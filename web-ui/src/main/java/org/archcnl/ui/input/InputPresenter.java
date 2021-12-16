package org.archcnl.ui.input;

import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.ui.input.InputContract.View;
import org.archcnl.ui.input.mappingeditor.ConceptEditorPresenter;
import org.archcnl.ui.input.mappingeditor.RelationEditorPresenter;
import org.archcnl.ui.input.ruleeditor.ArchitectureRulesLayout;
import org.archcnl.ui.input.ruleeditor.NewArchitectureRulePresenter;
import org.archcnl.ui.input.ruleeditor.NewArchitectureRuleView;
import org.archcnl.ui.main.MainPresenter;

public class InputPresenter implements InputContract.Presenter, InputContract.Remote {

    private static final long serialVersionUID = 7554955330532215929L;
    private View view;
    private MainPresenter mainPresenter;

    public InputPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void switchToConceptEditorView() {
        ConceptEditorPresenter conceptEditorPresenter = new ConceptEditorPresenter(this);
        view.changeCurrentlyShownView(conceptEditorPresenter.getMappingEditorView());
    }

    @Override
    public void switchToConceptEditorView(CustomConcept concept) {
        ConceptEditorPresenter conceptEditorPresenter = new ConceptEditorPresenter(this, concept);
        view.changeCurrentlyShownView(conceptEditorPresenter.getMappingEditorView());
    }

    @Override
    public void switchToRelationEditorView() {
        RelationEditorPresenter relationEditorPresenter = new RelationEditorPresenter(this);
        view.changeCurrentlyShownView(relationEditorPresenter.getMappingEditorView());
    }

    @Override
    public void switchToRelationEditorView(CustomRelation relation) {
        RelationEditorPresenter relationEditorPresenter =
                new RelationEditorPresenter(this, relation);
        view.changeCurrentlyShownView(relationEditorPresenter.getMappingEditorView());
    }

    @Override
    public void switchToArchitectureRulesView() {
        RulesOrMappingEditorView architectureRulesView = new ArchitectureRulesLayout(this);
        view.changeCurrentlyShownView(architectureRulesView);
    }

    @Override
    public void switchToNewArchitectureRuleView() {
        NewArchitectureRulePresenter presenter = new NewArchitectureRulePresenter(this);
        NewArchitectureRuleView newArchitectureRuleView = new NewArchitectureRuleView(presenter);
        view.changeCurrentlyShownView(newArchitectureRuleView);
    }

    @Override
    public ConceptAndRelationView createConceptAndRelationView() {
        return new ConceptAndRelationView(this, mainPresenter);
    }

    @Override
    public ArchitectureRulesLayout createArchitectureRulesLayout() {
        return new ArchitectureRulesLayout(this);
    }
}
