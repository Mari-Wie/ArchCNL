package org.archcnl.ui.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.MainView;
import org.archcnl.ui.menudialog.OpenRulePresetsDialog;

import com.vaadin.flow.component.ComponentEvent;

public class RulesOptionRequestedEvent extends ComponentEvent<MainView> {

	public enum RulesOption {
		IMPORT_FROM_FILE, IMPORT_RULE_PRESETS
	}

	private static final Logger LOG = LogManager.getLogger(RulesOptionRequestedEvent.class);
	private static final long serialVersionUID = 7994033413072875198L;
	private RulesOption option;

	public RulesOptionRequestedEvent(final MainView source, final boolean fromClient, final RulesOption option) {
		super(source, fromClient);
		this.option = option;
	}

	public void handleEvent() {
		switch (option) {
		case IMPORT_FROM_FILE:
			RulesOptionRequestedEvent.LOG.warn("{} is not implemented", option);
			break;
		case IMPORT_RULE_PRESETS:
			new OpenRulePresetsDialog().open();
			break;
		default:
			RulesOptionRequestedEvent.LOG.warn("Unhandled RulesOption {} appeared in RulesOptionRequestedEvent.",
					option);
			break;
		}
	}
}
