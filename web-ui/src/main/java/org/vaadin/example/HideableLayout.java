package org.vaadin.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

class HideableLayout extends HorizontalLayout {
    private Component mainLayout;
    private VaadinIcon icons[] = new VaadinIcon[] {VaadinIcon.EYE, VaadinIcon.EYE_SLASH};
    private int currentIcon = 0;
    private Button toggleButton = new Button();

    HideableLayout(Component mainLayout) {
        this.mainLayout = mainLayout;
        toggleButton.addClickListener(
                e -> {
                    mainLayout.setVisible(!mainLayout.isVisible());
                    e.getSource().setIcon(getNextIcon());
                });
        toggleButton.setIcon(getNextIcon());
        add(mainLayout, toggleButton);
    }

    private Icon getNextIcon() {
        int nextIcon = (currentIcon + 1) % 2;
        Icon newIcon = new Icon(icons[currentIcon]);
        currentIcon = nextIcon;
        return newIcon;
    }
}
