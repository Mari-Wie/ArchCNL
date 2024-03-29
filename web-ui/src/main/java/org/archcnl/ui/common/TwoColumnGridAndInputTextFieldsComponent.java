package org.archcnl.ui.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import java.util.Set;

public class TwoColumnGridAndInputTextFieldsComponent extends VerticalLayout {
    /** */
    private static final long serialVersionUID = -5196033723426808719L;

    private HorizontalLayout row;

    private TextField info1TextField;
    private TextField info2TextField;
    private Button addInfoBtn;
    private Grid<TwoColumnGridEntry> infoGrid;

    public TwoColumnGridAndInputTextFieldsComponent(
            String column1,
            String column2,
            Set<TwoColumnGridEntry> gridItems,
            Binder<TwoColumnGridEntry> binder) {

        super.setHeight("40%");
        super.setWidthFull();

        row = new HorizontalLayout();
        row.setSpacing(true);
        row.setWidthFull();

        info1TextField = new TextField(column1);
        info1TextField.setWidth("45%");
        info2TextField = new TextField(column2);
        info2TextField.setWidth("45%");
        addInfoBtn = new Button(new Icon(VaadinIcon.PLUS));
        addInfoBtn.setWidth("10%");
        row.setAlignSelf(Alignment.END, addInfoBtn);
        row.setDefaultVerticalComponentAlignment(Alignment.END);

        infoGrid = new Grid<>(TwoColumnGridEntry.class, false);

        binder.forField(info1TextField)
                .asRequired()
                .bind(TwoColumnGridEntry::getInfo1, TwoColumnGridEntry::setInfo1);
        binder.forField(info2TextField)
                .asRequired()
                .bind(TwoColumnGridEntry::getInfo2, TwoColumnGridEntry::setInfo2);

        infoGrid.addColumn(TwoColumnGridEntry::getInfo1).setHeader(column1);
        infoGrid.addColumn(TwoColumnGridEntry::getInfo2).setHeader(column2);
        infoGrid.setItems(gridItems);

        addInfoBtn.addClickListener(
                e -> {

                    // validate input
                    if (binder.isValid()) {
                        TwoColumnGridEntry entry =
                                new TwoColumnGridEntry(
                                        info1TextField.getValue(), info2TextField.getValue());
                        gridItems.add(entry);
                        infoGrid.setItems(gridItems);
                    } else { // not valid
                        binder.validate();
                    }
                });

        infoGrid.setHeight("200px");
        infoGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        row.add(info1TextField, info2TextField, addInfoBtn);
        add(row, infoGrid);
    }
}
