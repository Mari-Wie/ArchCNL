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

        Button backButton = new Button("Back", e -> presenter.Back());
        backButton.getStyle().set("background-color", "#76d0f1");
        backButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        backButton.getStyle().set("color", "Black");

        Button forwardButton = new Button("Forward", e -> presenter.Forward());
        forwardButton.getStyle().set("background-color", "#76d0f1");
        forwardButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        forwardButton.getStyle().set("color", "Black");

        Button projectButton = new Button("Project", e -> presenter.importProject());
        projectButton.getStyle().set("background-color", "#76d0f1");
        projectButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        projectButton.getStyle().set("padding-right", "10px");
        projectButton.getStyle().set("color", "Black");

        Button rulesButton = new Button("Rules", e -> presenter.importRules());
        rulesButton.getStyle().set("background-color", "#76d0f1");
        rulesButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        rulesButton.getStyle().set("color", "Black");

        Button viewButton = new Button("View", e -> presenter.view());
        viewButton.getStyle().set("background-color", "#76d0f1");
        viewButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        viewButton.getStyle().set("color", "Black");

        Button helpButton = new Button("Help", e -> presenter.help());
        helpButton.getStyle().set("background-color", "#76d0f1");
        helpButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        helpButton.getStyle().set("color", "Black");

        headerBox.add(
                backButton, forwardButton, projectButton, rulesButton, viewButton, helpButton);
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
        contactButton.getStyle().set("background-color", "#76d0f1");
        contactButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        contactButton.getStyle().set("color", "Black");

        Button wikiButton = new Button("Wiki", e -> presenter.wiki());
        wikiButton.getStyle().set("background-color", "#76d0f1");
        wikiButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        wikiButton.getStyle().set("color", "Black");

        Button siteButton = new Button("Project Site", e -> presenter.projectSite());
        siteButton.getStyle().set("background-color", "#76d0f1");
        siteButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        siteButton.getStyle().set("padding-right", "10px");
        siteButton.getStyle().set("color", "Black");

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
}
