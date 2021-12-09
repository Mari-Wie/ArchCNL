package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.archcnl.domain.common.Variable;
import org.archcnl.ui.input.mappingeditor.VariableManager;

public class VariableListView extends VerticalLayout implements PropertyChangeListener {

    private static final long serialVersionUID = 1573494658930202757L;
    private HorizontalLayout content;
    private VariableManager variableManager;

    public VariableListView(VariableManager variableManager) {
        this.variableManager = variableManager;
        this.variableManager.addPropertyChangeListener(this);
        content = new HorizontalLayout();
        content.setWidthFull();
        content.setPadding(true);
        content.getStyle().set("overflow-x", "auto");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof List<?>) {
            List<?> variables = (List<?>) evt.getNewValue();
            if (variables.get(variables.size() - 1) instanceof Variable) {
                Variable newVariable = (Variable) variables.get(variables.size() - 1);
                addVariableView(newVariable);
            }
        }
    }

    private void addVariableView(Variable variable) {
        if (content.getComponentCount() == 0) {
            addBasicView();
        }
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
