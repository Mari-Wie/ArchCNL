package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public class AndTripletsEditorView extends VerticalLayout implements View {

    private static final long serialVersionUID = -6056440514075289398L;
    private Presenter<View> presenter;
    private Button addButton, deleteButton;
    private VerticalLayout boxContent, boxButtonLayout;
    private HorizontalLayout orBlock;
    private ArrayList<TripletView> tripletList;

    public AndTripletsEditorView(AndTripletsEditorContract.Presenter<View> presenter) {
        this.presenter = presenter;
        tripletList = new ArrayList<TripletView>();
        setPadding(false);
        setWidthFull();

        orBlock = new HorizontalLayout();
        orBlock.setPadding(false);
        orBlock.setWidthFull();

        boxContent = new VerticalLayout();
        boxContent.getStyle().set("border", "1px solid black");
        boxContent.add(new Label("OR-Block (All rows in this block are AND connected)"));
        boxContent.setWidth(95, Unit.PERCENTAGE);
        addNewTripletViewAfter();

        boxButtonLayout = new VerticalLayout();
        boxButtonLayout.setWidth(5, Unit.PERCENTAGE);
        addButton = new Button(new Icon(VaadinIcon.PLUS), click -> presenter.addButtonPressed());
        deleteButton = new Button(new Icon(VaadinIcon.TRASH), click -> presenter.delete());
        boxButtonLayout.add(addButton, deleteButton);

        orBlock.add(boxContent, boxButtonLayout);
        add(orBlock);
        this.presenter.setView(this);
    }

    @Override
    public void addNewTripletViewAfter(TripletContract.View tripletView) {
        int previousIndex = boxContent.indexOf((Component) tripletView);
        TripletView newTripletView =
                createTripletView(new TripletPresenter(presenter, Optional.ofNullable(null)));
        boxContent.addComponentAtIndex(previousIndex + 1, newTripletView);
        tripletList.add(previousIndex, newTripletView);
        updateLabels();
    }

    public void addNewTripletViewAfter() {
        TripletView newTripletView =
                createTripletView(new TripletPresenter(presenter, Optional.ofNullable(null)));
        boxContent.addComponentAtIndex(1, newTripletView);
        tripletList.add(0, newTripletView);
        updateLabels();
    }

    @Override
    public void deleteTripletView(TripletContract.View tripletView) {
        boxContent.remove((Component) tripletView);
        tripletList.remove(tripletView);

        if (getTripletPresenters().isEmpty()) {
            if (presenter.isLastAndTripletsEditor()) {
                TripletView newTripletView =
                        createTripletView(
                                new TripletPresenter(presenter, Optional.ofNullable(null)));
                boxContent.add(newTripletView);
                tripletList.add(newTripletView);
            } else {
                presenter.delete();
            }
        }
        updateLabels();
    }

    @Override
    public List<TripletPresenter> getTripletPresenters() {
        return boxContent
                .getChildren()
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

    private TripletView createTripletView(TripletPresenter tripletPresenter) {
        return new TripletView(tripletPresenter, presenter.getVariableManager());
    }

    @Override
    public void addNewTripletView(TripletPresenter tripletPresenter) {
        boxContent.add(new TripletView(tripletPresenter, presenter.getVariableManager()));
    }

    @Override
    public void clearContent() {
        boxContent.removeAll();
    }

    public void updateLabels() {
        tripletList.get(0).setLabels(true);
    }
}
