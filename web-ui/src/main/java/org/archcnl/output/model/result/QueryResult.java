package org.archcnl.output.model.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryResult {

  private List<String> columnNames;
  private List<List<String>> results;

  public QueryResult(final List<String> columnNames, final List<List<String>> results) {
    this.columnNames = columnNames;
    this.results = results;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public List<List<String>> getResults() {
    return results;
  }

  public void addColumnName(final String columnName) {
    if (columnNames == null) {
      columnNames = new ArrayList<>();
    }
    columnNames.add(columnName);
  }

  public void addResult(final List<String> row) {
    if (results == null) {
      results = new ArrayList<>();
    }
    results.add(row);
  }

  @Override
  public int hashCode() {
    return Objects.hash(columnNames, results);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof QueryResult) {
      final QueryResult that = (QueryResult) obj;
      return Objects.equals(this.columnNames, that.columnNames)
          && Objects.equals(this.results, that.results);
    }
    return false;
  }
}
