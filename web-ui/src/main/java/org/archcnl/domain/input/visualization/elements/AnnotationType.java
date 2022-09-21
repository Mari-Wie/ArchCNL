package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationType extends NamespaceContent implements FamixType {

    private Optional<String> hasFullQualifiedName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    // PlantUml does not allow for multiple stereotypes
    private Optional<AnnotationInstance> hasAnnotationInstance = Optional.empty();
    private List<AnnotationTypeAttribute> hasAnnotationTypeAttribute = new ArrayList<>();

    public AnnotationType(Variable variable) {
        super(variable);
    }

    @Override
    protected String buildNameSection() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildVisibilityPrefixSection());
        builder.append(getElementIdentifier() + " ");
        builder.append("\"" + getHighestRankingName() + "\"");
        builder.append(" as ");
        builder.append(variable.getName());
        builder.append(buildAnnotationSection());
        return builder.toString();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        switch (property) {
            case "hasName":
                this.hasName = Optional.of((String) object);
                break;
            case "hasFullQualifiedName":
                this.hasFullQualifiedName = Optional.of((String) object);
                break;
            case "hasModifier":
                this.modifierContainer.setModifier((String) object);
                break;
            case "hasAnnotationInstance":
                AnnotationInstance instance = (AnnotationInstance) object;
                instance.parentIsFound();
                this.hasAnnotationInstance = Optional.of(instance);
                break;
            case "hasAnnotationTypeAttribute":
                AnnotationTypeAttribute attribute = (AnnotationTypeAttribute) object;
                attribute.parentIsFound();
                this.hasAnnotationTypeAttribute.add(attribute);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
    }

    @Override
    protected String getHighestRankingName() {
        String nameSection = variable.transformToGui();
        if (hasFullQualifiedName.isPresent()) {
            nameSection = hasFullQualifiedName.get();
        }
        if (hasName.isPresent()) {
            nameSection = hasName.get();
        }
        return nameSection;
    }

    @Override
    protected String getElementIdentifier() {
        return "annotation";
    }

    @Override
    protected List<String> buildBodySectionContentLines() {
        return hasAnnotationTypeAttribute.stream()
                .map(AnnotationTypeAttribute::buildPlantUmlCode)
                .collect(Collectors.toList());
    }

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " " + hasAnnotationInstance.get().buildPlantUmlCode();
    }

    protected String buildVisibilityPrefixSection() {
        return modifierContainer.getVisibilityPrefix();
    }
}
