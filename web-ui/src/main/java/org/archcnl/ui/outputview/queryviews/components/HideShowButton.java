package org.archcnl.ui.outputview.queryviews.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

class HideShowButton extends Button {

    private static final long serialVersionUID = 1L;

    private Component mainLayout;
    private VaadinIcon[] icons;
    private int currentIcon;

    public HideShowButton(final Component mainLayout) {
        icons = new VaadinIcon[] {VaadinIcon.EYE, VaadinIcon.EYE_SLASH};
        currentIcon = 1;
        if (mainLayout != null) {
            this.mainLayout = mainLayout;
        } else {
            // TODO Add error handling
        }
        addClickListener(
                e -> {
                    this.mainLayout.setVisible(!this.mainLayout.isVisible());
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
