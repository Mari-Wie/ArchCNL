package org.archcnl.ui.queryview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

class PauseButton extends Button {

    private static final long serialVersionUID = 1L;

    private Component mainLayout;
    private VaadinIcon icons[] = new VaadinIcon[] {VaadinIcon.PLAY_CIRCLE, VaadinIcon.PAUSE};
    private int currentIcon = 1;

    public PauseButton(final Component mainLayout) {
        addClickListener(e -> {
            e.getSource().setIcon(getNextIcon());
        });
        setIcon(getNextIcon());
    }

    private Icon getNextIcon() {
        final int nextIcon = (currentIcon + 1) % 2;
        final Icon newIcon = new Icon(icons[currentIcon]);
        currentIcon = nextIcon;
        return newIcon;
    }
}
