package org.archcnl.domain.output.model.query;

import org.archcnl.domain.common.FormattedAdocDomainObject;

public class FreeTextQuery implements FormattedAdocDomainObject {

    private String name;
    private String query;

    public FreeTextQuery(String name, String query) {
        this.name = name;
        this.query = query;
    }

    @Override
    public String transformToAdoc() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(": (((");
        builder.append(query);
        builder.append(")))");
        return builder.toString();
    }
}
