package org.archcnl.output.model.query.attribute;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.output.model.query.FormattedDomainObject;

/**
 * Representation of SPARQL object from triple. SPARQL object can be a field, type or primitive
 * value.
 */
public class QueryObject implements FormattedDomainObject {

  private static final String RX_DOUBLE = "[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?";
  private static final String RX_INT = "^-?\\d+$";
  private static final String RX_DATE_TIME =
      "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
  private static final String RX_BOOLEAN = "^(?i)(true|false)$";

  private String value;
  private QueryObjectType type;

  public QueryObject(final String value, final QueryObjectType type) {
    this.value = value;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public QueryObjectType getType() {
    return type;
  }

  @Override
  public String asFormattedString() {
    final StringBuffer sb = new StringBuffer();
    switch (type) {
      case PRIMITIVE_VALUE:
        sb.append("\"");
        sb.append(value);
        sb.append("\"");
        sb.append(getXsdPrimitiveValueTypeAsString());
        break;
      case PROPERTY:
        sb.append(value);
        break;
      default:
        sb.append("?" + value);
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, type);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof QueryObject) {
      final QueryObject that = (QueryObject) obj;
      return Objects.equals(this.value, that.value) && Objects.equals(this.type, that.type);
    }
    return false;
  }

  private String getXsdPrimitiveValueTypeAsString() {
    if (isValueOfType(QueryObject.RX_DOUBLE)) {
      return "^^xsd:double";
    } else if (isValueOfType(QueryObject.RX_INT)) {
      return "^^xsd:integer";
    } else if (isValueOfType(QueryObject.RX_BOOLEAN)) {
      return "^^xsd:boolean";
    } else if (isValueOfType(QueryObject.RX_DATE_TIME)) {
      return "^^xsd:dateTime";
    } else {
      return "^^xsd:string";
    }
  }

  private boolean isValueOfType(final String regex) {
    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
