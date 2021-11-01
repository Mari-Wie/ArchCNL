package org.archcnl.ui.input;

import java.util.List;

public interface MappingListEntry {

    public Object getContent();

    public List<MappingListEntry> getChildren();

    public String toString();
    
    public String getDescription();

    boolean isLeaf();

    boolean isAlterable();
    
}
