package org.archcnl.domain.input.exceptions;

import java.util.regex.Pattern;

public class NoMatchFoundException extends Exception {

    public NoMatchFoundException(String text, Pattern pattern) {
        super("No match found in \"" + text + "\" for the pattern \"" + pattern.toString() + "\".");
    }

    private static final long serialVersionUID = -5338254957929411807L;
}
