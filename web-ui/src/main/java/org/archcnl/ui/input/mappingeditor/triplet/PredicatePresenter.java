package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicatePresenter implements Presenter<View> {

    private static final long serialVersionUID = 6266956055576570360L;
    private View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }
}
