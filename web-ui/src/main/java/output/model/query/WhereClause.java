package output.model.query;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representation of SPARQL WHERE clause
 */
public class WhereClause implements FormattedDomainObject {

  public static final String WHERE = "WHERE";
  public static final String GRAPH = "GRAPH";
  public static final String GRAPH_FIELD = "?g";
  public static final String OPEN_BRACKET = "{";
  public static final String CLOSE_BRACKET = "}";
  public static final String NEW_LINE = System.lineSeparator();

  private Set<WhereTriple> triples;

  public WhereClause(final Set<WhereTriple> triples) {
    this.triples = triples;
  }

  public Set<WhereTriple> getTriples() {
    return triples;
  }

  public void addTriple(final WhereTriple triple) {
    if (triples == null) {
      triples = new HashSet<>();
    }
    triples.add(triple);
  }

  @Override
  public int hashCode() {
    return Objects.hash(triples);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof WhereClause) {
      final WhereClause that = (WhereClause) obj;
      return Objects.equals(this.triples, that.triples);
    }
    return false;
  }

  @Override
  public String asFormattedString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(WhereClause.WHERE);
    sb.append(" ");
    sb.append(WhereClause.OPEN_BRACKET);
    sb.append(WhereClause.NEW_LINE);
    sb.append(WhereClause.GRAPH);
    sb.append(" ");
    sb.append(WhereClause.GRAPH_FIELD);
    sb.append(WhereClause.NEW_LINE);
    sb.append(WhereClause.OPEN_BRACKET);
    sb.append(WhereClause.NEW_LINE);
    addTriplesToFormattedWhereClause(sb);
    sb.append(WhereClause.CLOSE_BRACKET);
    sb.append(WhereClause.NEW_LINE);
    sb.append(WhereClause.CLOSE_BRACKET);
    return sb.toString();
  }

  private void addTriplesToFormattedWhereClause(final StringBuilder sb) {
    triples.stream().forEach(t -> sb.append(t.asFormattedString() + "." + WhereClause.NEW_LINE));
  }
}
