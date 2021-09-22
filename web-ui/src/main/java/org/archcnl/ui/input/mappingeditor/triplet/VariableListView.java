package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.VariableListContract.View;

public class VariableListView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1573494658930202757L;
    private Presenter<View> presenter;
    private HorizontalLayout content;

    public VariableListView(VariableListContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);

        content = new HorizontalLayout();
        content.setWidthFull();
        content.setPadding(true);
        content.getStyle().set("overflow-x", "auto");
    }

    @Override
    public void addVariableView(Variable variable) {
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
