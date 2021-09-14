package org.archcnl.ui.input.components;

import com.vaadin.flow.component.Text;

public abstract class MappingCreationView extends RulesOrMappingCreationLayout {

    private static final long serialVersionUID = 156879235315976468L;

    protected MappingCreationView() {
        add(new Text("Hello World!"));
    }
}
