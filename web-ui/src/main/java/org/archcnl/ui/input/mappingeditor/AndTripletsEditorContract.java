package org.archcnl.ui.input.mappingeditor;

import java.util.List;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public interface AndTripletsEditorContract {

    public interface View {

        public void addNewTripletView(TripletPresenter tripletPresenter);

        public void addNewTripletViewAfter(TripletView tripletView);

        public void deleteTripletView(TripletView tripletView);

        public List<TripletPresenter> getTripletPresenters();

        public void clearContent();
    }

    public interface Presenter<T extends View> {

        public void setView(T view);

        public VariableManager getVariableManager();

        public void addButtonPressed();

        public boolean isLastAndTripletsEditor();

        public void delete();

        public TripletView createEmptyTripletView();
    }
}
