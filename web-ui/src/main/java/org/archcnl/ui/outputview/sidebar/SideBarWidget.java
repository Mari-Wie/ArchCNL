package org.archcnl.ui.outputview.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.outputview.queryviews.CustomQueryView;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsUiComponent;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

public class SideBarWidget extends VerticalLayout {

    private static final long serialVersionUID = 3732746285572139979L;
    private Tabs tabs;
    private SideBarTab customQueryTab;
    private SideBarTab freeTextQueryTab;

    public SideBarWidget(
            QueryResultsUiComponent defaultQueryView,
            CustomQueryView customQueryView,
            FreeTextQueryUiComponent freeTextQueryView) {
        setHeightFull();
        getStyle().set("overflow", "hidden");

        add(new Text("Query Options"));
        addTabs(defaultQueryView, customQueryView, freeTextQueryView);
        addReturnButton();
    }

    public void addReturnButton() {
        Button returnButton =
                new Button(
                        "Return to rule editor",
                        new Icon(VaadinIcon.CHEVRON_LEFT),
                        click -> fireEvent(new InputViewRequestedEvent(this, true)));

        add(returnButton);
    }

    public void addTabs(
            QueryResultsUiComponent defaultQueryView,
            CustomQueryView customQueryView,
            FreeTextQueryUiComponent freeTextQueryView) {

        customQueryTab = new SideBarTab("Custom Queries", VaadinIcon.AUTOMATION, customQueryView);
        freeTextQueryTab =
                new SideBarTab("Free Text Queries", VaadinIcon.TEXT_INPUT, freeTextQueryView);
        tabs =
                new Tabs(
                        new SideBarTab(
                                "General Information", VaadinIcon.INFO_CIRCLE, defaultQueryView),
                        customQueryTab,
                        freeTextQueryTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeightFull();

        tabs.addSelectedChangeListener(
                event -> {
                    if (event.getSelectedTab() instanceof SideBarTab) {
                        SideBarTab tab = (SideBarTab) event.getSelectedTab();
                        fireEvent(
                                new ShowComponentRequestedEvent(
                                        this, true, tab.getLinkedComponent()));
                    }
                });

        add(tabs);
    }

    public void addPinnedQueryTab(Component linkedComponent, String name) {
        SideBarTab newTab = new SideBarTab(name, VaadinIcon.PIN, linkedComponent);
        tabs.add(newTab);
        tabs.setSelectedTab(newTab);
    }

    public void updateCustomQueryTab(CustomQueryView customQueryView) {
        customQueryTab.setLinkedComponent(customQueryView);
    }

    public void updateFreeTextQueryTab(FreeTextQueryUiComponent freeTextQueryView) {
        freeTextQueryTab.setLinkedComponent(freeTextQueryView);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}