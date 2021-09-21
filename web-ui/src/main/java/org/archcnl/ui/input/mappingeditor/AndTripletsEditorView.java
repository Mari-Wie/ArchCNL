package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public class AndTripletsEditorView extends VerticalLayout implements View {

    private static final long serialVersionUID = -6056440514075289398L;
    private Presenter<View> presenter;
    private Button addButton;
    private VerticalLayout content;

    public AndTripletsEditorView(AndTripletsEditorContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);

        setPadding(false);
        content = new VerticalLayout();
        content.getStyle().set("border", "1px solid black");
        content.add(new Label("AndBlock: All rows in this block are \"AND\" connected"));
        content.add(createTripletView());
        add(content);

        addButton =
                new Button(
                        "Add new AndBlock",
                        new Icon(VaadinIcon.PLUS),
                        click -> presenter.addButtonPressed());
        addButton.setWidthFull();

        getElement().addEventListener("mouseover", e -> presenter.mouseEnter());
        getElement().addEventListener("mouseout", e -> presenter.mouseLeave());
    }

    @Override
    public void addNewTripletViewAfter(TripletContract.View tripletView) {
        int previousIndex = content.indexOf((Component) tripletView);
        content.addComponentAtIndex(previousIndex + 1, createTripletView());
    }

    @Override
    public void deleteTripletView(TripletContract.View tripletView) {
        content.remove((Component) tripletView);
        if (getTripletPresenters().isEmpty()) {
            if (presenter.isLastAndTripletsEditor()) {
                content.add(createTripletView());
            } else {
                presenter.delete();
            }
        }
    }

    @Override
    public List<TripletPresenter> getTripletPresenters() {
        return content.getChildren()
                .filter(TripletView.class::isInstance)
                .map(TripletView.class::cast)
                .map(TripletView::getPresenter)
                .filter(TripletPresenter.class::isInstance)
                .map(TripletPresenter.class::cast)
                .collect(Collectors.toList());
    }

    public Presenter<View> getPresenter() {
        return presenter;
    }

    private TripletView createTripletView() {
        TripletPresenter tripletPresenter = new TripletPresenter(presenter);
        return new TripletView(tripletPresenter, presenter.getVariableManager());
    }

    @Override
    public void setAddButtonVisible(boolean visible) {
        if (visible) {
            add(addButton);
        } else {
            remove(addButton);
        }
    }
}
