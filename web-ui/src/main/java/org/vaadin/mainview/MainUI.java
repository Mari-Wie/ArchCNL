package org.vaadin.mainview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
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
@HtmlImport("./styles/main-style-module.html")
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
        projectTitelLabel.getStyle().set("padding-left", "10px");
        projectTitelLabel.getStyle().set("padding-right", "10px");
        projectTitelLabel.getStyle().set("font-size", "large");
        projectTitelLabel.getStyle().set("font-family", "Montserrat");

        Button backButton = new Button("Back", e -> presenter.Back());
        setButtonStyle(backButton);

        Button forwardButton = new Button("Forward", e -> presenter.Forward());
        setButtonStyle(forwardButton);

        Button projectButton = new Button("Project", e -> presenter.importProject());
        setButtonStyle(projectButton);

        Button rulesButton = new Button("Rules", e -> presenter.importRules());
        setButtonStyle(rulesButton);

        Button viewButton = new Button("View", e -> presenter.view());
        setButtonStyle(viewButton);

        Button helpButton = new Button("Help", e -> presenter.help());
        setButtonStyle(helpButton);

        headerBox.add(
                projectTitelLabel,
                backButton,
                forwardButton,
                projectButton,
                rulesButton,
                viewButton,
                helpButton);
        headerBox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerBox.setSpacing(false);
        headerBox.getThemeList().add("spacing-xs");

        headerBox.getStyle().set("background-color", "#2596be");
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
        copyright.setWidth(87, Unit.PERCENTAGE);
        copyright.getStyle().set("font-family", "Montserrat");
        copyright.getStyle().set("padding-left", "10px");

        Button contactButton = new Button("Contact", e -> presenter.contact());
        setButtonStyle(contactButton);

        Button wikiButton = new Button("Wiki", e -> presenter.wiki());
        setButtonStyle(wikiButton);

        Button siteButton = new Button("Project Site", e -> presenter.projectSite());
        setButtonStyle(siteButton);
        siteButton.getStyle().set("padding-right", "10px");

        footerHbox.add(copyright, contactButton, wikiButton, siteButton);
        footerHbox.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        footerHbox.setSpacing(false);
        footerHbox.getThemeList().add("spacing-xs");

        footerHbox.getStyle().set("background-color", "#2596be");
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

    public void setButtonStyle(Button but) {
        but.getStyle().set("background-color", "#76d0f1");
        but.addThemeVariants(ButtonVariant.LUMO_ICON);
        but.getStyle().set("color", "Black");
        but.getStyle().set("font-family", "Montserrat");
    }
}
