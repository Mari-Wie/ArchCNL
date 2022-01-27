package org.archcnl.domain.common;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HierarchyNode<T> {
    private boolean isLeaf;
    private T entry;
    private String text;
    private Set<HierarchyNode<T>> children;

    public HierarchyNode(String text) {
        isLeaf = false;
        entry = null;
        this.text = text;
        children = new HashSet<HierarchyNode<T>>();
    }

    public Set<HierarchyNode<T>> getChildren(){
        return children;
    }
 

   public  HierarchyNode(Set<HierarchyNode<T>> s) {
        isLeaf = false;
        entry = null;
        children = s;
    }

   public  HierarchyNode(T entry) {
        isLeaf = true;
        this.entry = entry;
        children = new HashSet<HierarchyNode<T>>();
    }

   @Override
   public String toString(){
        if(isLeaf){
            entry.toString();
        }
        return text;
    }
    String getDescription(){
        return "UNIMPLEMENTED PLEASE FIX DESCRIPTIONS";
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
