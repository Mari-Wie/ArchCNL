package org.archcnl.ui.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.ui.main.MainContract.Presenter;
import org.archcnl.ui.main.MainContract.View;

@Route
@PWA(
        name = "ArchCNL",
        shortName = "ArchCNL",
        description = "Check your software projects for architecture violations.",
        enableInstallPrompt = false)
@CssImport("./styles/vaadin-button-styles.css")
public class MainView extends VerticalLayout implements MainContract.View {

    private static final long serialVersionUID = -4807002363716724924L;
    private HorizontalLayout header;
    private HorizontalLayout footer;
    private HorizontalLayout content;
    private Presenter<View> presenter;
    private MenuItem saveProjectMenuItem;

    public MainView() throws PropertyNotFoundException {
        presenter = new MainPresenter();
        presenter.setView(this);
        setSizeFull();
        header = createHeader();
        footer = createFooter();
        header.setHeight(5.0f, Unit.PERCENTAGE);
        footer.setHeight(5.0f, Unit.PERCENTAGE);
        setPadding(false);

        add(header);
        // initializes content field
        presenter.showArchitectureRuleView();
        add(footer);
    }

    @Override
    public void setContent(final HorizontalLayout newContent) {
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
        menuBar.addItem("View", e -> presenter.showView());
        menuBar.addItem("Help", e -> presenter.showHelp());

        final SubMenu projectSubMenu = project.getSubMenu();
        projectSubMenu.addItem("New Project", e -> presenter.showNewTab());
        projectSubMenu.addItem("Open Project", e -> presenter.showOpenProject());
        saveProjectMenuItem = projectSubMenu.addItem("Save", e -> presenter.saveProject());
        saveProjectMenuItem.setEnabled(false);
        projectSubMenu.addItem("Save As", e -> presenter.showSaveProject());

        final SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Undo", e -> presenter.undo());
        editSubMenu.addItem("Redo", e -> presenter.redo());

        final SubMenu rulesSubMenu = rules.getSubMenu();
        rulesSubMenu.addItem("Import from File", e -> presenter.showImportRulesFromFile());
        rulesSubMenu.addItem("Import Rule Presets", e -> presenter.showImportRulePresets());

        headerBox.add(title, menuBar);
        headerBox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerBox.setWidthFull();
        return headerBox;
    }

    private HorizontalLayout createFooter() {
        final HorizontalLayout footerHbox = new HorizontalLayout();

        final Label copyright = new Label("Developed at the University of Hamburg.");

        final HorizontalLayout buttonHBox = new HorizontalLayout();
        final Button contactButton = new Button("Contact", e -> presenter.showContact());
        final Button wikiButton = new Button("Wiki", e -> presenter.showWiki());
        final Button siteButton = new Button("Project Site", e -> presenter.showProjectSite());
        buttonHBox.add(copyright, contactButton, wikiButton, siteButton);

        footerHbox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footerHbox.addAndExpand(copyright);
        footerHbox.add(buttonHBox);
        footerHbox.setWidthFull();

        return footerHbox;
    }

    @Override
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

    @Override
    public void setSaveProjectMenuItemEnabled(final boolean enabled) {
        saveProjectMenuItem.setEnabled(enabled);
    }
}
