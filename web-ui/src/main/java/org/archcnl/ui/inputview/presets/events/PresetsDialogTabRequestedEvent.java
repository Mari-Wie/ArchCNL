package org.archcnl.ui.inputview.presets.events;

import org.archcnl.ui.menudialog.OpenRulePresetsDialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.tabs.Tabs;

public class PresetsDialogTabRequestedEvent extends ComponentEvent<OpenRulePresetsDialog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6484915312019460583L;
	private Component currentContent;
	private Component newContent;
	private Tabs tabs;

	public PresetsDialogTabRequestedEvent(OpenRulePresetsDialog source, boolean fromClient, Component currentContent, Component newContent, Tabs tabs) {
		super(source, fromClient);
		this.currentContent = currentContent;
		this.newContent = newContent;
		this.tabs = tabs;
	}
	
	public void handleEvent() {
		getSource().remove(currentContent);
		getSource().add(newContent);
		tabs.getSelectedTab().setEnabled(false);
		tabs.setSelectedIndex(tabs.getSelectedIndex()+1);
	}

}
