package org.archcnl.ui.input;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.Concept;
import org.archcnl.domain.input.model.mappings.CustomConcept;

public class ConceptListEntry implements MappingListEntry {

    private List<MappingListEntry> children;
    private String name;
    private Concept content;
    private boolean isLeaf;

    public ConceptListEntry(String name, List<Concept> list) {
        children =
                list.stream()
                        .map(entry -> new ConceptListEntry(entry.toString(), entry))
                        .collect(Collectors.toList());
        this.name = name;
        isLeaf = false;
    }

    public ConceptListEntry(String name, Concept content) {
        this.name = name;
        this.content = content;
        children = Collections.<MappingListEntry>emptyList();
        isLeaf = true;
    }

    public Concept getContent() {
        return content;
    }

    public List<MappingListEntry> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return getContent().getName();
        } else {
            return name;
        }
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public boolean isAlterable() {
        return getContent() instanceof CustomConcept;
    }
}
