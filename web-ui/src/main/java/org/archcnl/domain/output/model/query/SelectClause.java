package org.archcnl.domain.output.model.query;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;
import org.archcnl.domain.common.Variable;

/** Representation of SPARQL SELECT clause */
public class SelectClause implements FormattedQueryDomainObject, FormattedViewDomainObject {

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
        final StringBuffer sb = new StringBuffer();
        sb.append(SelectClause.SELECT);
        sb.append(" ");
        objects.stream().forEach(o -> sb.append(o.transformToGui() + " "));
        return sb.toString();
    }

    @Override
    public String transformToSparqlQuery() {
        final StringBuffer sb = new StringBuffer();
        sb.append(SelectClause.SELECT);
        sb.append(" ");
        objects.stream().forEach(o -> sb.append(o.transformToSparqlQuery() + " "));
        return sb.toString();
    }
}
