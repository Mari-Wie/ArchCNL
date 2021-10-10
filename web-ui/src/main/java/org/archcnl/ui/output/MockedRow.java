package org.archcnl.ui.output;

public class MockedRow {

    private String violates;
    private String className;
    private String line;
    private String lineNumber;
    private String fullName;
    private String timeStamp;
    private int index;

    public MockedRow(
            int index,
            String violates,
            String className,
            String line,
            String lineNumber,
            String fullName,
            String timeStamp) {
        this.index = index;
        this.violates = violates;
        this.className = className;
        this.line = line;
        this.lineNumber = lineNumber;
        this.fullName = fullName;
        this.timeStamp = timeStamp;
    }

    public String getViolates() {
        return violates;
    }

    public String getClassName() {
        return className;
    }

    public String getLine() {
        return line;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getIndex() {
        return index;
    }
}
