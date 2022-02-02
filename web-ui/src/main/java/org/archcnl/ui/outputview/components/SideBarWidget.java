package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.outputview.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.events.ShowComponentRequestedEvent;

public class SideBarWidget extends VerticalLayout {

    private static final long serialVersionUID = 3732746285572139979L;
    private Tabs tabs;
    private SideBarTab customQueryTab;

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

    public void addPinnedCustomQueryTab(final CustomQueryView customQueryView, final String name) {
        final Span nameSpan = new Span(name);
        nameSpan.setClassName("side-bar-text");
        final SideBarTab newTab = new SideBarTab(nameSpan, VaadinIcon.PIN, customQueryView);
        tabs.add(newTab);
        tabs.setSelectedTab(newTab);
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

        final Span genInfo = new Span("General Information");
        genInfo.setClassName("side-bar-text");
        final SideBarTab generalInformationTab =
                new SideBarTab(genInfo, VaadinIcon.INFO_CIRCLE, defaultQueryView);
        final Span customQuery = new Span("Custom Queries");
        customQuery.setClassName("side-bar-text");
        customQueryTab = new SideBarTab(customQuery, VaadinIcon.AUTOMATION, customQueryView);
        final Span freeText = new Span("Free Text Queries");
        freeText.setClassName("side-bar-text");
        final SideBarTab freeTextQueryTabs =
                new SideBarTab(freeText, VaadinIcon.TEXT_INPUT, freeTextQueryView);
        tabs = new Tabs(generalInformationTab, customQueryTab, freeTextQueryTabs);
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

    public void updateCustomQueryTab(final CustomQueryView customQueryView) {
        customQueryTab.setLinkedComponent(customQueryView);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
