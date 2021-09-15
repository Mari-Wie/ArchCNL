package org.archcnl.ui.input.mappingeditor;

public class MappingCreationPresenter implements MappingCreationContract.Presenter {

    public MappingCreationPresenter(MappingCreationView view) {
        view.setPresenter(this);
    }

    @Override
    public void nameHasChanged(String newName) {
        System.out.println(newName);
    }
}
