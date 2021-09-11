package org.vaadin.mainview;

import org.archcnl.ui.input.InputView;

import com.vaadin.flow.component.Component;

public class MainUIPresenter {

    private MainUI view;
    private Component resultView, ruleView, inputView;

    public MainUIPresenter(MainUI view) {
        this.view = view;
        ruleView = new TestView(this);
        resultView = new TestView2(this);
        inputView = new InputView();
    }

    public void setView(MainUI view) {
        this.view = view;
    }

    public Component getArchitectureRuleView() {
        return ruleView;
    }

    public Component getResultView() {
        return resultView;
    }

    public void setArchitectureRuleView() {
        view.setContent(inputView);
        System.out.println("setArchitectureRuleView()");
    }

    public void setResultView() {
        view.setContent(resultView);
        System.out.println("setResultView()");
    }

    public void Back() {
        // Undo Action
        System.out.println("Back()");
    }

    public void Forward() {
        // Redo Action
        System.out.println("Forward()");
    }

    public void view() {
        // Open View Option Popup
        System.out.println("view()");
    }

    public void help() {
        // Open Help Popup
        System.out.println("help()");
    }

    public void importProject() {
        // Open File Browser Popup
        System.out.println("importProject()");
    }

    public void importRules() {
        // Open File Browser Popup
        System.out.println("importRules()");
    }

    public void exportRules() {
        // Open File Browser Popup (select save location)
        System.out.println("exportRules()");
    }

    public void contact() {
        // Open Contact Popup
        System.out.println("contact()");
    }

    public void wiki() {
        // Open Wiki Webpage
        System.out.println("wiki()");
    }

    public void projectSite() {
        // Open ProjectSite Webpage
        System.out.println("projectSite()");
    }
}
