package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;

public class VerbPhraseContainer {

    public enum Connector {
        AND,
        OR;
    }

    private Connector connector = Connector.AND;
    private List<VerbPhrase> phrases = new ArrayList<>();

    public void addVerbPhrase(VerbPhrase phrase) {
        phrases.add(phrase);
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Connector getConnector() {
        return connector;
    }

    public List<VerbPhrase> getPhrases() {
        return phrases;
    }
}
