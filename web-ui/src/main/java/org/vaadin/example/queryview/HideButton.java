package org.vaadin.example.queryview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

class HideButton extends Button {
    private Component mainLayout;
    private VaadinIcon icons[] = new VaadinIcon[] {VaadinIcon.EYE, VaadinIcon.EYE_SLASH};
    private int currentIcon = 1;

    public HideButton(Component mainLayout) {
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
        int nextIcon = (currentIcon + 1) % 2;
        Icon newIcon = new Icon(icons[currentIcon]);
        currentIcon = nextIcon;
        return newIcon;
    }
}
