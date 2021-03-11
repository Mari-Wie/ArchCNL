package org.archcnl.owlcreator.api;

import org.archcnl.owlcreator.impl.OWLAPIImpl;

public class APIFactory {

    public static OntologyAPI instance;

    public static OntologyAPI get() {

        if (instance == null) {
            instance = new OWLAPIImpl();
        }

        return instance;
    }
}
