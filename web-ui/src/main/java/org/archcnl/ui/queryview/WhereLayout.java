package org.archcnl.ui.queryview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class WhereLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public WhereLayout() {
        addWhereTextLayout(0);
    }

    public void removeRow(final WhereTextBoxesLayout layout) {
        remove(layout);
    }

    public void clear() {
        removeAll();
        addWhereTextLayout(0);
    }

    public void addWhereTextLayout(final int position) {
        final WhereTextBoxesLayout newLayout = new WhereTextBoxesLayout();
        int newPosition = position;
        if (newPosition < getComponentCount()) {
            newPosition += 1;
        }

        addComponentAtIndex(newPosition, newLayout);

        final Registration regAdd = newLayout.addListener(AddWhereLayoutRequestEvent.class,
                e -> addWhereTextLayout(indexOf(e.getSource())));
        final Registration regMinus = newLayout.addListener(RemoveWhereLayoutRequestEvent.class,
                e -> removeRow(e.getSource()));
    }
}
