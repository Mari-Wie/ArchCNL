package org.archcnl.output.model.query.attribute;

import java.util.Objects;
import org.archcnl.output.model.query.FormattedDomainObject;

/**
 * Representation of SPARQL field.
 */
public class QueryField implements FormattedDomainObject {

  public static final String PREFIX = "?";

  private String value;

  public QueryField(final String value) {
    this.value = value;
  }

  public static String getPrefix() {
    return QueryField.PREFIX;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String asFormattedString() {
    return QueryField.PREFIX + value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, value);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof QueryField) {
      final QueryField that = (QueryField) obj;
      return Objects.equals(this.value, that.value);
    }
    return false;
  }
}
