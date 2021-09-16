package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.View;

public class SubjectPresenter implements Presenter<View> {

    private static final long serialVersionUID = 7992050926821966999L;
    private MappingEditorContract.Presenter<MappingEditorContract.View> superPresenter;
    private View view;

    public SubjectPresenter(
            MappingEditorContract.Presenter<MappingEditorContract.View> superPresenter) {
        this.superPresenter = superPresenter;
    }

    @Override
    public List<Variable> getVariables() {
        return superPresenter.getVariableManager().getVariables();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }
}
