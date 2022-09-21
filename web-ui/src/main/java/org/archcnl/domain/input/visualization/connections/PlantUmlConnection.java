package org.archcnl.domain.input.visualization.connections;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.PlantUmlPart;

public abstract class PlantUmlConnection implements PlantUmlPart {

    private Variable subject;
    private Variable object;
    private Optional<String> name = Optional.empty();
    private Optional<String> noteText = Optional.empty();
    private ArrowType arrowType;

    protected PlantUmlConnection(Variable subject, Variable object, ArrowType arrowType) {
        this.subject = subject;
        this.object = object;
        this.arrowType = arrowType;
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(subject.getName() + " ");
        builder.append(arrowType.getPlantUmlCode());
        builder.append(" " + object.getName());
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
