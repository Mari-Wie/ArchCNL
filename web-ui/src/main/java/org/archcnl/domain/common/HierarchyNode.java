package org.archcnl.domain.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class HierarchyNode<T extends HierarchyObject> {
    private T entry;
    private String text;
    private List<HierarchyNode<T>> children;

    /** Constructor for creating seperation nodes */
    public HierarchyNode(String text) {
        entry = null;
        this.text = text;
        children = new ArrayList<HierarchyNode<T>>();
    }

    /** Constructor for creating node containing HierarchyObject */
    public HierarchyNode(T p_entry) {
        this.entry = p_entry;
        text = null;
        children = new ArrayList<HierarchyNode<T>>();
    }

    /**
     * Checks if the node has a HierarchyObject
     *
     * @return false if no object was set, true otherwise
     */
    public boolean hasEntry() {
        return entry != null;
    }

    /**
     * Getter for child nodes
     *
     * @return List<HierarchyObject> List of children
     */
    public List<HierarchyNode<T>> getChildren() {
        return children;
    }

    /**
     * Getter for HierarchyObject
     *
     * @return returns a HierarchyObject if it was set, otherwise null
     */
    public T getEntry() {
        if (hasEntry()) {
            return entry;
        } else {
            return null;
        }
    }

    /**
     * Getter for the name of the node. The name is either the name of the HierarchyObjecty or the
     * name of text of the node
     *
     * @return Either the name of the contained HierarchyObject or the tesxt of the node.
     */
    public String getName() {
        if (hasEntry()) {
            return entry.getName();
        }
        return text;
    }

    /**
     * Getter for the desctiption of the contained HierarchyObject
     *
     * @return returns the description if a HierarchyObject is found, otherwise a empty string.
     */
    public String getDescription() {
        if (hasEntry()) {
            return entry.getDescription();
        }
        return "";
    }

    /**
     * Adds given node as a child to the node. If node and this node are the same no child is added
     *
     * @param newChild the node that is to be added
     * @return true if node was added, false otherwise
     */
    public boolean addChild(HierarchyNode<T> hierrarchyNode) {
        children.add(hierrarchyNode);
        return true;
    }

    /**
     * Adds given HierarchyObject as a newly created child to the node. If node and this node are
     * the same no child is added
     *
     * @param newChild the node that is to be added
     * @return true if node was added, false otherwise
     */
    public boolean add(T newChild) {
        // TODO consider using a set instead of a listfor children
        return addChild(new HierarchyNode<T>(newChild));
    }
    /**
     * Adds given HierarchyNode to a child node with given destination If node and this node are the
     * same no child is added If no child is found that matches with the destination no node is
     * added
     *
     * @param newChild the node that is to be added
     * @param destination name of the node where the child is to be added
     * @return true if node was added, false otherwise
     */
    public boolean add(HierarchyNode<T> newChild, String destination) {
        if (getName() == destination) {
            return addChild(newChild);
        } else {
            for (HierarchyNode<T> node : children) {
                return add(newChild, destination);
            }
        }
        return false;
    }

    /**
     * Removes given HierarchyNode Recursivelty searches in all children for given node If no node
     * is found nothing changes in the hierarchy
     *
     * @param hierarchyNode The node that is to be removed
     * @return true if node was removed, false otherwise
     */
    public boolean remove(HierarchyNode<T> hierarchyNode) {
        for (HierarchyNode<T> childNode : children) {

            if (hierarchyNode.equals(childNode)) {
                return removeChild(childNode);
            } else {
                if (childNode.remove(hierarchyNode)) return true;
            }
        }
        return false;
    }

    public boolean hasChildRecursive(HierarchyNode<T> hierarchyNode) {
        for (HierarchyNode<T> childNode : children) {
            if (hierarchyNode.equals(childNode)) {
                return true;
            } else {
                if (childNode.hasChildRecursive(hierarchyNode)) return true;
            }
        }
        return false;
    }

    /**
     * Removes given HierarchyNode from this node If no node is found nothing changes in the
     * hierarchy
     *
     * @param hierarchyNode The node that is to be removed
     * @return true if node was removed, false otherwise
     */
    public boolean removeChild(HierarchyNode<T> hierarchyNode) {
        children.remove(hierarchyNode);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HierarchyNode) {
            final HierarchyNode<?> that = (HierarchyNode<?>) obj;
            if (this.getName() != null && that.getName() != null) {
                return Objects.equals(this.getName(), that.getName());
            } else if (this.getEntry() != null && that.getEntry() != null) {
                return Objects.equals(this.getEntry().getName(), that.getEntry().getName());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName() != null ? Objects.hash(getName()) : Objects.hash(getEntry().getName());
    }
}
