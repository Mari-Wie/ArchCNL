package org.archcnl.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;

@Route("")
@PWA(
        name = "ArchCNL",
        shortName = "ArchCNL",
        description = "Check your software projects for architecture violations.",
        enableInstallPrompt = false)
@CssImport("./styles/main.css")
public class UiEntryPoint extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public UiEntryPoint() throws ConceptDoesNotExistException {
        setSizeFull();
        add(new MainPresenter().getView());
    }
}
