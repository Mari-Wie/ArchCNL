package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class VariableSelectionContract {

    public interface View {

        public void setItem(String variableName);

        public void updateItems();

        public void showErrorMessage(String message);

        public Optional<String> getSelectedItem();
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public List<String> getVariableNames();

        public void addCustomValue(String variableName);

        public boolean doesVariableExist(String variableName);

        public void handleDropEvent(Object data);
    }
}
