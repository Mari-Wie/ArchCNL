package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import org.archcnl.domain.input.model.mappings.Relation;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicateView extends ComboBox<Relation> implements PredicateContract.View {

    private static final long serialVersionUID = -5423813782732362932L;
    private Presenter<View> presenter;

    public PredicateView(PredicateContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
    }
}
