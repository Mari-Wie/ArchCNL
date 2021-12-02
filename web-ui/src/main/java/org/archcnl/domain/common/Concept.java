package org.archcnl.domain.common;

import java.util.Objects;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public abstract class Concept extends ActualObjectType {

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

    public void changeName(String newName) throws ConceptAlreadyExistsException {
        if (!RulesConceptsAndRelations.getInstance()
                .getConceptManager()
                .doesConceptExist(new CustomConcept(newName, ""))) {
            this.name = newName;
            RulesConceptsAndRelations.getInstance().getConceptManager().conceptHasBeenUpdated(this);
        } else {
            throw new ConceptAlreadyExistsException(newName);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        RulesConceptsAndRelations.getInstance().getConceptManager().conceptHasBeenUpdated(this);
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
