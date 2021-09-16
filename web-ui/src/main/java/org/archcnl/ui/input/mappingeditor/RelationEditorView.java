package org.archcnl.ui.input.mappingeditor;

import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;

    public RelationEditorView(MappingEditorContract.Presenter<View> presenter, InputView parent) {
        super(presenter, parent, "Relation");
    }
}
