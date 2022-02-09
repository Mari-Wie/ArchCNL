package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.SubjectComponent;

@DomEvent("change")
public class DetermineVerbComponentEvent extends ComponentEvent<SubjectComponent> {

    private static final long serialVersionUID = 1L;

    public DetermineVerbComponentEvent(SubjectComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
