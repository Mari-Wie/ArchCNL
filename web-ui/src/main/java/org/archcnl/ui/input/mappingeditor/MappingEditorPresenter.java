package org.archcnl.ui.input.mappingeditor;

import org.archcnl.ui.input.mappingeditor.MappingEditorContract.Presenter;

public abstract class MappingEditorPresenter implements MappingEditorContract.Presenter {

	private static final long serialVersionUID = -9123529250149326943L;

	public MappingEditorPresenter(MappingEditorContract.View<Presenter> view) {
        view.setPresenter(this);
    }

    @Override
    public void nameHasChanged(String newName) {
        System.out.println(newName);
    }
	
}
