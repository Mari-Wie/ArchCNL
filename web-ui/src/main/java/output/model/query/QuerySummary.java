package output.model.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Representation of query summary. For example: number of packages in the project, number of types
 * etc.
 */
public class QuerySummary {

  private Map<String, Double> summaries = new HashMap<>();

  public QuerySummary(final Map<String, Double> summaries) {
    this.summaries = summaries;
  }

  public Map<String, Double> getSummaries() {
    return summaries;
  }

  public void addSummary(final String name, final Double value) {
    if (summaries == null) {
      summaries = new HashMap<>();
    }
    summaries.put(name, value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(summaries);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof QuerySummary) {
      final QuerySummary that = (QuerySummary) obj;
      return Objects.equals(this.summaries, that.summaries);
    }
    return false;
  }
}
