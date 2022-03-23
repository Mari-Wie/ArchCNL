package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.button.Button;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;

public class PrespecifiedQueryComponent extends AbstractQueryComponent {

    private static final long serialVersionUID = 1L;

    private Button runButton =
            new Button(
                    "Run",
                    e -> fireEvent(new FreeTextRunButtonPressedEvent(gridView, true, getQuery())));

    public PrespecifiedQueryComponent(String defaultQueryText) {
        super(defaultQueryText);
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(queryTextArea, runButton, gridView);
    }
}
