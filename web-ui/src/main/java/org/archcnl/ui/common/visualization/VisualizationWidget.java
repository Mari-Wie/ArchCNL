package org.archcnl.ui.common.visualization;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VisualizationWidget extends Dialog {

    private static final long serialVersionUID = -8178332071305758974L;
    private final File diagramFile;
    private final FileInputStream stream;
    private final StreamResource diagramResource;
    private final String plantUmlSource;

    public VisualizationWidget(File diagramImage, String plantUmlSource)
            throws FileNotFoundException {
        stream = new FileInputStream(diagramImage);
        this.diagramFile = diagramImage;
        this.diagramResource = new StreamResource("uml", () -> stream);
        this.plantUmlSource = plantUmlSource;
        add(createContentLayout());
    }

    private VerticalLayout createContentLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);
        layout.add(createTitleBar());
        Image diagram = new Image(diagramResource, "Error");
        diagram.setMaxWidth(40, Unit.CM);
        layout.add(diagram);
        DownloadLink link = createDownloadLink();
        layout.add(link);
        layout.setAlignSelf(Alignment.END, link);
        layout.add(createSourceTextArea());
        return layout;
    }

    private HorizontalLayout createTitleBar() {
        final Label title = new Label("Arch2UML");
        title.setClassName("card-title-box--title");
        final Button closeButton = new Button(new Icon(VaadinIcon.CLOSE), click -> this.close());
        final HorizontalLayout titleBar = new HorizontalLayout(title, closeButton);
        titleBar.setWidthFull();
        title.setWidthFull();
        titleBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return titleBar;
    }

    private DownloadLink createDownloadLink() {
        return new DownloadLink(diagramFile);
    }

    private TextArea createSourceTextArea() {
        TextArea textArea = new TextArea("PlantUML Source Code");
        textArea.setWidth(100, Unit.PERCENTAGE);
        textArea.setMinWidth(20, Unit.CM);
        textArea.setValue(plantUmlSource);
        textArea.setReadOnly(true);
        return textArea;
    }
}
