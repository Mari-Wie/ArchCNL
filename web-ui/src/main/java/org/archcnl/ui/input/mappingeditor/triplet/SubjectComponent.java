package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class SubjectComponent extends VariableSelectionComponent {

    private static final long serialVersionUID = 7992050926821966999L;

    public SubjectComponent(VariableManager variableManager) {
        super(variableManager);
    }

    public String getSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return getValue();
    }

    public void setSubject(Variable subject) {
        setVariable(subject);
    }

    public void highlightWhenEmpty() {
        try {
            getSubject();
        } catch (InvalidVariableNameException e) {
            showErrorMessage("Invalid Variable name");
        } catch (SubjectOrObjectNotDefinedException e) {
            showErrorMessage("Variable not set");
        }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
