package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HierarchyNode<T extends ObjectType> {
    private boolean isLeaf = false;
    private T entry;
    private String text;
    private List<HierarchyNode<T>> children;

    public HierarchyNode(String text) {
        isLeaf = false;
        entry = null;
        this.text = text;
        children = new ArrayList<HierarchyNode<T>>();
    }

    public List<HierarchyNode<T>> getChildren() {
        return children;
    }

    public HierarchyNode(List<HierarchyNode<T>> s) {
        isLeaf = false;
        entry = null;
        text = null;
        children = s;
    }

    public HierarchyNode(T p_entry) {
        isLeaf = true;
        this.entry = p_entry;
        text = null;
        children = new ArrayList<HierarchyNode<T>>();
    }

    public String getName() {
        if (isLeaf) {
            return entry.getName();
        }
        return text;
    }

    String getDescription() {
        if (isLeaf) {
            entry.getDescription();
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

    //TODO: handle case where element is not found
    public void remove(HierarchyNode<T> hn){
        for (HierarchyNode<T> c : children){
            if(hn == c){
                removeChild(c);
                return;
            }
            else{
                c.remove(hn);
            }
        }
    }

    public void removeChild(HierarchyNode<T> hn) {
        children.remove(hn);
    }

    private List<T> gather(List<T> list) {
        if (isLeaf) {
            list.add(this.entry);
        } else {
            for (HierarchyNode<T> child : children) {
                list.addAll(child.gather());
            }
        }
        return list;
    }

    public List<T> gather() {
        List<T> list = new LinkedList<T>();
        return gather(list);
    }
}
