package org.archcnl.ui.input;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.datatypes.mappings.Concept;
import org.archcnl.domain.input.datatypes.mappings.Relation;

public class TreeGridListEntry<T> {

    private List<T> children;
    private String name;
    private T content;
    private boolean isLeaf;

    public TreeGridListEntry(String name, List<T> children) {
        this.children = children;
        this.name = name;
        isLeaf = false;
    }

    public TreeGridListEntry(String name, T content) {
        this.name = name;
        this.content = content;
        children = Collections.<T>emptyList();
        isLeaf = true;
    }

    public T getContent() {
        return content;
    }

    public List<TreeGridListEntry<T>> getHierarchicalChildren() {
        List<TreeGridListEntry<T>> hierarchicalChildren =
                children.stream()
                        .map(entry -> (TreeGridListEntry<T>) new TreeGridListEntry<>(entry.toString(), entry))
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
