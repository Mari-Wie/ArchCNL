package org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout;

import java.util.List;

public interface MappingListEntry {

    public Object getContent();

    public List<MappingListEntry> getChildren();

    public String toString();

    public String getDescription();

    boolean isLeaf();

    boolean isAlterable();
}
