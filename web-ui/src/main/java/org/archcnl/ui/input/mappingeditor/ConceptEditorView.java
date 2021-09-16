package org.archcnl.ui.input.mappingeditor;

import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;

    public ConceptEditorView(MappingEditorContract.Presenter<View> presenter, InputView parent) {
        super(presenter, parent, "Concept");
    }
}
