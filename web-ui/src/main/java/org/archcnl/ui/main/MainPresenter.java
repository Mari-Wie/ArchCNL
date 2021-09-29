package org.archcnl.ui.main;

import com.vaadin.flow.component.Component;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.main.MainContract.View;
import org.archcnl.ui.output.component.QueryView;

public class MainPresenter implements MainContract.Presenter<MainContract.View> {

    private static final long serialVersionUID = 2859025859553864862L;
    private View view;
    private Component resultView;
    private Component inputView;

    public MainPresenter() {
        inputView = new InputView();
        resultView = new QueryView();
    }

    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void showArchitectureRuleView() {
        view.setContent(inputView);
    }

    @Override
    public void showResultView() {
        view.setContent(resultView);
    }

    @Override
    public void undo() {
        // Undo Action
        System.out.println("undo()");
    }

    @Override
    public void redo() {
        // Redo Action
        System.out.println("redo()");
    }

    @Override
    public void showView() {
        // Open View Option Popup
        System.out.println("showView()");
    }

    @Override
    public void showHelp() {
        // Open Help Popup
        System.out.println("showHelp()");
    }

    @Override
    public void showImportProject() {
        // Open File Browser Popup
        System.out.println("showImportProject()");
    }

    @Override
    public void showImportRules() {
        // Open File Browser Popup
        System.out.println("showImportRules()");
    }

    @Override
    public void showExportRules() {
        // Open File Browser Popup (select save location)
        System.out.println("showExportRules()");
    }

    @Override
    public void showContact() {
        // Open Contact Popup
        System.out.println("showContact()");
    }

    @Override
    public void showWiki() {
        // Open Wiki Webpage
        System.out.println("showWiki()");
    }

    @Override
    public void showProjectSite() {
        // Open ProjectSite Webpage
        System.out.println("showProjectSite()");
    }

    @Override
    public void showImportRulesFromFile() {
        System.out.println("showImportRulesFromFile()");
    }

    @Override
    public void showImportRulePresets() {
        System.out.println("showImportRulePresets()");
    }

    @Override
    public void showOpenProject() {
        view.showOpenProjectDialog();
    }

    @Override
    public void showNewTab() {
        view.showNewTab();
    }
}
