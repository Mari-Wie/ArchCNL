package org.archcnl.domain.common.conceptsandrelations;

import java.util.Objects;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;

public abstract class Concept extends ActualObjectType implements HierarchyObject {

    private String name;
    private String description;

    protected Concept(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof Concept) {
            final Concept that = (Concept) obj;
            return Objects.equals(this.getName(), that.getName());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(name);
    }

    public void changeName(String newName, ConceptManager conceptManager)
            throws ConceptAlreadyExistsException {
        if (!conceptManager.doesConceptExist(new CustomConcept(newName, ""))) {
            this.name = newName;
        } else {
            throw new ConceptAlreadyExistsException(newName);
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return getConceptType() + ":" + getName();
    }

    @Override
    public boolean matchesRelatableObjectType(ActualObjectType actualObjectType) {
        return actualObjectType instanceof Concept
                && Objects.equals(this.getName(), actualObjectType.getName());
    }

    protected abstract String getConceptType();
}
