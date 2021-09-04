package org.archcnl.ui.queryview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<List<String>> collect() {
        return getChildren()
                .filter(child -> child.getClass() == WhereTextBoxesLayout.class)
                .map(WhereTextBoxesLayout.class::cast)
                .map(WhereTextBoxesLayout::getObjSubPredString)
                .collect(Collectors.toList());
    }

    public void addWhereTextLayout(final int position) {
        final WhereTextBoxesLayout newLayout = new WhereTextBoxesLayout();
        int newPosition = position;
        if (newPosition < getComponentCount()) {
            newPosition += 1;
        }

        addComponentAtIndex(newPosition, newLayout);

        final Registration regAdd =
                newLayout.addListener(
                        AddWhereLayoutRequestEvent.class,
                        e -> addWhereTextLayout(indexOf(e.getSource())));
        final Registration regMinus =
                newLayout.addListener(
                        RemoveWhereLayoutRequestEvent.class, e -> removeRow(e.getSource()));
    }
}
