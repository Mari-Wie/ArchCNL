package org.archcnl.ui.main.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.main.MainView;

public class FooterOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum FooterOption {
        CONTACT,
        WIKI,
        PROJECT_SITE
    }

    private static final Logger LOG = LogManager.getLogger(FooterOptionRequestedEvent.class);
    private static final long serialVersionUID = -1319047270686749102L;
    private static final String CONTACT_URL =
            "https://www.inf.uni-hamburg.de/en/inst/ab/swk/home.html";
    private static final String PROJECT_SITE_URL = "https://github.com/Mari-Wie/ArchCNL";
    private static final String WIKI_URL = "https://github.com/Mari-Wie/ArchCNL/wiki";
    private FooterOption option;

    public FooterOptionRequestedEvent(MainView source, boolean fromClient, FooterOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case CONTACT:
                openInNewTab(CONTACT_URL);
                break;
            case PROJECT_SITE:
                openInNewTab(PROJECT_SITE_URL);
                break;
            case WIKI:
                openInNewTab(WIKI_URL);
                break;
            default:
                LOG.warn(
                        "Unhandled FooterOption {} appeared in FooterOptionRequestedEvent.",
                        option);
                break;
        }
    }

    private void openInNewTab(String url) {
        try {
            getSource().getUI().get().getPage().open(url, "_blank");
        } catch (NoSuchElementException e) {
            LOG.error("Failed to open {} in a new tab", url);
        }
    }
}
