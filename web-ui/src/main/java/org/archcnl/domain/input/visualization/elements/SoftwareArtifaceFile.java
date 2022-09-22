package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class SoftwareArtifaceFile extends PlantUmlElement {

    private Optional<String> hasPath = Optional.empty();

    public SoftwareArtifaceFile(Variable variable) {
        super(variable, false);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("note as " + buildNameSection() + "\n");
        builder.append("===File\n");
        if (hasPath.isPresent()) {
            builder.append("\t" + hasPath.get() + "\n");
        }
        builder.append("end note");
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("hasPath".equals(property)) {
            this.hasPath = Optional.of((String) object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String buildNameSection() {
        return variable.getName();
    }
}
