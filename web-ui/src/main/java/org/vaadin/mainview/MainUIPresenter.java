package org.vaadin.mainview;

import com.vaadin.flow.component.Component;

public class MainUIPresenter {
    
    private MainUI view;
    private Component ruleView, resultView;
    
    public MainUIPresenter()
    {
        
    }
    
    public void setView(MainUI view)
    {
        this.view = view;
    }
        
    public Component getArchitectureRuleView()
    {
        return ruleView;
    }
    
    public Component getResultView()
    {
        return resultView;
    }
    
    public void setArchitectureRuleView()
    {
        view.setContent(ruleView);
    }
    
    public void setResultView()
    {
        view.setContent(resultView);
    }

    public void Back() {
        // Undo Action
    }

    public void Forward() {
        // Redo Action
    }   

    public void View() {
        // Open View Option Popup
    }

    public void Help() {
        // Open Help Popup
    }

    public void importProject() {
        // Open File Browser Popup
    }

    public void importRules() {
        // Open File Browser Popup
    }

    public void exportRules() {
        // Open File Browser Popup (select save location)
    }

    public void contact() {
        // Open Contact Popup
    }

    public void wiki() {
        // Open Wiki Webpage
    }

    public void projectSite() {
        // Open ProjectSite Webpage
    }

}
