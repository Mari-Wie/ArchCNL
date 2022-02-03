package org.archcnl.ui.outputview.queryviews.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

class PlayPauseButton extends Button {

    private static final long serialVersionUID = 1L;

    private VaadinIcon[] icons;
    private int currentIcon;

    public PlayPauseButton(final Component mainLayout) {
        icons = new VaadinIcon[] {VaadinIcon.PLAY_CIRCLE, VaadinIcon.PAUSE};
        currentIcon = 1;
        addClickListener(e -> e.getSource().setIcon(getNextIcon()));
        setIcon(getNextIcon());
    }

    private Icon getNextIcon() {
        final int nextIcon = (currentIcon + 1) % 2;
        final Icon newIcon = new Icon(icons[currentIcon]);
        currentIcon = nextIcon;
        return newIcon;
    }
}
