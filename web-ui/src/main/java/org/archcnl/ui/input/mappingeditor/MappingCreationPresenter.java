package org.archcnl.ui.input.mappingeditor;

import org.archcnl.ui.input.mappingeditor.MappingCreationContract.Presenter;

public abstract class MappingCreationPresenter implements MappingCreationContract.Presenter {

	private static final long serialVersionUID = -9123529250149326943L;

	public MappingCreationPresenter(MappingCreationContract.View<Presenter> view) {
        view.setPresenter(this);
    }

    @Override
    public void nameHasChanged(String newName) {
        System.out.println(newName);
    }
	
}
