package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions;

public class TripletNotDefinedException extends Exception {

    private static final long serialVersionUID = -6563406514496239361L;

    public TripletNotDefinedException() {
        super("Some parts of the triplet are not defined.");
    }
}
