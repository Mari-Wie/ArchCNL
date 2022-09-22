package org.archcnl.domain.input.visualization.connections;

import java.util.Optional;
import org.archcnl.domain.input.visualization.PlantUmlPart;

public abstract class PlantUmlConnection implements PlantUmlPart {

    private String subjectId;
    private String objectId;
    private Optional<String> name = Optional.empty();
    private Optional<String> noteText = Optional.empty();
    private ArrowType arrowType;

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
        builder.append(" " + objectId);
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

    public void setName(String name) {
        this.name = Optional.of(name);
    }

    public void setNodeText(String text) {
        this.noteText = Optional.of(text);
    }
}
