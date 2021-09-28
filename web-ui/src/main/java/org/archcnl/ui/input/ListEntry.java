package org.archcnl.ui.input;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.Concept;
import org.archcnl.domain.input.model.mappings.Relation;

public class ListEntry<T> {

    private List<T> children;
    private String name;
    private T content;
    private boolean isLeaf;

    public ListEntry(String name, List<T> children) {
        this.children = children;
        this.name = name;
        isLeaf = false;
    }

    public ListEntry(String name, T content) {
        this.name = name;
        this.content = content;
        children = Collections.<T>emptyList();
        isLeaf = true;
    }

    public T getContent() {
        return content;
    }

    public List<ListEntry<T>> getHierarchicalChildren() {
        List<ListEntry<T>> hierarchicalChildren =
                children.stream()
                        .map(entry -> (ListEntry<T>) new ListEntry<>(entry.toString(), entry))
                        .collect(Collectors.toList());
        return hierarchicalChildren;
    }

    // temporal solution, ideally Concept and Relation should have these methods as the toString
    // method
    @Override
    public String toString() {
        if (getContent() instanceof Concept) {
            return ((Concept) getContent()).toStringRepresentation();
        } else if (getContent() instanceof Relation) {
            return ((Relation) getContent()).toStringRepresentation();
        } else {
            return name;
        }
    }

    public boolean isLeaf() {
        return isLeaf;
    }
}