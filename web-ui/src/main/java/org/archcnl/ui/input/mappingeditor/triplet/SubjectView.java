package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.View;

public class SubjectView extends ComboBox<String> implements SubjectContract.View {

    private static final long serialVersionUID = 3452412403240444015L;

    private Presenter<View> presenter;

    public SubjectView(SubjectContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setLabel("Subject");
        setPlaceholder("Variable");
        updateItems();
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addCustomValueSetListener(event -> presenter.addCustomValue(event.getDetail()));
        addValueChangeListener(event -> setInvalid(false));
    }

    @Override
    public void updateItems() {
        setItems(presenter.getVariableNames());
    }

    @Override
    public void setItem(String variableName) {
        setValue(variableName);
    }

    @Override
    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    public Optional<String> getSelectedItem() {
        return Optional.ofNullable(getValue());
    }
}
