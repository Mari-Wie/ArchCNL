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
import org.archcnl.ui.outputview.queryviews.events.QueryNameUpdateRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

public class SideBarWidget extends VerticalLayout {

    private static final long serialVersionUID = 3732746285572139979L;
    private Tabs tabs;
    private SideBarTab customQueryTab;
    private SideBarTab freeTextQueryTab;

    public SideBarWidget(
            final QueryResultsUiComponent defaultQueryView,
            final CustomQueryView customQueryView,
            final FreeTextQueryUiComponent freeTextQueryView) {
        setHeightFull();
        getStyle().set("overflow", "hidden");

        add(new Text("Query Options"));
        addTabs(defaultQueryView, customQueryView, freeTextQueryView);
        addReturnButton();
    }

    public void addReturnButton() {
        final Button returnButton =
                new Button(
                        "Return to rule editor",
                        new Icon(VaadinIcon.CHEVRON_LEFT),
                        click -> fireEvent(new InputViewRequestedEvent(this, true)));

        add(returnButton);
    }

    public void addTabs(
            final QueryResultsUiComponent defaultQueryView,
            final CustomQueryView customQueryView,
            final FreeTextQueryUiComponent freeTextQueryView) {

        final SideBarTab generalInformationTab =
                new SideBarTab("General Information", VaadinIcon.INFO_CIRCLE, defaultQueryView);
        customQueryTab = new SideBarTab("Custom Queries", VaadinIcon.AUTOMATION, customQueryView);
        freeTextQueryTab =
                new SideBarTab("Free Text Queries", VaadinIcon.TEXT_INPUT, freeTextQueryView);
        tabs = new Tabs(generalInformationTab, customQueryTab, freeTextQueryTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeightFull();

        tabs.addSelectedChangeListener(
                event -> {
                    if (event.getSelectedTab() instanceof SideBarTab) {
                        final SideBarTab tab = (SideBarTab) event.getSelectedTab();
                        fireEvent(
                                new ShowComponentRequestedEvent(
                                        this, true, tab.getLinkedComponent()));
                    }
                });

        add(tabs);
    }

    public void addPinnedQueryTab(Component linkedComponent, String name) {
        final SideBarTab newTab = new SideBarTab(name, VaadinIcon.PIN, linkedComponent);
        tabs.add(newTab);
        tabs.setSelectedTab(newTab);
    }

    public void updateCustomQueryTab(final CustomQueryView customQueryView) {
        customQueryTab.setLinkedComponent(customQueryView);
    }

    public void updateFreeTextQueryTab(final FreeTextQueryUiComponent freeTextQueryView) {
        freeTextQueryTab.setLinkedComponent(freeTextQueryView);
    }

    public void updatePinnedQueryName(QueryNameUpdateRequestedEvent event) {
        tabs.getChildren()
                .filter(SideBarTab.class::isInstance)
                .map(SideBarTab.class::cast)
                .filter(tab -> !tab.equals(customQueryTab))
                .filter(tab -> !tab.equals(freeTextQueryTab))
                .filter(tab -> tab.getLinkedComponent().equals(event.getSource()))
                .findFirst()
                .ifPresent(tab -> tab.updateLabel(event.getName()));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
