package org.archcnl.ui.output.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

public class WhereLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public WhereLayout() {
        addWhereTextLayout(0);
    }

    public void removeRow(final WhereTextBoxesLayout layout) {
        remove(layout);
        if (collect().size() <= 0) {
            addWhereTextLayout(0);
        }
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
        newLayout.addListener(
                AddWhereLayoutRequestEvent.class, e -> addWhereTextLayout(indexOf(e.getSource())));
        newLayout.addListener(RemoveWhereLayoutRequestEvent.class, e -> removeRow(e.getSource()));
    }
}
