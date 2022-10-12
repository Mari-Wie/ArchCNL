package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class CustomConceptElement extends PlantUmlElement {

    private CustomConcept concept;

    public CustomConceptElement(CustomConcept concept, String uniqueName) {
        super(new Variable(uniqueName), false);
        this.concept = concept;
    }

    @Override
    public String buildPlantUmlCode() {
        return buildNameSection();
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String buildNameSection() {
        StringBuilder builder = new StringBuilder();
        builder.append("note ");
        builder.append("\"" + concept.getName() + "\"");
        builder.append(" as ");
        builder.append(getIdentifier().get(0));
        return builder.toString();
    }
}
