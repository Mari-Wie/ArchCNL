package org.archcnl.ui.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
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
import org.archcnl.ui.main.MainContract.Presenter;
import org.archcnl.ui.main.MainContract.View;

@Route
@PWA(
        name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/vaadin-button-styles.css")
public class MainView extends VerticalLayout implements MainContract.View {

    private static final long serialVersionUID = -4807002363716724924L;
    private Component header;
    private Component footer;
    private Component content;
    private Presenter<View> presenter;
    private MenuItem saveProjectMenuItem;

    public MainView() {
        presenter = new MainPresenter();
        presenter.setView(this);
        header = createHeader();
        // initializes content field
        presenter.showArchitectureRuleView();
        footer = createFooter();
        setPadding(false);

        add(header);
        addAndExpand(content);
        add(footer);
    }

    @Override
    public void setContent(Component newContent) {
        replace(content, newContent);
        content = newContent;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout headerBox = new HorizontalLayout();

        Label title = new Label("ArchCNL");
        title.getStyle().set("color", "White");
        title.getStyle().set("font-size", "large");

        MenuBar menuBar = new MenuBar();
        MenuItem project = menuBar.addItem("Project");
        MenuItem edit = menuBar.addItem("Edit");
        MenuItem rules = menuBar.addItem("Rules");
        menuBar.addItem("View", e -> presenter.showView());
        menuBar.addItem("Help", e -> presenter.showHelp());

        SubMenu projectSubMenu = project.getSubMenu();
        projectSubMenu.addItem("New Project", e -> presenter.showNewTab());
        projectSubMenu.addItem("Open Project", e -> presenter.showOpenProject());
        saveProjectMenuItem = projectSubMenu.addItem("Save", e -> presenter.saveProject());
        saveProjectMenuItem.setEnabled(false);
        projectSubMenu.addItem("Save As", e -> presenter.showSaveProject());

        SubMenu editSubMenu = edit.getSubMenu();
        editSubMenu.addItem("Undo", e -> presenter.undo());
        editSubMenu.addItem("Redo", e -> presenter.redo());

        SubMenu rulesSubMenu = rules.getSubMenu();
        rulesSubMenu.addItem("Import from File", e -> presenter.showImportRulesFromFile());
        rulesSubMenu.addItem("Import Rule Presets", e -> presenter.showImportRulePresets());

        headerBox.add(title, menuBar);
        headerBox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerBox.setWidthFull();
        return headerBox;
    }

    private HorizontalLayout createFooter() {
        HorizontalLayout footerHbox = new HorizontalLayout();

        Label copyright = new Label("Developed at the University of Hamburg.");

        HorizontalLayout buttonHBox = new HorizontalLayout();
        Button contactButton = new Button("Contact", e -> presenter.showContact());
        Button wikiButton = new Button("Wiki", e -> presenter.showWiki());
        Button siteButton = new Button("Project Site", e -> presenter.showProjectSite());
        buttonHBox.add(copyright, contactButton, wikiButton, siteButton);

        footerHbox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footerHbox.addAndExpand(copyright);
        footerHbox.add(buttonHBox);
        footerHbox.setWidthFull();

        return footerHbox;
    }

    @Override
    public void showNewTab() {
        VaadinServletRequest req = (VaadinServletRequest) VaadinService.getCurrentRequest();
        URI uri;
        try {
            uri = new URI(req.getRequestURL().toString());
            getUI().get().getPage().open(uri.toString(), "ArchCNL");
        } catch (URISyntaxException | NoSuchElementException e) {
            Notification notification = new Notification();
            Text errorMessage = new Text("Opening a new tab failed unexpectedly. Try again later.");
            Button okButton = new Button("OK", click -> notification.close());
            notification.add(errorMessage, okButton);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    @Override
    public void setSaveProjectMenuItemEnabled(boolean enabled) {
        saveProjectMenuItem.setEnabled(enabled);
    }
}
