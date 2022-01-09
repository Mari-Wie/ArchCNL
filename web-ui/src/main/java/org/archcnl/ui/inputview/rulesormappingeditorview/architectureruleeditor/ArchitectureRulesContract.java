package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import java.io.Serializable;

public interface ArchitectureRulesContract {

    public interface View {}

    public interface Presenter<T extends View> extends Serializable {
        public void saveArchitectureRule(String potentialRule);

        public void returnToRulesView();
    }
}
