package org.vaadin.mainview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(
        name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainUI extends VerticalLayout {

    private Component header, footer;
    Component content;
    private MainUIPresenter presenter;
    
    public MainUI()
    {            
        presenter = new MainUIPresenter();
        
        HorizontalLayout headerBox = new HorizontalLayout();
        headerBox.setHeight(10, Unit.PERCENTAGE);
        headerBox.add(createHeader());
        
        VerticalLayout contentBox = new VerticalLayout();
        contentBox.setAlignItems(Alignment.CENTER);
//        contentBox.setSizeFull();
//        contentBox.setWidthFull();
//        contentBox.setHeightFull();
//        contentBox.setHeight("800px");
//        contentBox.setHeight(80, Unit.PERCENTAGE);
//        contentBox.setDefaultHorizontalComponentAlignment(Alignment.END);
        contentBox.add(presenter.getArchitectureRuleView());
        
        HorizontalLayout footerBox = new HorizontalLayout();
        footerBox.setHeight(10, Unit.PERCENTAGE);
        footerBox.add(createFooter());
        
        add(headerBox, contentBox, footerBox);    
        contentBox.setHeight(80, Unit.PERCENTAGE);
        footerBox.setHeight(10, Unit.PERCENTAGE);
        headerBox.setHeight(10, Unit.PERCENTAGE);
        contentBox.setSizeFull();
    }
    
    private Component createHeader()
    {
        MenuBar menuBar = new MenuBar();        
            menuBar.addItem("Back", e -> presenter.Back());
            menuBar.addItem("Forward", e -> presenter.Forward());
            MenuItem project = menuBar.addItem("Project");
                project.getSubMenu().addItem("Import Project", e-> presenter.importProject());
            MenuItem rules = menuBar.addItem("Rules");
                rules.getSubMenu().addItem("Import Rules", e-> presenter.importRules());
                rules.getSubMenu().addItem("Export Rules", e-> presenter.exportRules());
            menuBar.addItem("View", e -> presenter.View());
            menuBar.addItem("Help", e -> presenter.Help());       
        menuBar.getStyle().set("border-style", "solid");
        return menuBar;
    }
    
    private Component createContent()
    {
        VerticalLayout vbox = new VerticalLayout();
        vbox.setHeightFull();
        return vbox;
    }
    
    private Component createFooter()
    {
        HorizontalLayout footerHbox = new HorizontalLayout();        
            Label copyright = new Label("© 2021 University of Hamburg. All rights reserved");        
            MenuBar menuBar = new MenuBar();  
                menuBar.addItem("Contact", e -> presenter.contact());
                menuBar.addItem("Wiki", e -> presenter.wiki());
                menuBar.addItem("Project Site", e -> presenter.projectSite());
        footerHbox.add(copyright, menuBar);
        //footerHbox.getStyle().set("background-color", "#3458eb");
        footerHbox.getStyle().set("border-style", "solid");
        footerHbox.setAlignItems(Alignment.END);//puts button in horizontal  center

        return footerHbox;            
    }
    
    public void setContent(Component content)
    {
        this.content = content;
    }
    
    public void setPresenter(MainUIPresenter presenter)
    {
        this.presenter = presenter;
    }
}
