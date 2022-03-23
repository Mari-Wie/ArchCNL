package org.archcnl.domain.output.model.query;

public class PrespecifiedQuery {

    private String name;
    private String query;
    private String description;

    public PrespecifiedQuery(String name, String description, String query) {
        this.name = name;
        this.description = description;
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public String getQueryString() {
        return query;
    }

    public String getDescription() {
        return description;
    }
}
