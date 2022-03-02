package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets;

import com.vaadin.flow.component.textfield.TextField;

public class VariableTextfieldWidget extends TextField {

    private static final long serialVersionUID = 1L;
    private String regexPattern;

    public VariableTextfieldWidget(String regex) {
        regexPattern = regex;
        addValueChangeListener(
                e -> {
                    checkRegex();
                });
    }

    public VariableTextfieldWidget(String regex, String regex2) {
        regexPattern = regex;
        addValueChangeListener(
                e -> {
                    checkRegex();
                });
    }

    public void checkRegex() {
        if (!getValue().matches(regexPattern)) {
            // showErrorMessage("Incorrect Input");
        }
    }

    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }
}
