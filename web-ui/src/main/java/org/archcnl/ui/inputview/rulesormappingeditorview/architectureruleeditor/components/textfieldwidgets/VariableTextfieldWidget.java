package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets;

import com.vaadin.flow.component.textfield.TextField;
import java.util.Set;

public class VariableTextfieldWidget extends TextField {

    private static final long serialVersionUID = 1L;
    private Set<String> regexPatternList;

    public VariableTextfieldWidget(Set<String> regex) {
        regexPatternList = regex;
        addValueChangeListener(
                e -> {
                    checkRegex();
                });
    }

    public void addRegex(String Regex) {
        regexPatternList.add(Regex);
    }

    public void removeRegex(String Regex) {
        regexPatternList.remove(Regex);
    }

    private void checkRegex() {
        boolean validInput = false;
        for (String regexString : regexPatternList) {
            if (getValue().matches(regexString)) {
                validInput = true;
            }
        }
        showErrorMessage(validInput);
    }

    private void showErrorMessage(boolean validInput) {
        if (!validInput) {
            setErrorMessage("Invalid Input");
            setInvalid(true);
        }
    }
}
