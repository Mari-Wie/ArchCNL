package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ObjectContract {

    public interface View {

        public void updateItems();

        public void showErrorMessage(String message);

        public Optional<String> getSelectedItem();
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public List<String> getConceptNames();
    }
}
