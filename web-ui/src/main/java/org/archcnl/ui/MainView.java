package org.archcnl.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.shared.Registration;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import org.archcnl.ui.events.EditOptionRequestedEvent;
import org.archcnl.ui.events.EditOptionRequestedEvent.EditOption;
import org.archcnl.ui.events.FooterOptionRequestedEvent;
import org.archcnl.ui.events.FooterOptionRequestedEvent.FooterOption;
import org.archcnl.ui.events.HelpOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent.ProjectOption;
import org.archcnl.ui.events.RulesOptionRequestedEvent;
import org.archcnl.ui.events.RulesOptionRequestedEvent.RulesOption;
import org.archcnl.ui.events.ViewOptionRequestedEvent;

public class MainView extends VerticalLayout {

    private static final long serialVersionUID = -4807002363716724924L;
    private HorizontalLayout header;
    private HorizontalLayout footer;
    private HorizontalLayout content;
    private MenuItem saveProjectMenuItem;

    public MainView(final HorizontalLayout content) {
        setSizeFull();
        header = createHeader();
        footer = createFooter();
        header.setHeight(5.0f, Unit.PERCENTAGE);
        footer.setHeight(5.0f, Unit.PERCENTAGE);
        setPadding(false);

        add(header);
        showContent(content);
        add(footer);
    }

    public void showContent(final HorizontalLayout newContent) {
        newContent.setHeight(87.4f, Unit.PERCENTAGE);
        replace(content, newContent);
        content = newContent;
    }

    private HorizontalLayout createHeader() {
        final HorizontalLayout headerBox = new HorizontalLayout();

        final Label title = new Label("ArchCNL");
        title.getStyle().set("color", "#76d0f1");
        title.getStyle().set("font-size", "x-large");
        title.getStyle().set("font-weight", "bold");

        final MenuBar menuBar = new MenuBar();
        final MenuItem project = menuBar.addItem("Project");
        final MenuItem edit = menuBar.addItem("Edit");
        final MenuItem rules = menuBar.addItem("Rules");
        menuBar.addItem("View", e -> fireEvent(new ViewOptionRequestedEvent(this, true)));
        menuBar.addItem("Help", e -> fireEvent(new HelpOptionRequestedEvent(this, true)));

        final SubMenu projectSubMenu = project.getSubMenu();
        projectSubMenu.addItem(
                "New Project",
                e -> fireEvent(new ProjectOptionRequestedEvent(this, true, ProjectOption.NEW)));
        projectSubMenu.addItem(
                "Open Project",
                e -> fireEvent(new ProjectOptionRequestedEvent(this, true, ProjectOption.OPEN)));
        saveProjectMenuItem =
                projectSubMenu.addItem(
                        "Save",
                        e ->
                                fireEvent(
                                        new ProjectOptionRequestedEvent(
                                                this, true, ProjectOption.SAVE)));
        saveProjectMenuItem.setEnabled(false);
        projectSubMenu.addItem(
                "Save As",
                e -> fireEvent(new ProjectOptionRequestedEvent(this, true, ProjectOption.SAVE_AS)));

        final SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem(
                "Undo", e -> fireEvent(new EditOptionRequestedEvent(this, true, EditOption.UNDO)));
        editSubMenu.addItem(
                "Redo", e -> fireEvent(new EditOptionRequestedEvent(this, true, EditOption.REDO)));

        final SubMenu rulesSubMenu = rules.getSubMenu();
        rulesSubMenu.addItem(
                "Import from File",
                e ->
                        fireEvent(
                                new RulesOptionRequestedEvent(
                                        this, true, RulesOption.IMPORT_FROM_FILE)));
        rulesSubMenu.addItem(
                "Import Rule Presets",
                e ->
                        fireEvent(
                                new RulesOptionRequestedEvent(
                                        this, true, RulesOption.IMPORT_RULE_PRESETS)));

        headerBox.add(title, menuBar);
        headerBox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerBox.setWidthFull();
        return headerBox;
    }

    private HorizontalLayout createFooter() {
        final HorizontalLayout footerHbox = new HorizontalLayout();

        final Label copyright = new Label("Developed at the University of Hamburg.");

        final HorizontalLayout buttonHBox = new HorizontalLayout();
        final Button contactButton =
                new Button(
                        "Contact",
                        e ->
                                fireEvent(
                                        new FooterOptionRequestedEvent(
                                                this, true, FooterOption.CONTACT)));
        final Button wikiButton =
                new Button(
                        "Wiki",
                        e ->
                                fireEvent(
                                        new FooterOptionRequestedEvent(
                                                this, true, FooterOption.WIKI)));
        final Button siteButton =
                new Button(
                        "Project Site",
                        e ->
                                fireEvent(
                                        new FooterOptionRequestedEvent(
                                                this, true, FooterOption.PROJECT_SITE)));
        buttonHBox.add(copyright, contactButton, wikiButton, siteButton);

        footerHbox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footerHbox.addAndExpand(copyright);
        footerHbox.add(buttonHBox);
        footerHbox.setWidthFull();

        return footerHbox;
    }

    public void showNewTab() {
        final VaadinServletRequest req = (VaadinServletRequest) VaadinService.getCurrentRequest();
        URI uri;
        try {
            uri = new URI(req.getRequestURL().toString());
            getUI().get().getPage().open(uri.toString(), "ArchCNL");
        } catch (URISyntaxException | NoSuchElementException e) {
            final Notification notification = new Notification();
            final Text errorMessage =
                    new Text("Opening a new tab failed unexpectedly. Try again later.");
            final Button okButton = new Button("OK", click -> notification.close());
            notification.add(errorMessage, okButton);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    public void setSaveProjectMenuItemEnabled(final boolean enabled) {
        saveProjectMenuItem.setEnabled(enabled);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
