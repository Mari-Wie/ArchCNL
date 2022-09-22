package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public abstract class ClassOrEnum extends NamespaceContent implements DeclaredType, FamixType {

    private Optional<String> hasFullQualifiedName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    private List<AnnotationInstance> hasAnnotationInstance = new ArrayList<>();
    private List<Field> definesAttribute = new ArrayList<>();
    private List<Method> definesMethod = new ArrayList<>();

    protected ClassOrEnum(Variable variable) {
        super(variable);
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
                instance.setParent(this);
                this.hasAnnotationInstance.add(instance);
                break;
            case "definesAttribute":
                Field field = (Field) object;
                field.setParent(this);
                this.definesAttribute.add(field);
                break;
            case "definesMethod":
                Method method = (Method) object;
                method.setParent(this);
                this.definesMethod.add(method);
                break;
            default:
                throw new PropertyNotFoundException(property + " couldn't be set");
        }
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
        builder.append(buildParentSection());
        return builder.toString();
    }

    @Override
    public String getTypeRepresentation() {
        return getHighestRankingName();
    }

    protected String buildAnnotationSection() {
        if (hasAnnotationInstance.isEmpty()) {
            return "";
        }
        return " "
                + hasAnnotationInstance.stream()
                        .map(AnnotationInstance::buildPlantUmlCode)
                        .collect(Collectors.joining());
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
    protected List<String> buildBodySectionContentLines() {
        List<String> bodyContentLines = new ArrayList<>();
        bodyContentLines.addAll(buildAttributeLines());
        bodyContentLines.addAll(buildMethodLines());
        return bodyContentLines;
    }

    private List<String> buildAttributeLines() {
        return definesAttribute.stream().map(Field::buildPlantUmlCode).collect(Collectors.toList());
    }

    private List<String> buildMethodLines() {
        return definesMethod.stream().map(Method::buildPlantUmlCode).collect(Collectors.toList());
    }

    protected String buildVisibilityPrefixSection() {
        return modifierContainer.getVisibilityPrefix();
    }

    protected abstract String buildParentSection();
}
