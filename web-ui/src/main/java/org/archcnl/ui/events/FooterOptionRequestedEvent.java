package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.MainView;

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

    public FooterOptionRequestedEvent(
            final MainView source, final boolean fromClient, final FooterOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case CONTACT:
                openInNewTab(FooterOptionRequestedEvent.CONTACT_URL);
                break;
            case PROJECT_SITE:
                openInNewTab(FooterOptionRequestedEvent.PROJECT_SITE_URL);
                break;
            case WIKI:
                openInNewTab(FooterOptionRequestedEvent.WIKI_URL);
                break;
            default:
                FooterOptionRequestedEvent.LOG.warn(
                        "Unhandled FooterOption {} appeared in FooterOptionRequestedEvent.",
                        option);
                break;
        }
    }

    private void openInNewTab(final String url) {
        try {
            getSource().getUI().get().getPage().open(url, "_blank");
        } catch (final NoSuchElementException e) {
            FooterOptionRequestedEvent.LOG.error("Failed to open {} in a new tab", url);
        }
    }
}
