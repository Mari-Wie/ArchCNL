package org.archcnl.domain.output.model.query;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.archcnl.domain.common.FormattedDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

/** Representation of SPARQL SELECT clause */
public class SelectClause implements FormattedDomainObject {

    public static final String SELECT = "SELECT";

    private Set<Variable> objects;

    public SelectClause(final Set<Variable> objects) {
        this.objects = objects;
    }

    public Set<Variable> getObjects() {
        return objects;
    }

    public void addObject(final Variable object) {
        if (objects == null) {
            objects = new LinkedHashSet<>();
        }
        objects.add(object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SelectClause) {
            final SelectClause that = (SelectClause) obj;
            return Objects.equals(this.objects, that.objects);
        }
        return false;
    }

    @Override
    public String transformToGui() {
        final StringBuilder sb = new StringBuilder();
        sb.append(SelectClause.SELECT);
        sb.append(" ");
        objects.stream().forEach(o -> sb.append(o.transformToGui() + " "));
        return sb.toString();
    }

    @Override
    public String transformToSparqlQuery() {
        final StringBuilder sb = new StringBuilder();
        sb.append(SelectClause.SELECT);
        sb.append(" ");
        objects.stream().forEach(o -> sb.append(o.transformToSparqlQuery() + " "));
        return sb.toString();
    }

    @Override
    public String transformToAdoc() {
        StringBuilder builder = new StringBuilder();
        builder.append("(SELECT");
        for (Variable variable : objects) {
            builder.append(" ");
            builder.append(variable.transformToAdoc());
        }
        builder.append(")");
        return builder.toString();
    }
}
