package org.archcnl.javaparser.exceptions;

public class FileIsNotAJavaClassException extends Exception {

    /** */
    private static final long serialVersionUID = -1710153821140047447L;

    private String message;

    public FileIsNotAJavaClassException(String fileName) {
        this.message = "Expected a java file, but was: " + fileName;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
