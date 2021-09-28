package org.archcnl.ui.main;

import com.vaadin.flow.component.Component;
import java.io.Serializable;

public interface MainContract {

    public interface View extends Serializable {

        public void setContent(Component newContent);
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public void showArchitectureRuleView();

        public void showResultView();

        public void undo();

        public void redo();

        public void showView();

        public void showHelp();

        public void showImportProject();

        public void showImportRules();

        public void showExportRules();

        public void showContact();

        public void showWiki();

        public void showProjectSite();

        public void showImportRulesFromFile();

        public void showImportRulePresets();
    }
}
