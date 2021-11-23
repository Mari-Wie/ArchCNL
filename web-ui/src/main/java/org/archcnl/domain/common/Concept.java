package org.archcnl.domain.common;

import java.util.Objects;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public abstract class Concept extends ObjectType {

    private String name;
    private String description;

    protected Concept(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Concept(String name) {
        this.name = name;
        this.description = name + ": Description Missing";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Concept)) {
            return false;
        }
        Concept otherConcept = (Concept) o;
        return name.equals(otherConcept.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void changeName(String newName) throws ConceptAlreadyExistsException {
        if (!RulesConceptsAndRelations.getInstance()
                .getConceptManager()
                .doesConceptExist(new CustomConcept(newName))) {
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

    protected abstract String getConceptType();
}
