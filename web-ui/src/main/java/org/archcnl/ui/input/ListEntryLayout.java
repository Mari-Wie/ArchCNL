package org.archcnl.ui.input;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ListEntryLayout<T> extends HorizontalLayout {
	
    private static final long serialVersionUID = 2L;
	
    private ListEntry<T> entry;
	
	private final String EDIT_TEXT = "Edit";
	private final String DELETE_TEXT = "Delete";
	
	public ListEntryLayout(ListEntry<T> entry) {
		//super(createSpan(), createEditButton(), createDeleteButton());
		this.entry = entry;
		
		Span text = new Span(entry.toString());
		text.setWidth("100%");
		addAndExpand(text);
		if(entry.isLeaf()) {
			add(new Button(EDIT_TEXT));
			add(new Button(DELETE_TEXT));
		}
	}

	public ListEntry<T> getListEntry() {
		return entry;
	}

}
