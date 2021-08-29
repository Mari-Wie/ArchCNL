package org.archcnl.output.model.query;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.archcnl.output.model.query.attribute.QueryField;

/**
 * Representation of SPARQL SELECT clause
 */
public class SelectClause implements FormattedQueryDomainObject, FormattedViewDomainObject {

  public static final String SELECT = "SELECT";

  private Set<QueryField> objects;

  public SelectClause(final Set<QueryField> objects) {
    this.objects = objects;
  }

  public Set<QueryField> getObjects() {
    return objects;
  }

  public void addObject(final QueryField object) {
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
  public String asFormattedString() {
    final StringBuffer sb = new StringBuffer();
    sb.append(SelectClause.SELECT);
    sb.append(" ");
    objects.stream().forEach(o -> sb.append(o.asFormattedString() + " "));
    return sb.toString();
  }

  @Override
  public String asFormattedQuery() {
    final StringBuffer sb = new StringBuffer();
    sb.append(SelectClause.SELECT);
    sb.append(" ");
    objects.stream().forEach(o -> sb.append(o.asFormattedQuery() + " "));
    return sb.toString();
  }
}
