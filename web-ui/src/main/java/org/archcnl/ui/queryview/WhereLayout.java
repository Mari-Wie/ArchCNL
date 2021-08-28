package org.archcnl.ui.queryview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class WhereLayout extends VerticalLayout {
    public WhereLayout() {
        addWhereTextLayout(0);
    }

    public void removeRow(WhereTextBoxesLayout layout) {
        remove(layout);
    }

    public void clear() {
        removeAll();
        addWhereTextLayout(0);
    }

    public void addWhereTextLayout(int position) {
        WhereTextBoxesLayout newLayout = new WhereTextBoxesLayout();
        int newPosition = position;
        if (newPosition < getComponentCount()) {
            newPosition += 1;
        }

        addComponentAtIndex(newPosition, newLayout);

        Registration regAdd =
                newLayout.addListener(
                        AddWhereLayoutRequestEvent.class,
                        e -> {
                            addWhereTextLayout(indexOf(e.getSource()));
                        });
        Registration regMinus =
                newLayout.addListener(
                        RemoveWhereLayoutRequestEvent.class,
                        e -> {
                            removeRow(e.getSource());
                        });
    }
}
