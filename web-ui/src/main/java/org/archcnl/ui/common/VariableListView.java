package org.archcnl.ui.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import org.archcnl.domain.common.Variable;

public class VariableListView extends VerticalLayout {

    private static final long serialVersionUID = 1573494658930202757L;
    private HorizontalLayout content;

    public VariableListView() {
        content = new HorizontalLayout();
        content.setWidthFull();
        content.setPadding(true);
        content.getStyle().set("overflow-x", "auto");
    }

    public void showVariableList(List<Variable> variables) {
        content.removeAll();
        if (getComponentCount() == 0) {
            addBasicView();
        }
        variables.forEach(this::addVariableView);
    }

    private void addVariableView(Variable variable) {
        content.add(createVariableCard(variable));
    }

    private void addBasicView() {
        add(new Label("Variables"));
        add(content);
    }

    private Div createVariableCard(Variable variable) {
        Div card = new Div();
        card.add(new Text(variable.getName()));
        card.getStyle().set("border", "1px solid black");
        DragSource<Div> cardDragSource = DragSource.configure(card);
        cardDragSource.setDragData(variable);
        cardDragSource.setDraggable(true);
        return card;
    }
}
