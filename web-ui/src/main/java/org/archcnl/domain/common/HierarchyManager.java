package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class HierarchyManager<T extends HierarchyObject> {

    List<HierarchyNode<T>> hierarchyRoots;

    public HierarchyManager() {
        hierarchyRoots = new ArrayList<>();
    }

    public void addHierarchyRoot(String name) {
        hierarchyRoots.add(new HierarchyNode<>(name));
    }

    public void addHierarchyRoot(String name, boolean removable) {
        HierarchyNode<T> newNode = new HierarchyNode<>(name);
        newNode.setRemoveable(removable);
        hierarchyRoots.add(newNode);
    }

    public void addHierarchyRoot(HierarchyNode<T> newRoot) {
        hierarchyRoots.add(newRoot);
    }

    private boolean containedInRoots(HierarchyNode<T> node) {
        for (HierarchyNode<T> n : hierarchyRoots) {
            if (node.equals(n)) {
                return true;
            }
        }
        return false;
    }

    public void moveNode(HierarchyNode<T> node, HierarchyNode<T> target) {
        if (containedInRoots(node)) {
            // TODO handle this case, currently the moved node
            // would vanish into thin air without this if statement
            return;
        }

        if (node.hasChildRecursive(target)) {
            return;
        }

        if (removeFromHierarchy(node)) {
            target.addChild(node);
        }
    }

    public List<HierarchyNode<T>> getRoots() {
        return hierarchyRoots;
    }

    public boolean removeFromHierarchy(HierarchyNode<T> node) {
        for (HierarchyNode<T> hn : hierarchyRoots) {
            if (hn.equals(node)) {
                if (hn.isRemoveable()) {
                    hierarchyRoots.remove(node);
                    return true;
                } else {
                    return false;
                }
            }
            if (hn.remove(node)) {
                return true;
            }
        }
        return false;
    }
}
