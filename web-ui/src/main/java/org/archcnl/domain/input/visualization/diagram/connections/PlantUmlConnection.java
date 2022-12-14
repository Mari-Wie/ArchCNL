package org.archcnl.domain.input.visualization.diagram.connections;

import java.util.Optional;

import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.rules.Cardinality;

public abstract class PlantUmlConnection implements PlantUmlPart {

    private String subjectId;
    private String objectId;
    private Optional<String> name = Optional.empty();
    private Optional<String> noteText = Optional.empty();
    private ArrowType arrowType;
    private ColorState colorState = ColorState.NEUTRAL;
    private Cardinality cardinality = Cardinality.UNLIMITED;
    private int quantity;

    protected PlantUmlConnection(String subjectId, String objectId, ArrowType arrowType) {
        this.subjectId = subjectId;
        this.objectId = objectId;
        this.arrowType = arrowType;
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(subjectId + " ");
        builder.append(arrowType.getPlantUmlCode());
        builder.append(" ");
        if (cardinality != Cardinality.UNLIMITED) {
            builder.append(cardinality.buildSection(quantity));
            builder.append(" ");
        }
        builder.append(objectId);
        if (colorState != ColorState.NEUTRAL) {
            builder.append(buildColorSection());
        }
        if (name.isPresent()) {
            builder.append(": ");
            builder.append(name.get());
        }
        if (noteText.isPresent()) {
            builder.append("\n");
            builder.append("note on link: ");
            builder.append(noteText.get());
        }
        return builder.toString();
    }

    @Override
    public void setColorState(ColorState colorState) {
        this.colorState = colorState;
    }

    public void setName(String name) {
        this.name = Optional.of(name);
    }

    public void setNoteText(String text) {
        this.noteText = Optional.of(text);
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private String buildColorSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(" #line:");
        builder.append(colorState.getColorName());
        builder.append(";text:");
        builder.append(colorState.getColorName());
        builder.append(" ");
        return builder.toString();
    }
}
