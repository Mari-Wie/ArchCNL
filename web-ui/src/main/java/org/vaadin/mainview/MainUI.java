package org.vaadin.mainview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(
        name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/vaadin-button-styles.css")
public class MainUI extends VerticalLayout {

    private Component header, oldContent, footer;
    private VerticalLayout content;
    private MainUIPresenter presenter;

    public MainUI() {
        presenter = new MainUIPresenter(this);
        header = createHeader();
        content = createContent();
        footer = createFooter();
        setPadding(false);

        add(header);
        addAndExpand(content);
        add(footer);
    }

    private Component createHeader() {
        HorizontalLayout headerBox = new HorizontalLayout();

        Label projectTitelLabel = new Label("ARCHCNL");
        projectTitelLabel.getStyle().set("color", "White");
        projectTitelLabel.getStyle().set("font-size", "large");

        Button backButton = new Button("Back", e -> presenter.Back());
        Button forwardButton = new Button("Forward", e -> presenter.Forward());
        Button projectButton = new Button("Project", e -> presenter.importProject());
        Button rulesButton = new Button("Rules", e -> presenter.importRules());
        Button viewButton = new Button("View", e -> presenter.view());
        Button helpButton = new Button("Help", e -> presenter.help());

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

    private VerticalLayout createContent() {
        VerticalLayout vbox = new VerticalLayout();
        oldContent = presenter.getArchitectureRuleView();
        vbox.add(oldContent);
        return vbox;
    }

    private Component createFooter() {
        HorizontalLayout footerHbox = new HorizontalLayout();

        Label copyright = new Label("© 2021 University of Hamburg. All rights reserved");

        HorizontalLayout buttonHBox = new HorizontalLayout();
        Button contactButton = new Button("Contact", e -> presenter.contact());
        Button wikiButton = new Button("Wiki", e -> presenter.wiki());
        Button siteButton = new Button("Project Site", e -> presenter.projectSite());
        buttonHBox.add(copyright, contactButton, wikiButton, siteButton);

        footerHbox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footerHbox.addAndExpand(copyright);
        footerHbox.add(buttonHBox);
        footerHbox.setWidthFull();

        return footerHbox;
    }

    public void setContent(Component view) {
        content.remove(oldContent);
        content.add(view);
        oldContent = view;
    }

    public void setPresenter(MainUIPresenter presenter) {
        this.presenter = presenter;
    }
}
