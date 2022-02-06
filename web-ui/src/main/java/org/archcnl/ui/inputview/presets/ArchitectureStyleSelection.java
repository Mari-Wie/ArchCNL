package org.archcnl.ui.inputview.presets;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.ui.inputview.presets.events.ArchitecturalStyleSelectedEvent;

public class ArchitectureStyleSelection extends VerticalLayout {

    private static final long serialVersionUID = 2495566558120787385L;
    private Text title;
    private RadioButtonGroup<ArchitecturalStyle> select;

    private ArchitecturalStyle selectedStyle;

    public ArchitectureStyleSelection() {
        title = new Text("Select archictectural style to open presets for");

        select = new RadioButtonGroup<ArchitecturalStyle>();

        select.setItems(ArchitecturalStyle.values());
        select.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        select.addValueChangeListener(
                event -> {
                    fireEvent(new ArchitecturalStyleSelectedEvent(this, false, event.getValue()));
                });
        add(title, select);
    }

    public ArchitecturalStyle getSelectedStyle() {
        return selectedStyle;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
