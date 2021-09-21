package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public class AndTripletsEditorView extends VerticalLayout implements View {

    private static final long serialVersionUID = -6056440514075289398L;
    private Presenter<View> presenter;

    public AndTripletsEditorView(AndTripletsEditorContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);

        getStyle().set("border", "1px solid black");
        add(new Text("AndBlock: All rows in this block are \"AND\" connected"));
        add(createTripletView());
    }

    @Override
    public void addNewTripletViewAfter(TripletContract.View tripletView) {
        int previousIndex = indexOf((Component) tripletView);
        addComponentAtIndex(previousIndex + 1, createTripletView());
    }

    @Override
    public void deleteTripletView(TripletContract.View tripletView) {
        remove((Component) tripletView);
        if (getComponentCount() == 0) {
            add(createTripletView());
        }
    }

    @Override
    public List<TripletPresenter> getTripletPresenters() {
        return getChildren()
                .filter(TripletView.class::isInstance)
                .map(TripletView.class::cast)
                .map(TripletView::getPresenter)
                .filter(TripletPresenter.class::isInstance)
                .map(TripletPresenter.class::cast)
                .collect(Collectors.toList());
    }

    private TripletView createTripletView() {
        TripletPresenter tripletPresenter = new TripletPresenter(presenter);
        return new TripletView(tripletPresenter, presenter.getVariableManager());
    }
}
