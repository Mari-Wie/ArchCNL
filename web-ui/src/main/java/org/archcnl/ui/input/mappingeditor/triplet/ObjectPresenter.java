package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectPresenter implements ObjectContract.Presenter<View> {

    private static final long serialVersionUID = -6274011448119690642L;
    private View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }
}
