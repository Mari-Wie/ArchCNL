package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.containers.ModifierContainer;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class AnnotationType implements PlantUmlElement {

    private Variable variable;
    private Optional<String> hasName = Optional.empty();
    private Optional<String> hasFullQualifiedName = Optional.empty();
    private ModifierContainer modifierContainer = new ModifierContainer();
    // PlantUml does not allow for multiple stereotypes
    private Optional<AnnotationInstance> hasAnnotationInstance = Optional.empty();
    private List<AnnotationTypeAttribute> attributes = new ArrayList<>();

    public AnnotationType(Variable variable) {
        this.variable = variable;
    }

    @Override
    public String buildPlantUmlCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        // TODO Auto-generated method stub

    }

    protected String buildNameSection() {
        // TODO Auto-generated method stub
        return null;
    }
}
