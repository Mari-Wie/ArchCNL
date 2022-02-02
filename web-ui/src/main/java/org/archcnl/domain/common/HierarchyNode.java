package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.ConceptAndRelation;

public class HierarchyNode<T extends ConceptAndRelation> {
    private T entry;
    private String text;
    private List<HierarchyNode<T>> children;

    public HierarchyNode(String text) {
        entry = null;
        this.text = text;
        children = new ArrayList<HierarchyNode<T>>();
    }

    public HierarchyNode(List<HierarchyNode<T>> s) {
        entry = null;
        text = null;
        children = s;
    }

    public HierarchyNode(T p_entry) {
        this.entry = p_entry;
        text = null;
        children = new ArrayList<HierarchyNode<T>>();
    }

    public boolean hasEntry() {
        return entry != null;
    }

    public List<HierarchyNode<T>> getChildren() {
        return children;
    }

    public T getEntry() {
        return entry;
    }

    public String getName() {
        if (hasEntry()) {
            return entry.getName();
        }
        return text;
    }

    public String getDescription() {
        if (hasEntry()) {
            return entry.getDescription();
        }
        return "";
    }

    public boolean isEmpty() {
        return children.size() != 0;
    }

    public void addChild(HierarchyNode<T> hn) {
        children.add(hn);
    }

    public void add(T c) {
        addChild(new HierarchyNode<T>(c));
    }

    public void add(HierarchyNode<T> nn, String destination) {
        if (text == destination) {
            addChild(nn);
        } else {
            for (HierarchyNode<T> node : children) {
                add(nn, destination);
            }
        }
    }

    // TODO: handle case where element is not found
    public void remove(HierarchyNode<T> hn) {
        for (HierarchyNode<T> c : children) {
            if (hn == c) {
                removeChild(c);
                return;
            } else {
                c.remove(hn);
            }
        }
    }

    public void removeChild(HierarchyNode<T> hn) {
        children.remove(hn);
    }
}
