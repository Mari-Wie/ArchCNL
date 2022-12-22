package org.archcnl.ui.common.visualization;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.sourceforge.plantuml.SourceStringReader;

public class AbstractVisualizationRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -5275445192204593147L;
    private static final String TEMP_FILE_PREFIX = "uml-";
    private static final String PNG_FILE_EXTENSION = ".png";

    public AbstractVisualizationRequestedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
    }

    protected File writeDiagrammAsPng(String plantUmlCode) throws IOException {
        File tempFile = prepareTempFile();
        writeAsTemporaryPng(tempFile, plantUmlCode);
        return tempFile;
    }

    private File prepareTempFile() throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE_PREFIX, PNG_FILE_EXTENSION);
        tempFile.deleteOnExit();
        return tempFile;
    }

    private void writeAsTemporaryPng(File tempFile, String plantUmlCode) throws IOException {
        OutputStream pngStream = new FileOutputStream(tempFile);
        SourceStringReader reader = new SourceStringReader(plantUmlCode);
        reader.outputImage(pngStream);
    }

    protected void showErrorMessage(Exception e) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(e.getMessage()));

        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.setPosition(Position.MIDDLE);
        notification.open();
    }
}
