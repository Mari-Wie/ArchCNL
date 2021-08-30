package org.archcnl.webui.exceptions;

public class UnrelatedMappingException extends Exception {

    private static final long serialVersionUID = -3768600660836011124L;

    public UnrelatedMappingException(String realName, String differentName) {
        super(
                "The mapping belonging to \""
                        + differentName
                        + "\" cannot be used for \""
                        + realName
                        + "\".");
    }
}
