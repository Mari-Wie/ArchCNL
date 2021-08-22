package output.model.query;

import java.util.Objects;
import output.model.query.attribute.QueryField;
import output.model.query.attribute.QueryObject;
import output.model.query.attribute.QueryPredicate;

/**
 * Representation of triple from SPARQL subject-predicate-object, for example: ?aggregate rdf:type
 * architecture:Aggregate
 */
public class WhereTriple implements FormattedDomainObject {

  private QueryField subject;
  private QueryPredicate predicate;
  private QueryObject object;

  public WhereTriple(final QueryField subject, final QueryPredicate predicate,
      final QueryObject object) {
    this.subject = subject;
    this.predicate = predicate;
    this.object = object;
  }

  public QueryField getSubject() {
    return subject;
  }

  public QueryPredicate getPredicate() {
    return predicate;
  }

  public QueryObject getObject() {
    return object;
  }

  @Override
  public String asFormattedString() {
    final StringBuilder sb = new StringBuilder();
    sb.append(subject.asFormattedString());
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, predicate, object);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof WhereTriple) {
      final WhereTriple that = (WhereTriple) obj;
      return Objects.equals(this.subject, that.subject)
          && Objects.equals(this.predicate, that.predicate)
          && Objects.equals(this.object, that.object);
    }
    return false;
  }
}
