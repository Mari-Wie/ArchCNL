package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class HierarchyManager<T extends HierarchyObject> {

    List<HierarchyNode<T>> hierarchy_roots;

    public HierarchyManager() {
        hierarchy_roots = new ArrayList<HierarchyNode<T>>();
    }

    public void addHierarchyRoot(String name) {
        hierarchy_roots.add(new HierarchyNode<T>(name));
    }

    public void addHierarchyRoot(HierarchyNode<T> newRoot) {
        hierarchy_roots.add(newRoot);
    }

    private boolean containedInRoots(HierarchyNode<T> node){
        for (HierarchyNode<T> n : hierarchy_roots){
            if (node.equals(n)) {return true;}
        }
        return false;
    }

    public void moveNode(HierarchyNode<T> node, HierarchyNode<T> target) {
        if (containedInRoots(node)) {
            //TODO handle this case, currently the moved node
            //would vanish into thin air without this if statement
            return;
        }

        if(node.hasChildRecursive(target)){
            return;
        }

        if(removeFromHierarchy(node)){
            target.addChild(node);
        }
    }

    public List<HierarchyNode<T>> getRoots() {
        return hierarchy_roots;
    }

    public boolean removeFromHierarchy(HierarchyNode<T> node) {
        for (HierarchyNode<T> hn : hierarchy_roots) {
            if(hn.remove(node)){
                return true;
            }
        }
        return false;
    }
  }
