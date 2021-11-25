package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

    public AndTripletsEditorView(AndTripletsEditorContract.Presenter<View> presenter) {
        this.presenter = presenter;
        setPadding(false);
        setWidthFull();

        orBlock = new HorizontalLayout();
        orBlock.setPadding(false);
        orBlock.setWidthFull();

        boxContent = new VerticalLayout();
        boxContent.getStyle().set("border", "1px solid black");
        boxContent.add(new Label("OR-Block (All rows in this block are AND connected)"));

        HorizontalLayout tripletLabelLayout = new HorizontalLayout();
        tripletLabelLayout.setWidthFull();

        TextField subjectLabel = new TextField();
        subjectLabel.setValue("Subject");
        subjectLabel.setEnabled(false);

        TextField predicateLabel = new TextField();
        predicateLabel.setValue("Predicate");
        predicateLabel.setEnabled(false);

        TextField objectLabel = new TextField();
        objectLabel.setValue("Object");
        objectLabel.setEnabled(false);

        tripletLabelLayout.add(subjectLabel, predicateLabel, objectLabel);

        boxContent.add(tripletLabelLayout);
        boxContent.add(
                createTripletView(new TripletPresenter(presenter, Optional.ofNullable(null))));
        boxContent.setWidth(95, Unit.PERCENTAGE);

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
        boxContent.addComponentAtIndex(
                previousIndex + 1,
                createTripletView(new TripletPresenter(presenter, Optional.ofNullable(null))));
    }

    @Override
    public void deleteTripletView(TripletContract.View tripletView) {
        boxContent.remove((Component) tripletView);
        if (getTripletPresenters().isEmpty()) {
            if (presenter.isLastAndTripletsEditor()) {
                boxContent.add(
                        createTripletView(
                                new TripletPresenter(presenter, Optional.ofNullable(null))));
            } else {
                presenter.delete();
            }
        }
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
}
