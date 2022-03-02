package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.subjectcomponents.SubjectComponent;

@DomEvent("change")
public class DetermineStatementComponentEvent extends ComponentEvent<SubjectComponent> {

    private static final long serialVersionUID = 1L;

    public DetermineStatementComponentEvent(SubjectComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
