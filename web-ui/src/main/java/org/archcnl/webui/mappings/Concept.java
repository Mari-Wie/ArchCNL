package org.archcnl.webui.mappings;

public class Concept {

    public enum ConceptType {
        famix,
        architecture
    }

    private String name;
    private ConceptType type;

    public Concept(String name, ConceptType conceptType) {
        this.name = name;
        this.type = conceptType;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return type.toString() + ":" + name;
    }
}
