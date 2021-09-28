package org.archcnl.ui.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
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

        Label projectTitelLabel = new Label("ARCHCNL");
        projectTitelLabel.getStyle().set("color", "White");
        projectTitelLabel.getStyle().set("font-size", "large");

        Button backButton = new Button("Back", e -> presenter.undo());
        Button forwardButton = new Button("Forward", e -> presenter.redo());
        Button projectButton = new Button("Project", e -> presenter.showImportProject());
        Button rulesButton = new Button("Rules", e -> presenter.showImportRules());
        Button viewButton = new Button("View", e -> presenter.showView());
        Button helpButton = new Button("Help", e -> presenter.showHelp());

        headerBox.add(
                projectTitelLabel,
                backButton,
                forwardButton,
                projectButton,
                rulesButton,
                viewButton,
                helpButton);
        headerBox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerBox.setWidthFull();
        return headerBox;
    }

    private HorizontalLayout createFooter() {
        HorizontalLayout footerHbox = new HorizontalLayout();

        Label copyright = new Label("© 2021 University of Hamburg. All rights reserved");

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
}
