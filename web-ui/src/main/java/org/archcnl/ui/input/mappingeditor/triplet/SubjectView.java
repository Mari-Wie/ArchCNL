package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionContract.View;

public class SubjectView extends VariableSelectionView {

    private static final long serialVersionUID = 3452412403240444015L;

    public SubjectView(VariableSelectionContract.Presenter<View> presenter) {
        super(presenter);
        setLabel("Subject");
    }
}
