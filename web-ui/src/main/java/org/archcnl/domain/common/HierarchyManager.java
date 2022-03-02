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

    public void moveNode(HierarchyNode<T> node, HierarchyNode<T> target) {
        if (hierarchy_roots.contains(node)) {
            hierarchy_roots.remove(node);
        } else {
            removeFromHierarchy(node);
        }
        target.addChild(node);
    }

    public List<HierarchyNode<T>> getRoots() {
        return hierarchy_roots;
    }

    public void removeFromHierarchy(HierarchyNode<T> node) {
        for (HierarchyNode<T> hn : hierarchy_roots) {
            hn.remove(node);
        }
    }
}
