package org.archcnl.input.datatypes.mappings;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.input.exceptions.AllConceptsSupportedException;

public class Relation {
    // TODO properly allow the relation to non concepts

    public enum RelationType {
        rdf,
        famix,
        architecture,
        matches
    }

    // The name used in the UI
    private String name;
    // The name used in the architecture rule file
    private String realName;
    private RelationType type;
    // The object types this relation relates to
    // Can be empty if the relation supports all concepts as objects
    private List<Concept> concepts;
    private boolean canRelateToString;

    /**
     * Constructs a relation that relates to the given concept. Concepts can be empty if relation
     * supports all concepts as objects.
     *
     * @param name The name of the relation used in the UI and in written files
     * @param relationType The type of the relation
     * @param concepts The concepts this relation relates to
     */
    public Relation(String name, RelationType relationType, List<Concept> concepts) {
        this.name = name;
        this.realName = name;
        this.type = relationType;
        this.concepts = concepts;
        this.canRelateToString = false;
    }

    /**
     * Constructs a relation that can relate to all concepts
     *
     * @param name The name of the relation used in the UI and in written files
     * @param relationType The type of the relation
     */
    public Relation(String name, RelationType relationType) {
        this.name = name;
        this.realName = name;
        this.type = relationType;
        this.concepts = new LinkedList<>();
        this.canRelateToString = true;
    }

    /**
     * Special constructor for the rdf:type relation (a.k.a. "is-of-type"). Do not use this
     * constructor for any other relations.
     *
     * @param name The name of the relation used in the UI
     * @param realName The name of the relation when written in RDF format
     */
    public Relation(String name, String realName) {
        this.name = name;
        this.realName = realName;
        this.type = RelationType.rdf;
        this.concepts = new LinkedList<>();
        this.canRelateToString = false;
    }

    public String toStringRepresentation() {
        return type.toString() + ":" + realName;
    }

    public boolean doesRelationSupportAllConceptsAsObjects() {
        return concepts.isEmpty();
    }

    public boolean canRelateToConcept(Concept concept) {
        return concepts.isEmpty() || concepts.contains(concept);
    }

    public List<Concept> getRelatedConcepts() throws AllConceptsSupportedException {
        if (!concepts.isEmpty()) {
            return concepts;
        }
        throw new AllConceptsSupportedException(name);
    }

    public String getName() {
        return name;
    }

    public RelationType getRelationType() {
        return type;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public boolean canRelateToString() {
        return canRelateToString;
    }
}
