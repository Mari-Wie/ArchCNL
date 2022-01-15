package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.events.AddAndTripletsViewButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.DeleteAndTripletsViewRequestedEvent;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public class AndTripletsEditorView extends VerticalLayout {

    private static final long serialVersionUID = -6056440514075289398L;
    private VerticalLayout boxContent;

    public AndTripletsEditorView(TripletView emptyTripletView) {
        setPadding(false);
        setWidthFull();

        HorizontalLayout orBlock = new HorizontalLayout();
        orBlock.setPadding(false);
        orBlock.setWidthFull();

        boxContent = new VerticalLayout();
        boxContent.getStyle().set("border", "1px solid black");
        boxContent.add(new Label("OR-Block (All rows in this block are AND connected)"));
        boxContent.add(emptyTripletView);
        boxContent.setWidth(95, Unit.PERCENTAGE);

        VerticalLayout boxButtonLayout = new VerticalLayout();
        boxButtonLayout.setWidth(5, Unit.PERCENTAGE);
        Button addButton =
                new Button(
                        new Icon(VaadinIcon.PLUS),
                        click -> fireEvent(new AddAndTripletsViewButtonPressedEvent(this, true)));
        Button deleteButton =
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new DeleteAndTripletsViewRequestedEvent(this, true)));
        boxButtonLayout.add(addButton, deleteButton);

        orBlock.add(boxContent, boxButtonLayout);
        add(orBlock);
        updateLabels();
    }

    public void addNewTripletViewAfter(TripletView oldTripletView, TripletView newTripletView) {
        int previousIndex = boxContent.indexOf((Component) oldTripletView);
        boxContent.addComponentAtIndex(previousIndex + 1, newTripletView);
    }

    public void deleteTripletView(TripletView tripletView) {
        boxContent.remove((Component) tripletView);
    }

    public void addTripletView(TripletView tripletView) {
        boxContent.add(tripletView);
    }

    public void clearContent() {
        boxContent.removeAll();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void updateLabels() {
        if (getFirstTripletView().isPresent()) {
            TripletView firstTriplet = (TripletView) getFirstTripletView().get();
            firstTriplet.setLabels();
        }
    }

    private Optional<Component> getFirstTripletView() {
        return boxContent.getChildren().filter(TripletView.class::isInstance).findFirst();
    }
}
