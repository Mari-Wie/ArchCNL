package org.archcnl.ui.main;

import com.vaadin.flow.component.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.archcnl.domain.input.ProjectManager;
import org.archcnl.ui.common.ConfirmNotification;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.main.MainContract.View;
import org.archcnl.ui.main.io.OpenProjectDialog;
import org.archcnl.ui.main.io.SaveProjectDialog;
import org.archcnl.ui.output.component.QueryView;

public class MainPresenter
        implements MainContract.Presenter<MainContract.View>, PropertyChangeListener {

    private static final long serialVersionUID = 2859025859553864862L;
    private View view;
    private Component resultView;
    private Component inputView;

    public MainPresenter() {
        inputView = new InputView();
        resultView = new QueryView();
        ProjectManager.getInstance().addPropertyChangeListener(this);
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
        new OpenProjectDialog().open();
    }

    @Override
    public void showSaveProject() {
        new SaveProjectDialog().open();
    }

    @Override
    public void showNewTab() {
        view.showNewTab();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.setSaveProjectMenuItemEnabled(true);
    }

    @Override
    public void saveProject() {
        try {
            ProjectManager.getInstance().saveProject();
        } catch (IOException e) {
            new ConfirmNotification("Project file could not be written.").open();
        }
    }
}
