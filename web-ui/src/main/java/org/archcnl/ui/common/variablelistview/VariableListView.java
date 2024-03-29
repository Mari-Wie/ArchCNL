package org.archcnl.ui.common.variablelistview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class VariableListView extends VerticalLayout {

    private static final long serialVersionUID = 1573494658930202757L;
    private HorizontalLayout content;

    public VariableListView() {
        content = new HorizontalLayout();
        content.setWidthFull();
        content.setPadding(true);
        content.getStyle().set("overflow-x", "auto");
    }

    public void showVariableList(final Set<Variable> variables) {
        content.removeAll();
        if (getComponentCount() == 0) {
            addBasicView();
        }
        variables.forEach(this::addVariableView);
    }

    private void addVariableView(final Variable variable) {
        final Div createVariableCard = createVariableCard(variable);
        createVariableCard.setClassName("variable-card");
        content.add(createVariableCard);
    }

    private void addBasicView() {
        add(new Label("Variables"));
        add(content);
    }

    private Div createVariableCard(final Variable variable) {
        final Div card = new Div();
        card.add(new Text(variable.getName()));
        card.getStyle().set("border", "1px solid black");
        final DragSource<Div> cardDragSource = DragSource.configure(card);
        cardDragSource.setDragData(variable);
        cardDragSource.setDraggable(true);
        return card;
    }
}
