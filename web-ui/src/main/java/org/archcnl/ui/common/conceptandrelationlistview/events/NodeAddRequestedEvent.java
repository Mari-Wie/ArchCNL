package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class NodeAddRequestedEvent
        extends ComponentEvent<HierarchyView<? extends HierarchyObject>> {

    private static final long serialVersionUID = -6392097240611643823L;

    public enum NodeType {
        CONCEPT,
        RELATION,
        UNKNOWN
    }

    private String nodename;
    private NodeType nodeType;

    public NodeAddRequestedEvent(
            HierarchyView<? extends HierarchyObject> source,
            String nodename,
            boolean isFromClient) {
        super(source, isFromClient);
        this.nodename = nodename;
        this.nodeType = NodeType.UNKNOWN;
    }

    public String getName() {
        return this.nodename;
    }

    public NodeType nodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}
