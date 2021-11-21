package org.archcnl.ui.input.mappingeditor.triplet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.archcnl.domain.common.Variable;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListContract.View;

public class VariableListPresenter implements Presenter<View>, PropertyChangeListener {

    private static final long serialVersionUID = 1017932470677534140L;
    private VariableManager variableManager;
    private View view;

    public VariableListPresenter(VariableManager variableManager) {
        this.variableManager = variableManager;
        this.variableManager.addPropertyChangeListener(this);
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof List<?>) {
            List<?> variables = (List<?>) evt.getNewValue();
            if (variables.get(variables.size() - 1) instanceof Variable) {
                Variable newVariable = (Variable) variables.get(variables.size() - 1);
                view.addVariableView(newVariable);
            }
        }
    }
}
