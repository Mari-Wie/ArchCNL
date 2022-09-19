package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationInstance extends PlantUmlElement {

    private Optional<AnnotationType> hasAnnotationType = Optional.empty();
    private List<AnnotationInstanceAttribute> hasAnnotationInstanceAttribute = new ArrayList<>();

    public AnnotationInstance(Variable variable) {
        super(variable, true);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("<<");
        builder.append(buildNameSection());
        if (!hasAnnotationInstanceAttribute.isEmpty()) {
            builder.append(buildAttributeSection());
        }
        builder.append(">>");
        return builder.toString();
    }

    private String buildNameSection() {
        if (hasAnnotationType.isPresent()) {
            return hasAnnotationType.get().getHighestRankingName();
        }
        return variable.transformToGui();
    }

    private String buildAttributeSection() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        String joinedAttributes =
                hasAnnotationInstanceAttribute.stream()
                        .map(AnnotationInstanceAttribute::buildPlantUmlCode)
                        .collect(Collectors.joining(", "));
        builder.append(joinedAttributes);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("hasAnnotationType".equals(property)) {
            this.hasAnnotationType = Optional.of((AnnotationType) object);
        } else if ("hasAnnotationInstanceAttribute".equals(property)) {
            this.hasAnnotationInstanceAttribute.add((AnnotationInstanceAttribute) object);
        } else {
            throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }
}
