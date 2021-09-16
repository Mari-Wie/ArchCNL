package org.archcnl.ui.input;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.Relation;

public class RelationListEntry implements MappingListEntry {

    private List<MappingListEntry> children;
    private String name;
    private Relation content;
    private boolean isLeaf;

    public RelationListEntry(String name, List<Relation> list) {
    	children =
                list.stream()
                        .map(
                                entry ->
                                                new RelationListEntry(entry.toString(), entry))
                        .collect(Collectors.toList());
        this.name = name;
        isLeaf = false;
    }

    public RelationListEntry(String name, Relation content) {
        this.name = name;
        this.content = content;
        children = Collections.<MappingListEntry>emptyList();
        isLeaf = true;
    }

    public Relation getContent() {
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

    // TODO:
    // change this to return false for default entries when these can be differentiated from other
    // entries
    public boolean isAlterable() {
        return true;
    }
}
