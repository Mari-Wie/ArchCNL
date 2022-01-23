package org.archcnl.ui.outputview.sidebar;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.outputview.components.CustomQueryView;
import org.archcnl.ui.outputview.components.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.components.QueryResultsUiComponent;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

public class SideBarWidget extends VerticalLayout {

    private static final long serialVersionUID = 3732746285572139979L;
    private Tabs tabs;
    private SideBarTab customQueryTab;

    public SideBarWidget(
            QueryResultsUiComponent defaultQueryView,
            CustomQueryView customQueryView,
            FreeTextQueryUiComponent freeTextQueryView) {
        setHeightFull();
        getStyle().set("overflow", "hidden");

        customQueryTab = new SideBarTab("Custom Queries", VaadinIcon.AUTOMATION, customQueryView);
        tabs =
                new Tabs(
                        new SideBarTab(
                                "General Information", VaadinIcon.INFO_CIRCLE, defaultQueryView),
                        customQueryTab,
                        new SideBarTab(
                                "Free Text Queries", VaadinIcon.TEXT_INPUT, freeTextQueryView));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeightFull();

        add(new Text("Query Options"));
        add(tabs);
        add(
                new Button(
                        "Return to rule editor",
                        new Icon(VaadinIcon.CHEVRON_LEFT),
                        click -> fireEvent(new InputViewRequestedEvent(this, true))));

        tabs.addSelectedChangeListener(
                event -> {
                    if (event.getSelectedTab() instanceof SideBarTab) {
                        SideBarTab tab = (SideBarTab) event.getSelectedTab();
                        fireEvent(
                                new ShowComponentRequestedEvent(
                                        this, true, tab.getLinkedComponent()));
                    }
                });
    }

    public void addPinnedCustomQueryTab(CustomQueryView customQueryView, String name) {
        SideBarTab newTab = new SideBarTab(name, VaadinIcon.PIN, customQueryView);
        tabs.add(newTab);
        tabs.setSelectedTab(newTab);
    }

    public void updateCustomQueryTab(CustomQueryView customQueryView) {
        customQueryTab.setLinkedComponent(customQueryView);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
