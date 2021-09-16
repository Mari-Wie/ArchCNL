package org.archcnl.ui.input.mappingeditor;

import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;

public abstract class MappingEditorPresenter implements MappingEditorContract.Presenter<View> {

    private static final long serialVersionUID = -9123529250149326943L;
    private View view;

    @Override
    public void nameHasChanged(String newName) {
        System.out.println(newName);
    }

    @Override
    public VariableManager getVariableManager() {
        return getMapping().getVariableManager();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    protected abstract Mapping getMapping();
}
